package backend

import (
	"encoding/json"
	"errors"
	"fmt"
	"log"
	"reflect"
	"strconv"
	"sync"
	"time"

	"github.com/garyburd/redigo/redis"
	gosocketio "github.com/graarh/golang-socketio"
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

type mongoConf struct {
	Addr        string `json:"addr"`
	Port        string `json:"port"`
	Database    string `json:"database"`
	Username    string `json:"username"`
	Password    string `json:"password"`
	Collection  string `json:"collection"`
	MaxChipSize int    `json:"maxChipSize"`
}

type mongoExportStruct struct {
	ID      int       `json:"id"`
	Mongodb mongoConf `json:"mongodb"`
	Keys    []string  `json:"keys"`
	Process float32   `json:"process"`
	ErrMsg  string    `json:"errMsg"`
	Task    string    `json:"task"`

	complete     chan int
	processMutex *sync.RWMutex
	redisClient  *redis.Conn
	session      *mgo.Session
	mgoChan      chan redisValue
	waitGroup    *sync.WaitGroup
	errorChan    chan error
}

var exportStorage []*mongoExportStruct
var maxExportID int

// sendExportErrorToAll
func sendExportErrorToAll(msg string) {
	if isDebug {
		logErr(errors.New(msg))
	}
	socketIOServer.BroadcastToAll("cmdErr", "Export to mongodb error: "+msg)
}

func (task *mongoExportStruct) calProcess(currentKeyLen, currentKeyTotal int) {
	if isDebug {
		log.Println("calProcess", currentKeyLen, currentKeyTotal, len(task.Keys))
	}
	task.processMutex.Lock()
	task.Process += float32(currentKeyLen) / float32(currentKeyTotal) / float32(len(task.Keys)*2)
	if isDebug {
		log.Println("calProcess", task.Process)
	}
	task.processMutex.Unlock()
}

func scanVals(c redis.Conn, key string, t scanType, iterate int64) (int64, []string, error) {
	var method string
	var ret []interface{}
	var keys []string
	var err error
	switch t {
	case setScan:
		method = "sscan"
	case hashScan:
		method = "hscan"
	case zsetScan:
		method = "zscan"
	}
	ret, err = redis.Values(c.Do(method, key, iterate, "COUNT", _globalConfigs.System.KeyScanLimits))
	if err != nil {
		return 0, nil, err
	}
	keys, err = redis.Strings(ret[1], nil)
	if err != nil {
		return 0, nil, err
	}
	iterate, err = redis.Int64(ret[0], nil)
	if err != nil {
		return 0, nil, err
	}
	return iterate, keys, nil
}

func (task *mongoExportStruct) run() {

	task.waitGroup.Add(2)
	go func() {
		var isCloseMgoChan = false
		defer func() {
			task.waitGroup.Done()
			task.session.Close()
			if !isCloseMgoChan {
				close(task.mgoChan)
			}
			if isDebug {
				log.Println("close mongo goroutine ")
			}
		}()
		mongoClient := task.session.DB(task.Mongodb.Database).C(task.Mongodb.Collection)
		var err error
		var _redisVal redisValue
		var isClose bool
		for {
			select {
			case err = <-task.errorChan:
				if err != nil {
					task.ErrMsg = err.Error()
					if isDebug {
						logErr(err)
					}
					return
				}
			case _redisVal, isClose = <-task.mgoChan:
				if _redisVal.Val != nil {
					// upsert key
					if isDebug {
						log.Println("upsert key!" + _redisVal.Key)
					}
					// remove the exists key
					_, err = mongoClient.Upsert(bson.M{"key": _redisVal.Key}, _redisVal)

					if err != nil {
						if isDebug {
							logErr(errors.New("export key: " + _redisVal.Key + " occur " + err.Error()))
						}
						task.errorChan <- err
						isCloseMgoChan = true
						close(task.mgoChan)
						return
					}
					v := reflect.ValueOf(_redisVal.Val)
					switch v.Kind() {
					case reflect.Map, reflect.Slice, reflect.Array:
						task.calProcess(v.Len(), _redisVal.Size)
					default:
						task.calProcess(1, 1)
					}
				}
				if _redisVal.Val == nil && !isClose {
					return
				}
			case _ = <-task.complete:
				return
			}
		}
	}()

	go func() {
		defer func() {
			if err := recover(); err != nil {
				if isDebug {
					logErr(fmt.Errorf("err occur: %s", err))
				}
			}
			task.waitGroup.Done()
			(*task.redisClient).Close()
			if isDebug {
				log.Println("close redis goroutine ")
			}
			task.complete <- 1
		}()
		c := *task.redisClient

		var keysNums = len(task.Keys)
		var _redisVal redisValue
		var err error

		for i := 0; i < keysNums; i++ {
			var chipNums int
			select {
			case err = <-task.errorChan:
				if err != nil {
					task.ErrMsg = err.Error()
					if isDebug {
						logErr(err)
					}
					return
				}
			default:
				key := task.Keys[i]
				t, err := redis.String(c.Do("TYPE", key))
				if err != nil {
					task.errorChan <- err
					return
				}
				_redisVal.Key = key
				_redisVal.T = t
				_redisVal.Val = nil
				switch t {
				case "none":
					task.errorChan <- errors.New("redis: key [" + key + "] is not exists")
					return
				case "string":
					str, err := redis.String(c.Do("GET", key))
					if err != nil {
						if isDebug {
							logErr(err)
						}
						task.errorChan <- err
						return
					}
					_redisVal.Val = str
					_redisVal.Size = 1
				case "list":
					l, err := redis.Int(c.Do("LLEN", key))
					if err != nil {
						if isDebug {
							logErr(err)
						}
						task.errorChan <- err
						return
					}
					_redisVal.Size = l
					var tmpList []string
					tmpList = make([]string, 0, _globalConfigs.System.RowScanLimits)
					for j := 0; j < l; j += _globalConfigs.System.RowScanLimits {
						end := _globalConfigs.System.RowScanLimits - 1
						list, err := redis.Strings(c.Do("LRANGE", key, j, end))
						if err != nil {
							if isDebug {
								logErr(err)
							}
							task.errorChan <- err
							return
						}
						tmpList = append(tmpList, list...)
						task.calProcess(len(list), l)
						if len(tmpList) >= task.Mongodb.MaxChipSize { // make Chip
							_redisVal.Val = tmpList
							tmp := _redisVal.Key
							_redisVal.Key = _redisVal.Key + "_skychip" + strconv.Itoa(chipNums)
							chipNums++
							task.mgoChan <- _redisVal
							_redisVal.Key = tmp
							tmpList = make([]string, 0, _globalConfigs.System.RowScanLimits)
						}
					}
					_redisVal.Val = tmpList
				case "set":
					l, err := redis.Int(c.Do("SCARD", key))
					if err != nil {
						task.errorChan <- err
						return
					}
					_redisVal.Size = l
					var tmpList []string
					tmpList = make([]string, 0, _globalConfigs.System.RowScanLimits)
					var iter int64
					var vals []string
					for {
						iter, vals, err = scanVals(c, key, setScan, iter)
						if err != nil {
							if isDebug {
								logErr(err)
							}
							task.errorChan <- err
							return
						}
						task.calProcess(len(vals), l)
						tmpList = append(tmpList, vals...)
						if iter == 0 {
							break
						}
						if len(tmpList) >= task.Mongodb.MaxChipSize { // make Chip
							_redisVal.Val = tmpList
							tmp := _redisVal.Key
							_redisVal.Key = _redisVal.Key + "_skychip" + strconv.Itoa(chipNums)
							chipNums++
							task.mgoChan <- _redisVal
							_redisVal.Key = tmp
							tmpList = make([]string, 0, _globalConfigs.System.RowScanLimits)
						}
					}
					_redisVal.Val = tmpList
				case "zset":
					l, err := redis.Int(c.Do("ZCOUNT", key, "-inf", "+inf"))
					if err != nil {
						task.errorChan <- err
						return
					}
					_redisVal.Size = l
					var tmpMap map[string]float64
					tmpMap = make(map[string]float64)
					var iter int64
					var vals []string
					for {
						iter, vals, err = scanVals(c, key, zsetScan, iter)
						if err != nil {
							if isDebug {
								logErr(err)
							}
							task.errorChan <- err
							return
						}
						task.calProcess(len(vals)/2, l)
						for j := 0; j < len(vals); j += 2 {
							tmpFloat64, _ := strconv.ParseFloat(vals[j+1], 64)
							tmpMap[vals[j]] = tmpFloat64
						}
						if iter == 0 {
							break
						}
						if len(tmpMap) >= task.Mongodb.MaxChipSize { // make Chip
							_redisVal.Val = tmpMap
							tmp := _redisVal.Key
							_redisVal.Key = _redisVal.Key + "_skychip" + strconv.Itoa(chipNums)
							chipNums++
							task.mgoChan <- _redisVal
							_redisVal.Key = tmp
							tmpMap = make(map[string]float64)
						}
					}
					_redisVal.Val = tmpMap
				case "hash":
					l, err := redis.Int(c.Do("HLEN", key))
					if err != nil {
						if isDebug {
							logErr(err)
						}
						task.errorChan <- err
						return
					}
					_redisVal.Size = l
					var tmpMap map[string]string
					tmpMap = make(map[string]string)
					var iter int64
					var vals []string
					for {
						iter, vals, err = scanVals(c, key, hashScan, iter)
						if err != nil {
							if isDebug {
								logErr(err)
							}
							task.errorChan <- err
							return
						}
						if isDebug {
							log.Println("iter", iter)
						}
						task.calProcess(len(vals)/2, l)
						for j := 0; j < len(vals); j += 2 {
							tmpMap[vals[j]] = vals[j+1]
						}
						if iter == 0 {
							break
						}
						if len(tmpMap) >= task.Mongodb.MaxChipSize { // make Chip
							_redisVal.Val = tmpMap
							tmp := _redisVal.Key
							_redisVal.Key = _redisVal.Key + "_skychip" + strconv.Itoa(chipNums)
							chipNums++
							task.mgoChan <- _redisVal
							_redisVal.Key = tmp
							tmpMap = make(map[string]string)
						}
					}
					_redisVal.Val = tmpMap
				default:
					task.errorChan <- errors.New("redis: keyType [" + t + "] of key [" + key + "]  is not support")
					return
				}
				if chipNums > 0 {
					_redisVal.Key = _redisVal.Key + "_skychip" + strconv.Itoa(chipNums)
				}
				task.mgoChan <- _redisVal
			}
		}
	}()
	task.waitGroup.Wait()
	select {
	case err := <-task.errorChan:
		if err != nil {
			task.ErrMsg = err.Error()
		}
	default:
		task.Process = 1
	}
	close(task.errorChan)
	close(task.complete)
}

//Export2mongodb export keys to mongodb
func Export2mongodb(conn *gosocketio.Channel, data interface{}) {
	if operdata, ok := checkOperData(conn, data); ok {
		dataBytes, _ := json.Marshal(operdata.Data)
		var exportInfo mongoExportStruct
		err := json.Unmarshal(dataBytes, &exportInfo)
		if err != nil {
			sendCmdError(conn, err.Error())
			return
		}
		session, err := mgo.DialWithInfo(&mgo.DialInfo{
			Username: exportInfo.Mongodb.Username,
			Password: exportInfo.Mongodb.Password,
			Database: exportInfo.Mongodb.Database,
			Addrs:    []string{exportInfo.Mongodb.Addr + ":" + exportInfo.Mongodb.Port},
			Timeout:  time.Second * 3,
		})
		if err != nil {
			sendCmdError(conn, err.Error())
			return
		}
		redisClient, err := getRedisClient(operdata.ServerID, operdata.DB)
		if err != nil {
			sendCmdError(conn, err.Error())
			return
		}
		maxExportID++
		exportInfo.ID = maxExportID
		if exportInfo.Task == "" {
			exportInfo.Task = time.Now().Format("2006-01-02 15:04:05")
		}
		exportInfo.session = session
		exportInfo.redisClient = &redisClient
		exportInfo.mgoChan = make(chan redisValue, 5)
		exportInfo.errorChan = make(chan error, 1)
		exportInfo.complete = make(chan int, 1)
		exportInfo.processMutex = new(sync.RWMutex)
		exportInfo.waitGroup = new(sync.WaitGroup)
		if exportInfo.Mongodb.MaxChipSize == 0 {
			exportInfo.Mongodb.MaxChipSize = 100000
		}
		exportStorage = append(exportStorage, &exportInfo)
		go exportInfo.run()
		conn.Emit("AddExportTaskSuccess", 0)
	}
}

// GetExportTasksProcess get process of all tasks
func GetExportTasksProcess(conn *gosocketio.Channel, data interface{}) {
	conn.Emit("ShowExportTaskProcess", exportStorage)
}

// DelExportTask del process task
func DelExportTask(conn *gosocketio.Channel, id int) {
	for i := 0; i < len(exportStorage); i++ {
		if exportStorage[i].ID == id {
			exportStorage = append(exportStorage[:i], exportStorage[i+1:]...)
			break
		}
	}
	conn.Emit("tip", &info{"success", "del success!", 2})
}

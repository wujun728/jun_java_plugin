package backend

import (
	"errors"

	"time"

	"strconv"

	"reflect"

	"encoding/json"

	"github.com/garyburd/redigo/redis"
	gosocketio "github.com/graarh/golang-socketio"
)

var redisClients map[int]*redis.Pool

type scanType int

var pool *redis.Pool

const (
	hashScan scanType = iota
	zsetScan
	setScan
	keyScan
)

// operData 操作协议
type operData struct {
	DB       int         `json:"db"`
	ServerID int         `json:"serverid"`
	Data     interface{} `json:"data"`
}

type dataStruct struct {
	Index  int       `json:"index"` // list 用
	OldVal redisData `json:"oldVal"`
	NewVal redisData `json:"newVal"`
}

type redisData struct {
	Val   string  `json:"val"`
	Field string  `json:"field"`
	Score float32 `json:"score"`
}

// type redisValue
type redisValue struct {
	Key  string      `json:"key"`
	T    string      `json:"t"`
	TTL  int64       `json:"ttl"`
	Size int         `json:"size"`
	Val  interface{} `json:"val"`
}

func (_redisValue redisValue) marshal() ([]byte, error) {
	return json.Marshal(_redisValue)
}

func init() {
	redisClients = make(map[int]*redis.Pool)
}

func getRedisClient(serverID int, db int) (redis.Conn, error) {
	var ok bool
	pool, ok = redisClients[serverID]
	if ok == false {
		pool = redis.NewPool(func() (redis.Conn, error) {
			for i := 0; i < len(_globalConfigs.Servers); i++ {
				if serverID == _globalConfigs.Servers[i].ID {
					c, err := redis.DialTimeout("tcp", _globalConfigs.Servers[i].Host+":"+strconv.Itoa(_globalConfigs.Servers[i].Port), time.Duration(_globalConfigs.System.ConnectionTimeout)*time.Second, time.Duration(_globalConfigs.System.ExecutionTimeout)*time.Second, time.Duration(_globalConfigs.System.ExecutionTimeout)*time.Second)
					if err != nil {
						return nil, errors.New("redis server dial error" + err.Error())
					}
					if _globalConfigs.Servers[i].Auth != "" {
						if _, err := c.Do("AUTH", _globalConfigs.Servers[i].Auth); err != nil {
							c.Close()
							return nil, err
						}
					}
					return c, nil
				}
			}
			return nil, errors.New("redis server id is out of range")
		}, 2)
	}
	if pool == nil {
		return nil, errors.New("redis server id is out of range")
	}
	redisClients[serverID] = pool
	c := pool.Get()
	_, err := c.Do("SELECT", db)
	if err != nil {
		if c != nil {
			c.Close()
		}
		return nil, err
	}
	return c, nil
}

func closeClient(serverID int) error {
	if client, ok := redisClients[serverID]; ok {
		return client.Close()
	}
	return nil
}

// checkOperData 检查协议
func checkOperData(conn *gosocketio.Channel, data interface{}) (operData, bool) {
	var info operData
	var err error
	var bytes []byte
	bytes, err = json.Marshal(data)
	if err != nil {
		sendCmdError(conn, err.Error())
		return info, false
	}
	err = json.Unmarshal(bytes, &info)
	if err != nil {
		sendCmdError(conn, err.Error())
		return info, false
	}
	return info, true
}

// sendCmd
func sendCmd(conn *gosocketio.Channel, cmd string) {
	conn.Emit("cmdLog", cmd)
}

// sendRedisErr
func sendCmdError(conn *gosocketio.Channel, cmd string) {
	if isDebug {
		logErr(errors.New(cmd))
	}
	conn.Emit("cmdErr", cmd)
}

// sendRedisReceive
func sendCmdReceive(conn *gosocketio.Channel, data interface{}) {
	var info string
	v := reflect.ValueOf(data)
	k := v.Kind()
	switch k {
	case reflect.Int,
		reflect.Int8,
		reflect.Int16,
		reflect.Int32,
		reflect.Uint,
		reflect.Uint8,
		reflect.Uint16,
		reflect.Uint32,
		reflect.Uint64,
		reflect.Int64:
		info = strconv.FormatInt(v.Int(), 10)
	case reflect.Float64, reflect.Float32:
		info = strconv.FormatFloat(v.Float(), 'f', -1, 64)
	case reflect.Array, reflect.Map, reflect.Slice:
		info = "Array: " + strconv.Itoa(v.Len())
	case reflect.String:
		info = "String: " + v.String()
	default:
		info = "Unknown: " + k.String()
	}
	conn.Emit("cmdReceive", "Receive: "+info)
}

func checkRedisValue(conn *gosocketio.Channel, data interface{}) (c redis.Conn, _redisValue redisValue, b bool) {
	if info, ok := checkOperData(conn, data); ok {
		bytes, _ := json.Marshal(info.Data)
		var err error
		err = json.Unmarshal(bytes, &_redisValue)
		if err != nil {
			sendCmdError(conn, "Unmarshal error "+err.Error())
			return c, _redisValue, false
		}
		c, err = getRedisClient(info.ServerID, info.DB)
		if err != nil {
			sendCmdError(conn, "redis error: "+err.Error())
			return c, _redisValue, false
		}
		return c, _redisValue, true
	}
	return c, _redisValue, false
}

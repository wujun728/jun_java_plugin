package backend

import (
	"encoding/json"
	"fmt"
	"strconv"
	"strings"

	"log"

	"github.com/garyburd/redigo/redis"
	gosocketio "github.com/graarh/golang-socketio"
)

type scanInfoStruct struct {
	Key  string `json:"key"`
	Iter int64  `json:"iter"`
}

type scanResultStruct struct {
	Keys []string `json:"keys"`
	Iter int64    `json:"iter"`
}

func scan(conn *gosocketio.Channel, c redis.Conn, key, field string, t scanType, iterate int64, maxScanLimits int) (int64, []string) {

	var method, cmd string
	var ret []interface{}
	var keys, tmp []string
	var err error
	switch t {
	case keyScan:
		method = "scan"
	case setScan:
		method = "sscan"
	case hashScan:
		method = "hscan"
	case zsetScan:
		method = "zscan"
	default:
		log.Println("type not exists!")
		return 0, nil
	}

	for {

		if t == keyScan {
			if !strings.ContainsAny(key, "*") {
				key = key + "*"
			}
			cmd = fmt.Sprintf("%s %d MATCH %s COUNT %d", method, iterate, key, _globalConfigs.System.KeyScanLimits)
			ret, err = redis.Values(c.Do(method, iterate, "MATCH", key, "COUNT", _globalConfigs.System.KeyScanLimits))

		} else {
			if key == "" {
				sendCmdError(conn, "key can't not be empty")
				return 0, nil
			}
			if !strings.ContainsAny(field, "*") {
				field = field + "*"
			}
			cmd = fmt.Sprintf("%s %s %d MATCH %s COUNT %d", method, key, iterate, field, _globalConfigs.System.KeyScanLimits)
			ret, err = redis.Values(c.Do(method, key, iterate, "MATCH", field, "COUNT", _globalConfigs.System.KeyScanLimits))
		}

		sendCmd(conn, cmd)
		if err != nil {
			sendCmdError(conn, "redis error: "+err.Error())
			return 0, nil
		}
		sendCmdReceive(conn, ret)
		tmp, err = redis.Strings(ret[1], nil)
		if err != nil {
			sendCmdError(conn, "redis error: "+err.Error())
			return 0, nil
		}
		keys = append(keys, tmp...)
		iterate, err = redis.Int64(ret[0], nil)
		if err != nil {
			sendCmdError(conn, "redis error: "+err.Error())
			return 0, nil
		}
		if iterate == 0 || len(keys) >= maxScanLimits {
			break
		}
	}

	return iterate, keys
}

// ScanKeys scan redis key
func ScanKeys(conn *gosocketio.Channel, data interface{}) {
	if info, ok := checkOperData(conn, data); ok {
		tmpBytes, _ := json.Marshal(info.Data)
		var scanInfo scanInfoStruct
		err := json.Unmarshal(tmpBytes, &scanInfo)
		if err != nil {
			sendCmdError(conn, "Unmarshal error: "+err.Error())
			return
		}
		c, err := getRedisClient(info.ServerID, info.DB)
		if err != nil {
			sendCmdError(conn, "redis error: "+err.Error())
			return
		}
		defer c.Close()
		iter, keys := scan(conn, c, scanInfo.Key, "", keyScan, scanInfo.Iter, _globalConfigs.System.KeyScanLimits)
		result := scanResultStruct{
			keys, iter,
		}
		conn.Emit("LoadKeys", result)
	}
}

// ScanRemote scan remote
func ScanRemote(conn *gosocketio.Channel, data interface{}) {
	if c, _redisValue, ok := checkRedisValue(conn, data); ok {
		defer c.Close()

		var key = _redisValue.Key
		field, ok := (_redisValue.Val).(string)
		if !ok {
			sendCmdError(conn, "val should be string")
			return
		}
		var size int
		size = _redisValue.Size
		if size == 0 {
			size = _globalConfigs.System.RowScanLimits
		}
		if t, err := keyType(conn, c, key); err == nil {
			if t == "none" {
				sendCmdError(conn, "key is not exists")
				return
			}
			switch t {
			case "string", "list":
				sendCmdError(conn, t+" not support this method!")
				return
			case "set":
				_, vals := scan(conn, c, key, field, setScan, 0, size)
				conn.Emit("ReloadDatas", formatSetAndList(vals))
			case "zset":
				_, vals := scan(conn, c, key, field, zsetScan, 0, size)
				conn.Emit("ReloadDatas", formatZset(vals))
			case "hash":
				_, vals := scan(conn, c, key, field, hashScan, 0, size)
				conn.Emit("ReloadDatas", formatHash(vals))
			default:
				sendCmdError(conn, "underfined type:"+t)
				return
			}
		}
	}
}

// GetKey get value of the key
func GetKey(conn *gosocketio.Channel, data interface{}) {
	if c, _redisValue, ok := checkRedisValue(conn, data); ok {
		defer c.Close()
		// type, ttl, data
		var key = _redisValue.Key
		extra, ok := (_redisValue.Val).(map[string]string)
		var field = ""
		if ok {
			field = extra["field"]
		}
		var size int
		size = _redisValue.Size
		if size == 0 {
			size = _globalConfigs.System.RowScanLimits
		}
		if t, err := keyType(conn, c, key); err == nil {
			if t == "none" {
				sendCmdError(conn, "key is not exists")
				return
			}
			_redisValue.T = t
			// ttl
			cmd := "TTL " + key
			sendCmd(conn, cmd)
			expire, err := redis.Int64(c.Do("TTL", key))
			if err != nil {
				sendCmdError(conn, err.Error())
				return
			}
			sendCmdReceive(conn, expire)
			_redisValue.TTL = expire

			switch t {
			case "string":
				cmd = "GET " + key
				sendCmd(conn, cmd)
				s, err := redis.String(c.Do("GET", key))
				if err != nil {
					sendCmdError(conn, err.Error())
					return
				}
				sendCmdReceive(conn, s)
				_redisValue.Size = 1
				_redisValue.Val = s
			case "list":
				cmd = "LRANGE " + key + " 0 " + strconv.Itoa(_globalConfigs.System.RowScanLimits)
				sendCmd(conn, cmd)
				list, err := redis.Strings(c.Do("LRANGE", key, 0, _globalConfigs.System.RowScanLimits))
				if err != nil {
					sendCmdError(conn, err.Error())
					return
				}
				cmd = "LLEN " + key
				sendCmd(conn, cmd)
				l, err := redis.Int(c.Do("LLEN", key))
				if err != nil {
					sendCmdError(conn, err.Error())
					return
				}
				sendCmdReceive(conn, l)
				_redisValue.Size = l
				_redisValue.Val = formatSetAndList(list)
			case "set":
				_, vals := scan(conn, c, key, field, setScan, 0, size)

				cmd = "SCARD " + key
				sendCmd(conn, cmd)
				l, err := redis.Int(c.Do("SCARD", key))
				if err != nil {
					sendCmdError(conn, err.Error())
					return
				}
				sendCmdReceive(conn, l)
				_redisValue.Size = l
				_redisValue.Val = formatSetAndList(vals)
			case "zset", "hash":
				var method scanType
				var lenMethod string
				if t == "zset" {
					method = zsetScan
					lenMethod = "ZCOUNT"
				} else {
					lenMethod = "HLEN"
					method = hashScan
				}
				_, vals := scan(conn, c, key, field, method, 0, size)
				cmd = lenMethod + " " + key
				var l int
				var err error
				if t == "zset" {
					_redisValue.Val = formatZset(vals)
					cmd += " -inf +inf"
					sendCmd(conn, cmd)
					l, err = redis.Int(c.Do(lenMethod, key, "-inf", "+inf"))
				} else {
					_redisValue.Val = formatHash(vals)
					sendCmd(conn, cmd)
					l, err = redis.Int(c.Do(lenMethod, key))
				}

				if err != nil {
					sendCmdError(conn, err.Error())
					return
				}
				sendCmdReceive(conn, l)
				_redisValue.Size = l
			}

			conn.Emit("ShowRedisValue", _redisValue)
		}
	}
}

func formatSetAndList(datas []string) []map[string]interface{} {
	tmp := make([]map[string]interface{}, 0, 100)
	for i := 0; i < len(datas); i++ {
		row := make(map[string]interface{})
		row["val"] = datas[i]
		row["index"] = i
		tmp = append(tmp, row)
	}
	return tmp
}

func formatHash(datas []string) []map[string]string {
	tmp := make([]map[string]string, 0, 100)
	for i := 0; i < len(datas); i = i + 2 {
		row := make(map[string]string)
		row["field"] = datas[i]
		row["val"] = datas[i+1]
		tmp = append(tmp, row)
	}
	return tmp
}

func formatZset(datas []string) []map[string]interface{} {
	tmp := make([]map[string]interface{}, 0, 100)
	for i := 0; i < len(datas); i = i + 2 {
		row := make(map[string]interface{})
		row["val"] = datas[i]
		row["score"], _ = strconv.ParseFloat(datas[i+1], 10)
		tmp = append(tmp, row)
	}
	return tmp
}

// SetTTL set ttl
func SetTTL(conn *gosocketio.Channel, data interface{}) {
	if c, _redisValue, ok := checkRedisValue(conn, data); ok {
		defer c.Close()
		cmd := "EXPIRE " + _redisValue.Key + " " + strconv.FormatInt(_redisValue.TTL, 10)
		sendCmd(conn, cmd)
		expire, err := redis.Int(c.Do("EXPIRE", _redisValue.Key, _redisValue.TTL))
		if err != nil {
			sendCmdError(conn, "redis error: "+err.Error())
			return
		}
		sendCmdReceive(conn, expire)

		conn.Emit("ReloadTTL", _redisValue.TTL)
		conn.Emit("tip", &info{"success", "set ttl success!", 2})
	}
}

// KeyType type of the key
func KeyType(conn *gosocketio.Channel, data interface{}) {
	if c, _redisValue, ok := checkRedisValue(conn, data); ok {
		defer c.Close()
		s, err := keyType(conn, c, _redisValue.Key)
		if err == nil {
			conn.Emit("KeyType", s)
		}
	}
}

// Rename rename a key
func Rename(conn *gosocketio.Channel, data interface{}) {
	if c, _redisValue, ok := checkRedisValue(conn, data); ok {
		defer c.Close()
		newKey, ok := (_redisValue.Val).(string)
		if !ok {
			sendCmdError(conn, "data should be string of the new key")
			return
		}
		cmd := "RENAME " + _redisValue.Key + " " + newKey
		sendCmd(conn, cmd)
		_, err := c.Do("RENAME", _redisValue.Key, newKey)
		if err != nil {
			sendCmdError(conn, "redis error: "+err.Error())
			return
		}
		conn.Emit("ReloadName", newKey)
		conn.Emit("tip", &info{"success", "rename success!", 2})
	}
}

/*
 none (key不存在)
 string (字符串)
 list (列表)
 set (集合)
 zset (有序集)
 hash (哈希表)
*/
func keyType(conn *gosocketio.Channel, c redis.Conn, key string) (string, error) {
	cmd := "TYPE " + key
	sendCmd(conn, cmd)
	s, err := redis.String(c.Do("TYPE", key))
	if err != nil {
		sendCmdError(conn, err.Error())
		return "", err
	}
	sendCmdReceive(conn, s)
	return s, err
}

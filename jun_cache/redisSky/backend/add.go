package backend

import (
	"encoding/json"
	"fmt"

	"reflect"

	"github.com/garyburd/redigo/redis"
	gosocketio "github.com/graarh/golang-socketio"
)

// AddKey save to redis
func AddKey(conn *gosocketio.Channel, data interface{}) {
	if c, _redisValue, ok := checkRedisValue(conn, data); ok {
		defer c.Close()
		// check whether the key is exists
		exists, err := redis.Int(c.Do("EXISTS", _redisValue.Key))
		if err != nil {
			sendCmdError(conn, err.Error())
			return
		}
		if exists == 1 {
			sendCmdError(conn, "the key is already exists !")
			return
		}
		switch _redisValue.T {
		case "string":
			val, ok := (_redisValue.Val).(string)
			if !ok {
				sendCmdError(conn, "val should be string")
				return
			}
			cmd := "SET " + _redisValue.Key + " " + val
			sendCmd(conn, cmd)
			r, err := c.Do("SET", _redisValue.Key, val)
			if err != nil {
				sendCmdError(conn, err.Error())
				return
			}
			sendCmdReceive(conn, r)
		case "list", "set":
			val, ok := (_redisValue.Val).([]interface{})
			if !ok {
				sendCmdError(conn, "val should be array of string now is "+reflect.ValueOf(_redisValue.Val).Kind().String())
				return
			}
			var method string
			if _redisValue.T == "list" {
				method = "LPUSH"
			} else {
				method = "SADD"
			}
			slice := make([]interface{}, 0, 10)
			slice = append(slice, _redisValue.Key)
			slice = append(slice, val...)
			cmd := method
			for i := 0; i < len(slice); i++ {
				cmd += fmt.Sprintf("%s", slice[i])
			}
			sendCmd(conn, cmd)
			r, err := c.Do(method, slice...)
			if err != nil {
				sendCmdError(conn, err.Error())
				return
			}
			sendCmdReceive(conn, r)
		case "hash":
			if hsetMulti(conn, c, _redisValue) == false {
				return
			}
		case "zset":
			if zaddMulti(conn, c, _redisValue) == false {
				return
			}
		default:
			sendCmdError(conn, "type is not correct")
			return
		}

		conn.Emit("AddKeySuccess", _redisValue.Key)
		conn.Emit("tip", &info{"success", "add success!", 2})
	}
}

// zadd
func zadd(conn *gosocketio.Channel, c redis.Conn, _redisValue redisValue) bool {
	vals, ok := (_redisValue.Val).(map[string]interface{})
	if !ok {
		sendCmdError(conn, "val should be map of string -> int64 or string -> string")
		return false
	}
	var cmd string
	for k, v := range vals {
		kind := reflect.ValueOf(v).Kind()
		if kind != reflect.Int64 && kind != reflect.Int && kind != reflect.String && kind != reflect.Float64 {
			sendCmdError(conn, "val should be map of string -> int or string -> string, now is string -> "+kind.String())
			return false
		}

		cmd = fmt.Sprintf("ZADD %s %d %s", _redisValue.Key, v, k)
		sendCmd(conn, cmd)
		r, err := c.Do("ZADD", _redisValue.Key, v, k)
		if err != nil {
			sendCmdError(conn, err.Error())
			return false
		}
		sendCmdReceive(conn, r)
	}
	return true
}

// zaddMulti
func zaddMulti(conn *gosocketio.Channel, c redis.Conn, _redisValue redisValue) bool {
	var datas []redisData
	tmp, _ := json.Marshal(_redisValue.Val)
	err := json.Unmarshal(tmp, &datas)
	if err != nil {
		sendCmdError(conn, "val should be array of redisData: "+err.Error())
		return false
	}
	var cmd string
	for i := 0; i < len(datas); i++ {
		cmd = fmt.Sprintf("ZADD %s %f %s", _redisValue.Key, datas[i].Score, datas[i].Val)
		sendCmd(conn, cmd)
		r, err := c.Do("ZADD", _redisValue.Key, datas[i].Score, datas[i].Val)
		if err != nil {
			sendCmdError(conn, err.Error())
			return false
		}
		sendCmdReceive(conn, r)
	}
	return true
}

// hset redis hset
func hset(conn *gosocketio.Channel, c redis.Conn, _redisValue redisValue) bool {
	vals, ok := (_redisValue.Val).(map[string]interface{})
	if !ok {
		sendCmdError(conn, "val should be map of string -> int64 or string -> string")
		return false
	}
	var cmd string
	for k, v := range vals {
		kind := reflect.ValueOf(v).Kind()
		if kind != reflect.Int64 && kind != reflect.Int && kind != reflect.String {
			sendCmdError(conn, "val should be map of string -> int or string -> string")
			return false
		}

		cmd = fmt.Sprintf("HSET %s %s %s", _redisValue.Key, k, v)
		sendCmd(conn, cmd)
		r, err := c.Do("HSET", _redisValue.Key, k, v)
		if err != nil {
			sendCmdError(conn, err.Error())
			return false
		}
		sendCmdReceive(conn, r)
	}
	return true
}

// hsetMulti
func hsetMulti(conn *gosocketio.Channel, c redis.Conn, _redisValue redisValue) bool {
	var datas []redisData
	tmp, _ := json.Marshal(_redisValue.Val)
	err := json.Unmarshal(tmp, &datas)
	if err != nil {
		sendCmdError(conn, "val should be array of redisData: "+err.Error())
		return false
	}
	var cmd string
	for i := 0; i < len(datas); i++ {
		cmd = fmt.Sprintf("HSET %s %s %s", _redisValue.Key, datas[i].Field, datas[i].Val)
		sendCmd(conn, cmd)
		r, err := c.Do("HSET", _redisValue.Key, datas[i].Field, datas[i].Val)
		if err != nil {
			sendCmdError(conn, err.Error())
			return false
		}
		sendCmdReceive(conn, r)
	}
	return true
}

// AddRow add one row 2 redis
func AddRow(conn *gosocketio.Channel, data interface{}) {
	if c, _redisValue, ok := checkRedisValue(conn, data); ok {
		defer c.Close()
		t, err := keyType(conn, c, _redisValue.Key)
		if err != nil {
			sendCmdError(conn, err.Error())
			return
		}
		if t != _redisValue.T {
			sendCmdError(conn, "type "+_redisValue.T+" does not match"+t)
			return
		}
		var allowType = [4]string{
			"hash",
			"zset",
			"set",
			"list",
		}
		for i := 0; i < len(allowType); i++ {
			if t == allowType[i] {
				switch t {
				case "set", "list":
					var method string
					v, ok := (_redisValue.Val).(string)
					if ok == false {
						sendCmdError(conn, "val should be string")
						return
					}
					if t == "set" {
						method = "SADD"
					} else {
						method = "LPUSH"
					}
					cmd := method + " " + _redisValue.Key + " " + v
					sendCmd(conn, cmd)
					r, err := c.Do(method, _redisValue.Key, v)
					if err != nil {
						sendCmdError(conn, err.Error())
						return
					}
					sendCmdReceive(conn, r)
				case "zset":
					if zadd(conn, c, _redisValue) == false {
						return
					}
				case "hash":
					if hset(conn, c, _redisValue) == false {
						return
					}
				}
				conn.Emit("ReloadValue", 1)
				conn.Emit("tip", &info{"success", "success!", 2})
				return
			}
		}
		sendCmdError(conn, "type "+t+" does not support")
	}
}

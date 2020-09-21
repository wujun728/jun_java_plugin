package backend

import (
	"encoding/json"
	"fmt"

	"github.com/garyburd/redigo/redis"
	gosocketio "github.com/graarh/golang-socketio"
)

func lrange(conn *gosocketio.Channel, c redis.Conn, key string, start int, end int) ([]string, bool) {
	var cmd string
	cmd = fmt.Sprintf("LRANGE %s %d %d", key, start, end)
	sendCmd(conn, cmd)
	vals, err := redis.Strings(c.Do("LRANGE", key, start, end))
	if err != nil {
		sendCmdError(conn, "redis err:"+err.Error())
		return nil, false
	}
	sendCmdReceive(conn, vals)
	return vals, true
}

// ModifyKey modify one key
func ModifyKey(conn *gosocketio.Channel, data interface{}) {
	if c, _redisValue, ok := checkRedisValue(conn, data); ok {
		defer c.Close()
		var key = _redisValue.Key
		t, err := keyType(conn, c, key)
		var cmd string
		if err == nil {
			switch t {
			case "none":
				sendCmdError(conn, "key: "+key+" is not exists")
			case "string":
				data, ok := (_redisValue.Val).(string)
				if !ok {
					sendCmdError(conn, "val should be string")
					return
				}
				cmd = "SET " + key + " " + data
				sendCmd(conn, cmd)
				r, err := c.Do("SET", key, data)
				if err != nil {
					sendCmdError(conn, "redis error:"+err.Error())
					return
				}
				sendCmdReceive(conn, r)
			default:
				bytes, _ := json.Marshal(_redisValue.Val)
				var _val dataStruct
				var err error
				err = json.Unmarshal(bytes, &_val)
				if err != nil {
					sendCmdError(conn, "val should be dataStruct")
					return
				}
				oldVal := &(_val.OldVal)
				newVal := &(_val.NewVal)
				switch t {
				case "list":
					if vals, ok := lrange(conn, c, _redisValue.Key, _val.Index, _val.Index); ok {
						if len(vals) != 1 {
							sendCmdError(conn, "the index of the list is empty")
							return
						}
						var valInRedis = vals[0]
						if oldVal.Val != valInRedis {
							sendCmdError(conn, "your value: "+valInRedis+" does not match "+oldVal.Val)
							return
						}
						cmd = fmt.Sprintf("LSET %s %d %s", _redisValue.Key, _val.Index, newVal.Val)
						sendCmd(conn, cmd)
						r, err := c.Do("LSET", _redisValue.Key, _val.Index, newVal.Val)
						if err != nil {
							sendCmdError(conn, err.Error())
							return
						}
						sendCmdReceive(conn, r)
					}
				case "set":
					srem(conn, c, _redisValue.Key, oldVal.Val)
					cmd = fmt.Sprintf("SADD %s %s", _redisValue.Key, newVal.Val)
					r, err := c.Do("SADD", _redisValue.Key, newVal.Val)
					if err != nil {
						sendCmdError(conn, "val should be dataStruct")
						return
					}
					sendCmdReceive(conn, r)
				case "zset":
					if oldVal.Val != newVal.Val {
						zrem(conn, c, _redisValue.Key, oldVal.Val)
					}
					cmd = fmt.Sprintf("ZADD %s %f %s", _redisValue.Key, newVal.Score, newVal.Val)
					sendCmd(conn, cmd)
					r, err := c.Do("ZADD", _redisValue.Key, newVal.Score, newVal.Val)
					if err != nil {
						sendCmdError(conn, "redis err "+err.Error())
						return
					}
					sendCmdReceive(conn, r)
				case "hash":
					if oldVal.Field != newVal.Field {
						hdel(conn, c, _redisValue.Key, oldVal.Field)
					}
					cmd = fmt.Sprintf("HSET %s %s %s", _redisValue.Key, newVal.Field, newVal.Val)
					sendCmd(conn, cmd)
					r, err := c.Do("HSET", _redisValue.Key, newVal.Field, newVal.Val)
					if err != nil {
						sendCmdError(conn, "redis err"+err.Error())
						return
					}
					sendCmdReceive(conn, r)
				default:
					sendCmdError(conn, "type is unknown")
				}
			}
		}
		conn.Emit("ReloadValue", _redisValue.Key)
		conn.Emit("tip", &info{"success", "modify success!", 2})
	}
}

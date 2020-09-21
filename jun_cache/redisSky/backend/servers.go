package backend

import (
	"bytes"
	"encoding/json"
	"strconv"

	gosocketio "github.com/graarh/golang-socketio"
)

var socketIOServer *gosocketio.Server

//SetSocketIOServer set socketio server
func SetSocketIOServer(server *gosocketio.Server) {
	socketIOServer = server
}

// redisServer 配置
type redisServer struct {
	ID     int    `json:"id"`
	Name   string `json:"name"`
	Host   string `json:"host"`
	Port   int    `json:"port"`
	Auth   string `json:"auth"`
	DBNums int    `json:"dbNums"`
}

var _maxServerID int

// QueryServers 获取列表
func QueryServers(conn *gosocketio.Channel) {
	conn.Emit("ShowServers", _globalConfigs.Servers)
}

// QueryServer 获取列表
func QueryServer(conn *gosocketio.Channel, serverID int) {
	for index := 0; index < len(_globalConfigs.Servers); index++ {
		if _globalConfigs.Servers[index].ID == serverID {
			conn.Emit("ShowServer", _globalConfigs.Servers[index])
			return
		}
	}
	sendCmdError(conn, "serverID is out of range")
}

// AddServer 增加redis server
func AddServer(conn *gosocketio.Channel, data interface{}) {
	var server redisServer
	_bytes, _ := json.Marshal(data)
	err := json.Unmarshal(_bytes, &server)

	if err != nil {
		sendCmdError(conn, "data sould be struct of redisServer !")
		return
	}
	_maxServerID++
	server.ID = _maxServerID
	_globalConfigs.Servers = append(_globalConfigs.Servers, server)
	saveConf()
	conn.Emit("AddServerSuccess", server)
}

// UpdateServer 更新redisServer
func UpdateServer(conn *gosocketio.Channel, data interface{}) {
	var server redisServer
	_bytes, _ := json.Marshal(data)
	err := json.Unmarshal(_bytes, &server)

	if err != nil {
		sendCmdError(conn, "data sould be struct of redisServer !")
		return
	}
	for i := 0; i < len(_globalConfigs.Servers); i++ {
		if server.ID == _globalConfigs.Servers[i].ID {
			_globalConfigs.Servers[i] = server
			delete(redisClients, server.ID)
		}
	}
	saveConf()
	conn.Emit("UpdateServerSuccess", server)
}

// DelServer del redis server
func DelServer(conn *gosocketio.Channel, serverid int) {

	for i := 0; i < len(_globalConfigs.Servers); i++ {
		if serverid == _globalConfigs.Servers[i].ID {
			delete(redisClients, serverid)
			_globalConfigs.Servers = append(_globalConfigs.Servers[0:i], _globalConfigs.Servers[i+1:]...)
			break
		}
	}
	saveConf()
	conn.Emit("DelServerSuccess", _globalConfigs.Servers)
}

// ServerInfo query server info
func ServerInfo(conn *gosocketio.Channel, serverID int) {
	c, err := getRedisClient(serverID, 0)
	if err != nil {
		sendCmdError(conn, err.Error())
		return
	}
	sendCmd(conn, "INFO")
	infos, err := c.Do("INFO")
	if err != nil {
		sendCmdError(conn, err.Error())
		return
	}
	sendCmdReceive(conn, infos)
	retBytes := bytes.Split(infos.([]byte), []byte("\r\n"))
	ret := make(map[string][]string)
	var currentSection string
	for i := 0; i < len(retBytes); i++ {
		if bytes.HasPrefix(retBytes[i], []byte("#")) {
			currentSection = string(retBytes[i])
		} else {
			ret[currentSection] = append(ret[currentSection], string(retBytes[i]))
		}
	}

	sendCmdReceive(conn, ret)
	conn.Emit("ShowServerInfo", ret)
}

// GetTotalKeysNums get total keys nums
func GetTotalKeysNums(conn *gosocketio.Channel, data interface{}) {
	if data, ok := checkOperData(conn, data); ok {
		sendCmd(conn, "INFO KEYSPACE")
		c, err := getRedisClient(data.ServerID, data.DB)
		if err != nil {
			sendCmdError(conn, err.Error())
			return
		}
		infos, err := c.Do("INFO", "KEYSPACE")
		if err != nil {
			sendCmdError(conn, err.Error())
			return
		}
		retBytes := bytes.Split(infos.([]byte), []byte("\r\n"))
		prefix := []byte("db" + strconv.Itoa(data.DB) + ":")
		for i := 0; i < len(retBytes); i++ {
			if bytes.HasPrefix(retBytes[i], prefix) {
				info := bytes.TrimPrefix(retBytes[i], prefix)
				keyInfos := bytes.Split(info, []byte(","))
				keyPrefix := []byte("keys=")
				for j := 0; j < len(keyInfos); j++ {
					if bytes.HasPrefix(keyInfos[j], keyPrefix) {
						nums := bytes.TrimPrefix(keyInfos[j], keyPrefix)
						conn.Emit("ShowTotalKeysNums", string(nums))
						return
					}
				}
			}
		}
		conn.Emit("ShowTotalKeysNums", 0)
		return
	}
}

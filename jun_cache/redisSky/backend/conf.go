package backend

import (
	"encoding/json"
	"io/ioutil"
	"os"

	gosocketio "github.com/graarh/golang-socketio"
)

type systemConf struct {
	ConnectionTimeout int `json:"connectionTimeout"`
	ExecutionTimeout  int `json:"executionTimeout"`
	KeyScanLimits     int `json:"keyScanLimits"`
	RowScanLimits     int `json:"rowScanLimits"`
	DelRowLimits      int `json:"delRowLimits"`
}

type info struct {
	Type     string `json:"type"`
	Msg      string `json:"msg"`
	Duration int    `json:"duration"`
}

type globalConfigs struct {
	Servers []redisServer `json:"servers"`
	System  systemConf    `json:"system"`
}

var _globalConfigs globalConfigs

// Message 协议
type Message struct {
	Operation string      `json:"operation"`
	Data      interface{} `json:"data"`
	Error     string      `json:"error"`
}

func (message Message) marshal() ([]byte, error) {
	return json.Marshal(message)
}

var isDebug = true

// _configFilePath 配置文件路径
var _configFilePath = "./conf.json"
var _defaultConfig = []byte(`{"servers":[{"id":1,"name":"localhost","host":"127.0.0.1","port":6379,"auth":"","dbNums":15}],"system":{"connectionTimeout":10,"executionTimeout":10,"keyScanLimits":1000,"rowScanLimits":1000,"delRowLimits":1000}}`)

func init() {
	_, err := os.Stat(_configFilePath)
	if os.IsNotExist(err) {
		err = ioutil.WriteFile(_configFilePath, _defaultConfig, 0755)
		checkErr(err)
	}

	conf, err := ioutil.ReadFile(_configFilePath)
	checkErr(err)
	err = json.Unmarshal(conf, &_globalConfigs)
	checkErr(err)
	_maxServerID = 0
	for i := 0; i < len(_globalConfigs.Servers); i++ {
		if _maxServerID < _globalConfigs.Servers[i].ID {
			_maxServerID = _globalConfigs.Servers[i].ID
		}
	}
}

func saveConf() error {
	data, err := json.Marshal(_globalConfigs)
	logErr(err)
	if err == nil {
		err = ioutil.WriteFile(_configFilePath, data, 0755)
	}
	return err
}

// QuerySystemConfigs 获取系统配置信息
func QuerySystemConfigs(conn *gosocketio.Channel) {
	conn.Emit("LoadSystemConfigs", _globalConfigs.System)
}

// UpdateSystemConfigs 更新系统信息
func UpdateSystemConfigs(conn *gosocketio.Channel, data interface{}) {
	var _systemConf systemConf
	var err error
	bytes, _ := json.Marshal(data)
	err = json.Unmarshal(bytes, &_systemConf)
	if err != nil {
		sendCmdError(conn, "data sould be struct of systemConf!")
		return
	}

	_globalConfigs.System = _systemConf
	err = saveConf()
	if err != nil {
		sendCmdError(conn, err.Error())
		return
	}
	conn.Emit("LoadSystemConfigs", _globalConfigs.System)
}

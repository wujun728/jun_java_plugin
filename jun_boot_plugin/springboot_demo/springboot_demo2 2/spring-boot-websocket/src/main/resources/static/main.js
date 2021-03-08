new Vue({
    el: '#talk',
    data: {
        currentUserName: '',
        picked: 'multi',
        selected: '',
        options: '',
        loginOutBox: '',
        multiMsgOutput: '',
        multiMsgInput: '',
        singleMsgOutput: '',
        singleMsgInput: ''
    },
    mounted: function(){
        this.currentUserName = sessionStorage.getItem("userName");
        this.initWebSocket(this.currentUserName);
        this.listAllUser().then(resolve => {
            this.options = resolve;
        });
    },
    methods:　{
        chooseTalkType: function (picked) {
            // console.log(picked);
            // console.log("picked:" + this.picked);
        },
        listAllUser: async function() {
            return await axios.get('/listAllUser')
                        .then(function (response) {
                            console.log(response);
                            if (response.status == 200) {
                                return Promise.resolve(response.data);
                            }
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
        },
        submit: function (type,msg) {
            if (!msg.trim()){
                return;
            }
            var param = new FormData();
            param.append('msg', msg);
            param.append('fromUserName', this.currentUserName);
            if (type == 0){
                this.sendMsg('/sendMultiMsg',param);
                this.multiMsgInput = '';
            } else{
                if (!this.selected) {
                    return alert("请选择私聊对象！");
                }
                param.append('toUserName', this.selected);
                this.sendMsg('/sendSingleMsg',param);
                this.singleMsgInput = '';
                this.singleMsgOutput += this.currentUserName + ":\n" + msg + "\n"
            }
        },
        sendMsg: function(url,param) {
                axios.post(url,param)
                    .then(function (response) {
                        console.log(response);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
        },
        initWebSocket: function(currentUserName) {
            var websocket = null,
                me = this;
            if('WebSocket' in window){
                websocket = new WebSocket('ws://localhost:8888/websocket/' + currentUserName);
            }else{
                alert("该浏览器不支持WebSocket");
            }

            websocket.onopen = function (event) {
                console.log("建立连接");
            }

            websocket.onclose = function (event) {
                console.log("断开连接");
            }

            websocket.onmessage = function (event) {
                me.changeText(JSON.parse(event.data));

            }

            websocket.onerror = function (event) {
                alert("websocket通信发生错误");
            }

            window.onbeforeunload = function (event) {
                websocket.close();
            }
        },
        changeText: function (data) {
            if (data.type == 0){
                this.multiMsgOutput += data.msg;
            } else if(data.type == 1){
                if (this.picked == "multi"){
                    alert("收到私信啦~");
                }
                this.selected = data.fromUserName;
                this.singleMsgOutput += data.fromUserName +　":\n" + data.msg +　"\n";
            } else if (data.type == 2) {
                this.options = data.msg;
            } else{
                this.loginOutBox += data.msg;
            }
        }
    },
    watch: {
        picked: function(value) {
            // console.log("value:" + value);
            // console.log("picked:" + this.picked);
        },
        selected: function(value) {
            console.log("value:" + value);
            // console.log("picked:" + this.picked);
        },
    }
});

<style scoped>
.offset-top-10{
    margin-top: 10px;
}
.overflow-hidden{
    text-overflow: ellipsis;    
    overflow: hidden;
}
.overflow-y-show{
    overflow-y: auto;
}
.hidden{
    display: none;
}
.width100{
    width: 100% !important;
}
.key-list{
    width: 100% !important;
}
.spin-container{
    display: inline-block;
    width: 100%;
    margin: 0 auto;
    height: 50px;
    position: relative;
}
</style>
<style lang="less">
.vertical-center-modal{
    display: flex;
    align-items: center;
    justify-content: center;

    .ivu-modal{
        top: 0;
    }
}
</style>
<template>
    <Row :gutter="16">
        <Col span="5" class="left">
            <Card>
                <Button type="success" long @click="addKeyMoal = true">Add Key</Button>
                <Modal
                    title="Add Key"
                    v-model="addKeyMoal"
                    @on-ok="addKey"
                    class-name="vertical-center-modal">
                    <Form :model="newItem" :label-width="80">
                        <Form-item label="key" >
                            <Input v-model="newItem.key" type="text" placeholder="please input key..."></Input>
                        </Form-item>
                        <Form-item label="type" >
                            <Select v-model="newItem.t" class="width100">
                                <Option value="string">String</Option>
                                <Option value="list">List</Option>
                                <Option value="set">Set</Option>
                                <Option value="zset">Zset</Option>
                                <Option value="hash">Hash</Option>
                            </Select>
                        </Form-item>
                        <Form-item
                            :class="{'hidden': newItem.t != 'set' && newItem.t != 'list'}"
                            v-for="(item, index) in newItem.listVal"
                            :label="'val' + (index + 1)"
                            >
                            <Row>
                                <Col span="18">
                                    <Input type="text" v-model="newItem.listVal[index]" placeholder="please input ..."></Input>
                                </Col>
                                <Col span="4" offset="1">
                                    <Button type="ghost" @click="handleRemoveList(index)">del</Button>
                                </Col>
                            </Row>
                        </Form-item>
                        <Form-item :class="{'hidden': newItem.t != 'set' && newItem.t != 'list'}">
                            <Row>
                                <Col span="12">
                                    <Button type="dashed" long @click="handleAddList" icon="plus-round">add</Button>
                                </Col>
                            </Row>
                        </Form-item>
                        <Form-item
                            :class="{'hidden': newItem.t != 'hash' }"
                            v-for="(item, index) in newItem.hashVal"
                            :key="index"
                            :label="'val' + (index + 1)"
                            >
                            <Row>
                                <Col span="9">
                                    <Input type="text" v-model="newItem.hashVal[index].field" placeholder="please input field..."></Input>
                                </Col>
                                <Col span="9" offset="1">
                                    <Input type="text" v-model="newItem.hashVal[index].val" placeholder="please input val..."></Input>
                                </Col>
                                <Col span="4" offset="1">
                                    <Button type="ghost" @click="handleRemoveHash(index)">del</Button>
                                </Col>
                            </Row>
                        </Form-item>
                        <Form-item :class="{'hidden': newItem.t != 'hash' }">
                            <Row>
                                <Col span="12">
                                    <Button type="dashed" long @click="handleAddHash" icon="plus-round">add</Button>
                                </Col>
                            </Row>
                        </Form-item>
                        <Form-item
                            :class="{'hidden': newItem.t != 'zset' }"
                            v-for="(item, index) in newItem.zsetVal"
                            :key="index"
                            :label="'val' + (index + 1)"
                            >
                            <Row>
                                <Col span="9">
                                    <Input type="text" v-model="newItem.zsetVal[index].val" placeholder="please input val..."></Input>
                                </Col>
                                <Col span="9" offset="1">
                                    <Input-number v-model="newItem.zsetVal[index].score" placeholder="please input score..."></Input-number>
                                </Col>
                                <Col span="4" offset="1">
                                    <Button type="ghost" @click="handleRemoveZset(index)">del</Button>
                                </Col>
                            </Row>
                        </Form-item>
                        <Form-item :class="{'hidden': newItem.t != 'zset' }">
                            <Row>
                                <Col span="12">
                                    <Button type="dashed" long @click="handleAddZset" icon="plus-round">add</Button>
                                </Col>
                            </Row>
                        </Form-item>
                        <Form-item label="value" :class="{'hidden': newItem.t != 'string' }">
                            <Input v-model="newItem.stringVal" type="textarea" :autosize="{minRows: 4,maxRows: 6}" placeholder="please input string val..."></Input>
                        </Form-item> 
                    </Form>
                </Modal>
                <div class="offset-top-10">
                    <Select v-model="serverdb" class="width100">
                        <Option v-for="item in server.dbNums" :value="(item - 1)" :key="(item-1)">
                        db{{ item -1}}
                        </Option>
                    </Select>
                </div>
                <div class="offset-top-10">
                    <Input v-model="inputKey" placeholder="redis key..." class="width100"></Input>
                </div>

                <div style="border-bottom: 1px solid #e9e9e9;padding-bottom:6px;margin:6px 0;">
                    <Checkbox
                        :indeterminate="indeterminate"
                        :value="checkAll"
                        @click.prevent.native="handleCheckAll">全选</Checkbox>
                    (<span>{{currentKeysNums}}</span>/<span>{{totalKeysNums}}</span>)
                    <br/>
                    <Button style="margin-left:3px;" type="ghost" @click="exportMoal = true" v-if="showMutilFuncBtn">export</Button>
                    <Button style="margin-left:3px;" type="ghost" @click="delKeys" v-if="showMutilFuncBtn">delete</Button>
                    <Button style="margin-left:3px;" type="ghost" @click="getKeys">reload</Button>
                    <Modal
                        title="Export keys 2 mongodb"
                        v-model="exportMoal"
                        @on-ok="export2mongodb"
                        class-name="vertical-center-modal">
                        <Form :model="mongodb" :label-width="80">
                            <Form-item label="task" >
                                <Input v-model="mongodb.task" type="text" placeholder="please input mongodb task name..."></Input>
                            </Form-item>
                            <Form-item label="addr" >
                                <Input v-model="mongodb.addr" type="text" placeholder="please input mongodb addr..."></Input>
                            </Form-item>
                            <Form-item label="port" >
                                <Input v-model="mongodb.port" type="text" placeholder="please input mongodb port..."></Input>
                            </Form-item>
                            <Form-item label="database" >
                                <Input v-model="mongodb.database" type="text" placeholder="please input mongodb database..."></Input>
                            </Form-item>
                            <Form-item label="username" >
                                <Input v-model="mongodb.username" type="text" placeholder="please input mongodb username..."></Input>
                            </Form-item>
                            <Form-item label="password" >
                                <Input v-model="mongodb.password" type="text" placeholder="please input mongodb password..."></Input>
                            </Form-item>
                            <Form-item label="collection" >
                                <Input v-model="mongodb.collection" type="text" placeholder="please input mongodb collection..."></Input>
                            </Form-item>
                            <Form-item label="maxChipSize" >
                                <Input-number v-model="mongodb.maxChipSize" :min="10000" :step="100000" placeholder="please input mongodb maxChipSize..."></Input-number>
                            </Form-item>
                        </Form>
                    </Modal>
                </div>
                <div class="overflow-y-show" ref="keysCard" :style="{ 'height': keysCardHeight}" v-infinite-scroll="getKeys" infinite-scroll-disabled="busy" infinite-scroll-distance="10">                    
                    <Checkbox-group v-model="checkAllGroup" @on-change="checkAllGroupChange">
                        <Checkbox class="key-list" v-for="item in keys" :label="item" ><span @click.prevent="jumpLink(item)">{{item}}</span></Checkbox>
                    </Checkbox-group>
                    <div class="spin-container" v-if="loadingMore">
                        <Spin fix></Spin>
                    </div>
                </div>
            </Card>
        </Col>
        <Col span="19">
            <Card>
                <router-view v-on:refresh="getKeys"></router-view>
            </Card>
        </Col>
        <Modal
            v-model="exportSuccessModal"
            @on-ok="exportPorcess"
            class-name="vertical-center-modal">
            <p>导出任务正在后台进行，点击确定查看进度！</p>
        </Modal>
    </Row>
    
</template>

<script>
    export default {
        data(){
            return {
                mongodb: {
                    addr: "localhost",
                    database: "default",
                    port: "27017",
                    username: "",
                    password: "",
                    collection: "default",
                    task: "",
                    maxChipSize: 100000,
                },
                loadingMore: true,
                exportSuccessModal: false,
                exportMoal: false,
                indeterminate: true,
                checkAll: false,
                checkAllGroup: [],
                inputKey: "",
                serverdb: 0,
                totalKeysNums: 0,
                addKeyMoal: false,
                keys: [],// this.getKeys(),
                keysIter: false,
                newItem: {
                    t: 'string',
                    stringVal: '',
                    key: '',
                    listVal: [''],
                    zsetVal: [{val:'', score:0}],
                    hashVal: [{field:'', val:''}]
                }
            }
        },        
        computed: {
            currentKeysNums(){
                if ( typeof this.keys == 'undefined') { return 0;}
                return this.keys.length;
            },
            keysCardHeight(){
                return window.innerHeight - 260 +"px";
            },
            serverid : function(){
                return parseInt(this.$route.params.serverid);
            },
            server(){
                for (var i = this.$store.state.servers.length - 1; i >= 0; i--) {
                    if (this.$store.state.servers[i]["id"] == this.serverid) {
                        return this.$store.state.servers[i];
                    }
                }
                return {dbNums:0, id:0}
            },
            showMutilFuncBtn(){
                return this.checkAllGroup.length > 0 ? true : false;
            }
        },
        watch: {
            '$route': 'reload',
            // 如果 question 发生改变，这个函数就会运行
            inputKey () {
                this.initKeys();
                this.getKeys();
            },
            serverdb (){
                this.initKeys();                
                this.getKeys();                
            }
        },
        methods: {
            initKeys(){
                this.keys = [];
                this.keysIter = false;
            },
            getReqInfo(){
                var info = {};
                info.serverid = this.server.id;
                info.db = parseInt( this.serverdb );
                return info;
            },
            exportPorcess(){
                this.$router.push("/export/process");
            },
            delKeys(){
                var info = this.getReqInfo();
                info.data = this.checkAllGroup;
                this.$socket.emit("DelKeys", info)
            },
            export2mongodb(){                
                var info = this.getReqInfo();
                info.data = {
                    mongodb: this.mongodb,
                    task: this.mongodb.task,
                    keys: this.checkAllGroup,
                };
                this.$socket.emit("Export2mongodb", info)
            },
            handleCheckAll () {
                if (this.indeterminate) {
                    this.checkAll = false;
                } else {
                    this.checkAll = !this.checkAll;
                }
                this.indeterminate = false;
                if (this.checkAll) {
                    this.checkAllGroup = this.keys;
                } else {
                    this.checkAllGroup = [];
                }
            },
            checkAllGroupChange (data) {
                if (data.length === this.keys.length && data.length > 0) {
                    this.indeterminate = false;
                    this.checkAll = true;
                } else if (data.length > 0) {
                    this.indeterminate = true;
                    this.checkAll = false;
                } else {
                    this.indeterminate = false;
                    this.checkAll = false;
                }
            },
            getKeys: _.debounce(function(){
                if (this.keysIter === 0) {return;}
                this.loadingMore = true;
                var info = this.getReqInfo();
                info.data = {};
                info.data.key = this.inputKey;
                info.data.iter = parseInt( this.keysIter );
                this.$socket.emit("ScanKeys", info)
                this.$socket.emit("GetTotalKeysNums", info); 
            }, 200),
            handleAddList () {
                this.newItem.listVal.push('');
            },
            handleRemoveList (index) {
                this.newItem.listVal.splice(index, 1);
            },
            handleAddHash () {
                this.newItem.hashVal.push({field:'', val:''});
            },
            handleRemoveHash (index) {
                this.newItem.hashVal.splice(index, 1);
            },
            handleAddZset () {
                this.newItem.zsetVal.push({val:'', score:0});
            },
            handleRemoveZset (index) {
                this.newItem.zsetVal.splice(index, 1);
            },
            reload:  function(newRouter, oldRouter){
                if (this.serverid != this.$route.params.serverid || this.serverdb != this.$route.params.db) {
                    this.inputKey = "";
                    this.serverid = parseInt( this.$route.params.serverid );
                    this.initKeys();
                    this.getKeys();
                }
            },
            jumpLink(item){
                var url = '/serverid/'+ this.serverid + '/db/' + (this.serverdb ? parseInt(this.serverdb) : 0) +'/key/'+ item;
                this.$router.push(url);
            },
            addKey(){
                // add one key to redis
                var info = this.getReqInfo();
                info.data = {}
                info.data.key = this.newItem.key;
                if (this.newItem.t == 'string') {
                    info.data.val = this.newItem.stringVal;
                }else if(this.newItem.t == 'set' || this.newItem.t == 'list'){
                    info.data.val = this.newItem.listVal;
                }else if(this.newItem.t == 'zset'){                    
                    info.data.val = this.newItem.zsetVal;
                }else if(this.newItem.t == 'hash'){
                    info.data.val = this.newItem.hashVal;
                }else{
                    this.$Notice.error({
                        title: 'Error',
                        desc: "unknown type : " + this.newItem.t
                    });
                    return;
                }
                info.data.t = this.newItem.t;
                this.$socket.emit("AddKey", info);
            }
        },
        socket:{
            events:{
                LoadKeys(info){
                    if (this.keysIter) {
                        this.checkAllGroup = [];
                    }
                    if (info.keys !== null) {
                        this.keys.unshift.apply(this.keys, info.keys );
                        this.keysIter = info.iter;
                    }
                    this.keys.sort();
                    this.loadingMore = false;
                },
                AddKeySuccess(key){
                    this.keys.push(key);
                },
                ReloadKeys(){
                    this.initKeys();
                    this.getKeys();                    
                },
                ShowTotalKeysNums(nums){
                    this.totalKeysNums = nums;
                },
                AddExportTaskSuccess(){
                    this.exportSuccessModal = true;
                }
            }
        }

       
    }
</script>
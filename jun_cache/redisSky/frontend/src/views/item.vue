<style scoped>
.width100{
    width: 100% !important;
}
.offset-top-10{
    margin-top: 10px;
}
.offset-left-10{
    margin-left: 10px;
}
.hidden{
    display: none;
}
</style>
<template>
    <Row :gutter="16" :class="{'hidden': dataValue.t == 'none'}">
        <Col span="6">
            <span :style="{width: '20%'}">{{dataValue.t}}: </span><Input v-model="key" :style="{width: '80%'}"></Input>
        </Col>
        <Col span="8">            
            <span>size: {{dataValue.size}}</span>
            <span>TTL: <Input v-model="dataValue.ttl" :style="{width: '60%'}"></Input></span>
        </Col>
        <Col span="10">
            <span :style="{float:'right'}">
                <Button @click="routeReload">Reload</Button>
                <Button @click="setTTL" type="info">Set TTL</Button>
                <Button @click="rename" type="primary">rename</Button>
                <Button @click="deleteKey" type="warning">Delete</Button>
            </span>
        </Col>
    
        <Col span="24" :class="{'hidden': dataValue.t == 'string'}">
            <Row class="offset-top-10">
                <Col span="17">
                    <Table border height="200" size="small" :columns="columns" :data="data" :highlight-row="true" @on-row-click="viewData" @on-current-change="selectRow"></Table>  
                </Col>
                <Col span="7">
                    <Button class="offset-top-10 offset-left-10" type="ghost" long @click="addRowModal = true"><Icon type="plus-round" :color="'green'"></Icon>Add Row</Button>
                    <Modal
                        v-model="addRowModal"
                        title="add row"
                        width="800"
                        @on-ok="addRow">
                        <Form :model="newItem" :label-width="80">
                            <Form-item label="score" :class="{'hidden': dataValue.t != 'zset'}">
                                <Input-number class="width100" v-model="newItem.score" type="text" placeholder="please input score..."></Input-number>
                            </Form-item>
                            <Form-item label="field" :class="{'hidden': dataValue.t != 'hash'}">
                                <Input v-model="newItem.field" type="textarea" :autosize="{minRows: 2,maxRows: 2}" placeholder="please input field..."></Input>
                            </Form-item>
                            <Form-item label="value">
                                <Input v-model="newItem.value" type="textarea" :autosize="{minRows: 4,maxRows: 6}" placeholder="please input value..."></Input>
                            </Form-item> 
                        </Form>
                    </Modal>
                    <Button class="offset-top-10 offset-left-10" type="ghost" long @click="delRow" >
                        <span>
                            <Icon type="android-delete" :color="'red'"></Icon>Delete Row
                        </span>
                    </Button>

                    <Input class="offset-top-10 offset-left-10" :class="{'hidden': dataValue.t == 'list'}" v-model="searchField" placeholder="search field remote..."></Input>
                    <Input class="offset-top-10 offset-left-10" v-model="searchKey" placeholder="search local..."></Input>

                    <Col class="offset-top-10 offset-left-10" span="24">
                        <span>scan nums: </span>
                        <Input-number :max="size" :min="1" :step="1000" v-model="scanNums"></Input-number>
                    </Col>
                </Col>
            </Row>
        </Col>

        <Col span="24" :class="{'hidden': dataValue.t != 'zset'}">
            <Row class="offset-top-10">
                <Col span="24">Score:</Col>
                <Col span="24"><Input :disabled="!selectedRow" v-model="score"></Input></Col>
            </Row>
        </Col>

        <Col span="24" :class="{'hidden': dataValue.t != 'hash'}">
            <Row class="offset-top-10">
                <Col span="16">
                    <p>field({{fieldBytes}} bytes): </p>
                </Col>
                <Col span="8">
                    <p>
                        <span>View as：</span>
                        <Select v-model="fieldType" :style="{width:'60%', float:'right'}">
                            <Option value="Text">Plain Text</Option>
                            <Option value="Json">Json</Option>
                        </Select>
                    </p>
                </Col>
            </Row>
            <Row class="offset-top-10">
                <Input :disabled="!selectedRow" v-model="field" type="textarea" :autosize="{minRows: 5,maxRows: 5}"></Input>
            </Row>
        </Col>

        <Col span="24">
            <Row class="offset-top-10">
                <Col span="16">
                    <p>Value({{valueBytes}} bytes): </p>
                </Col>
                <Col span="8">
                    <p>
                        <span>View as：</span>
                        <Select v-model="valueType" :style="{width:'60%', float:'right'}">
                            <Option value="Text">Plain Text</Option>
                            <Option value="Json">Json</Option>
                        </Select>
                    </p>
                </Col>
            </Row>
            <Row class="offset-top-10">
                <Input :disabled="dataValue.t != 'string' && !selectedRow" v-model="editVal" type="textarea" :autosize="{minRows: dataValue.t == 'string' ? 20 : 10, maxRows: 50}"></Input>
            </Row>
        </Col>

        <Col span="24">
            <Row class="offset-top-10">
                <Button @click="modifyKey" type="info" :style="{float:'right'}">Save</Button>
            </Row>
        </Col>
    </Row>
</template>

<script>
import Vue from 'vue';
    export default {
        data(){
            return {
                server: {
                    id: 0,
                    db: 0,                    
                },
                dataValue:{
                    key: "",
                    t: "list",
                    ttl: -1,
                    val: {}
                },
                key: "",
                field: "",
                valueType: "Text",
                fieldType: "Text",
                searchKey: "",
                searchField: "",
                editVal: '',
                addRowModal: false,
                size: 10000,
                score: 1,
                selectedRow: false,
                scanNums: 1000,
                columns: [
                    {
                        title: 'row',
                        ellipsis: true,
                        type: 'index',
                        width: '15%',
                    }
                ],
                data: [],
                newItem: {
                    field: '',
                    value: '',
                    score:0
                }
            }
        },
        computed: {
            fieldBytes : function(){
                return this.getBytes(this.field);
            },
            valueBytes: function(){                
                return this.getBytes(this.editVal);
            }
        },
        created () {
            // 组件创建完后获取数据，
            // 此时 data 已经被 observed 了
            this.routeReload();
        },
        watch: {
            // 如果路由有变化，会再次执行该方法
            '$route': 'routeReload',
            valueType: function(){
                this.formatVal(this.editVal);
            },
            fieldType: function(){
                if (this.fieldType == 'Json') {
                    this.field = this.format(this.field)
                }else if(this.fieldType == 'Text'){
                    this.field = this.format(this.field, true)
                }                 
            },
            searchKey: function(){
                if (this.dataValue.t == "string") {return}
                if (this.searchKey == "") {
                    this.data = this.dataValue.val;
                }
                var tmp = [];
                var reg = new RegExp(this.searchKey)
                for (var i = 0; i < this.dataValue.val.length; i++) {
                    if (reg.test(this.dataValue.val[i].field) || reg.test(this.dataValue.val[i].val) ) {
                        tmp.push(this.dataValue.val[i]);
                    }
                }
                this.data = tmp;
            },
            searchField: function(){
                this.scanRemote();
            },
            scanNums: function(){
                this.scanRemote();
            },
            selectedRow: function(){
                if (this.selectedRow === false) {
                    this.score = 0;
                    this.field = "";
                    this.editVal = "";
                }else{
                    this.score = this.selectedRow.score;
                    this.field = this.selectedRow.field;
                    this.formatVal(this.selectedRow.val);
                }
            }
        },
        methods: {
            scanRemote: _.debounce(function(){
                var info = this.getReqData(); 
                info.data.val = this.searchField;
                this.$socket.emit("ScanRemote", info)
            }, 200),
            routeReload(){
                this.editVal = "";
                this.data = [];
                this.selectedRow = false;
                this.dataValue.key = this.key = this.$route.params.key;
                this.fetchData();
            },
            checkKeyChanged(){
                if (this.dataValue.key == ""){
                    return false;
                }
                if (this.key != this.dataValue.key){
                    this.$Message.error("key is changed, please rename first!");
                    return false;
                }
                return true;
            },
            getReqData(){
                var info = {};
                info.db = parseInt(this.server.db);
                info.serverid = parseInt( this.server.id );
                info.data = this.dataValue;
                info.data.ttl = parseInt(info.data.ttl);
                info.data.size = this.scanNums
                return info;
            },
            setTTL: function(){
                if (this.checkKeyChanged()) {
                    this.$Modal.confirm({
                        content: '<p>Sure set ttl to '+this.dataValue.ttl+'?</p>',
                        onOk: () => {
                            var info = this.getReqData();                    
                            this.$socket.emit("SetTTL", info)
                        }
                    });
                }
            },
            rename: function(){
                this.$Modal.confirm({
                    content: '<p>Sure rename to '+this.key+'?</p>',
                    onOk: () => {
                        var info = this.getReqData();
                        info.data.val = this.key;        
                        this.$socket.emit("Rename", info)
                    }
                });
            },
            deleteKey: function(){
                if (this.checkKeyChanged()) {
                    this.$Modal.confirm({
                        content: '<p>Sure delete '+this.key+'? This could not be recoverd</p>',
                        onOk: () => {
                            var info = this.getReqData();
                            this.$socket.emit("DelKey", info)
                        }
                    });
                }
            },
            addRow: function(){
                if (this.checkKeyChanged()) {
                    var info = this.getReqData();
                    this.addRowModal = false;
                    if (info.data.t == 'set' || info.data.t == 'list') {
                        info.data.val = this.newItem.value;
                    }else if(info.data.t == 'zset'){
                        info.data.val = {}
                        info.data.val[this.newItem.value] = this.newItem.score;
                    }else if(info.data.t == 'hash'){
                        info.data.val = {}
                        info.data.val[this.newItem.field] = this.newItem.value;
                    }else{
                        this.$Notice.error({
                            title: 'Error',
                            desc: "unSupport type : "+ this.data.t
                        });
                        return;
                    }
                    this.$socket.emit("AddRow", info)
                }
                
            },
            formatVal: function(val){
                if (this.valueType == 'Json') {
                    this.editVal = this.format(val)
                }else if(this.valueType == 'Text'){
                    this.editVal = this.format(val, true)
                }
            },
            viewData: function (info){
                this.formatVal(info.val);
                this.field = info.field;
            },
            fetchData(){
                this.server.id = this.$route.params.serverid;
                this.server.db = this.$route.params.db;
                var info = this.getReqData();
                this.$socket.emit("GetKey", info)
                // ajax 获取数据
            },
            delRow () {
                var info = this.getReqData();
                if (info.data.t == 'set' || info.data.t == 'zset') {
                    info.data.val = this.format(this.selectedRow.val, true);
                }else if(info.data.t == 'hash'){
                    info.data.val = this.format(this.selectedRow.field, true);                    
                }else if(info.data.t == 'list'){
                    var tmp = {}
                    tmp.index = this.selectedRow.index;
                    tmp.oldVal = {
                        key: this.key,
                        val: this.selectedRow.val
                    }
                    info.data.val = tmp
                }else{
                    this.$Message.error("unSupport type : "+ this.data.t, 2000);
                    return;
                }
                this.$socket.emit("DelRow", info);
            },
            modifyKey(){
                var info = this.getReqData();
                var newVal = this.format(this.editVal, true);
                if (info.data.t == 'string'){
                    info.data.val = newVal
                }else if (info.data.t =='set'){
                    var tmp = {};
                    tmp.oldVal = {
                        val: this.selectedRow.val
                    }
                    tmp.newVal = {
                        val: newVal
                    }
                    info.data.val = tmp
                }else if (info.data.t == 'zset') {
                    var tmp = {};
                    tmp.oldVal = {
                        field: "",
                        score: this.selectedRow.score,
                        val: this.selectedRow.val
                    }
                    tmp.newVal = {
                        field: "",
                        score: parseInt(this.score),
                        val: newVal
                    }
                    info.data.val = tmp
                }else if(info.data.t == 'hash'){
                    var tmp = {};
                    tmp.oldVal = {
                        field: this.selectedRow.field,
                        val: this.selectedRow.val
                    }
                    tmp.newVal = {
                        field: this.field,
                        val: newVal
                    }
                    info.data.val = tmp
                }else if(info.data.t == 'list'){
                    var tmp = {};
                    tmp.index = this.selectedRow.index;
                    tmp.oldVal = {
                        val: this.selectedRow.val
                    }
                    tmp.newVal = {
                        val: this.editVal
                    }
                    info.data.val = tmp
                }else{
                    this.$Message.error("unSupport type : "+ this.data.t, 2000);
                    return;
                }
                this.$socket.emit("ModifyKey", info);
            },
            selectRow (currentRow, oldCurrentRow){
                this.selectedRow = currentRow;
            },
            getBytes: function(str){ 
                if (typeof(str) == 'undefined') return 0; 
                var realLength = 0;  
                for (var i = 0; i < str.length; i++){  
                    var charCode = str.charCodeAt(i);  
                    if (charCode >= 0 && charCode <= 128)   
                    realLength += 1;  
                    else   
                    realLength += 2;  
                }  
                return realLength;  
            },
            format: function format(txt,compress/*是否为压缩模式*/){/* 格式化JSON源码(对象转换为JSON文本) */
                // 参考 http://blog.csdn.net/macwhirr123/article/details/50686841
                if ( typeof(txt) == 'undefined' ) return '';
                var indentChar = '    ';     
                if(/^\s*$/.test(txt)){
                    return '';     
                }     
                try{var data=eval('('+txt+')');}     
                catch(e){     
                    return txt;     
                };     
                var draw=[],last=false,This=this,line=compress?'':'\n',nodeCount=0,maxDepth=0;     
                     
                var notify=function(name,value,isLast,indent/*缩进*/,formObj){     
                    nodeCount++;/*节点计数*/    
                    for (var i=0,tab='';i<indent;i++ )tab+=indentChar;/* 缩进HTML */    
                    tab=compress?'':tab;/*压缩模式忽略缩进*/    
                    maxDepth=++indent;/*缩进递增并记录*/    
                    if(value&&value.constructor==Array){/*处理数组*/    
                        draw.push(tab+(formObj?('"'+name+'":'):'')+'['+line);/*缩进'[' 然后换行*/    
                        for (var i=0;i<value.length;i++)     
                            notify(i,value[i],i==value.length-1,indent,false);     
                        draw.push(tab+']'+(isLast?line:(','+line)));/*缩进']'换行,若非尾元素则添加逗号*/    
                    }else if(value&&typeof value=='object'){/*处理对象*/    
                        draw.push(tab+(formObj?('"'+name+'":'):'')+'{'+line);/*缩进'{' 然后换行*/    
                        var len=0,i=0;     
                        for(var key in value)len++;     
                        for(var key in value)notify(key,value[key],++i==len,indent,true);     
                        draw.push(tab+'}'+(isLast?line:(','+line)));/*缩进'}'换行,若非尾元素则添加逗号*/    
                    }else{     
                        if(typeof value=='string')value='"'+value+'"';     
                        draw.push(tab+(formObj?('"'+name+'":'):'')+value+(isLast?'':',')+line);     
                    };     
                };     
                var isLast=true,indent=0;     
                notify('',data,isLast,indent,false);     
                return draw.join('');     
            }

        },
        socket:{
            events:{
                ReloadTTL(ttl){
                    this.dataValue.ttl = ttl;
                },
                ReloadValue(){
                    this.routeReload();
                },
                ReloadName(newKey){
                    this.$route.params.key = this.key = newKey;
                },
                ReloadDatas(vals){
                    this.data = vals;                    
                },
                DelSuccess(){
                    this.dataValue.t = 'none';
                    this.$emit("refresh");                    
                },
                ShowRedisValue(redisValue){
                    this.newItem = {
                        field: '',
                        value: '',
                        score:0
                    };
                    this.dataValue = redisValue;
                    this.$route.params.key = this.key = redisValue.key;
                    this.columns = [
                        {
                            title: 'row',
                            ellipsis: true,
                            type: 'index',
                            width: '15%',
                        },{
                            title: 'val',
                            key: 'val',
                            ellipsis: true,
                        }
                    ];

                    if (redisValue.t == 'hash') {
                        this.columns.splice(1, 0, {
                            title: 'field',
                            key: 'field',
                            ellipsis: true,
                        });
                        if (redisValue.val != null){
                            redisValue.val.sort(function(a, b){
                                return b.field < a.field;
                            });
                        }
                    }
                    if (redisValue.t == 'zset') {
                        this.columns.splice(1, 0, {
                            title: 'score',
                            key: 'score',
                            sortable: true,
                            width: '20%',                      
                        });
                        if (redisValue.val != null){
                            redisValue.val.sort(function(a, b){
                                return b.score - a.score;
                            });
                        }
                    }
                    switch (redisValue.t){
                        case "string":
                            this.editVal = redisValue.val;
                            break;
                        default:
                            this.data = redisValue.val;
                    }
                    
                },
                DelRow(){
                    this.scanRemote();
                }
            }
        }

    }
</script>
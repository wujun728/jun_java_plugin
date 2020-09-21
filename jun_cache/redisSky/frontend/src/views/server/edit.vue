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
    <Row :gutter="16">       
        <Col span="24">
            <Form ref="server" :rules="ruleValidate" :model="server" :label-width="200">
                <Form-item prop="name" label="redis server name">
                    <Input v-model="server.name" placeholder="please input redis server's name..."></Input>
                </Form-item>
                <Form-item prop="host" label="redis server host">
                    <Input v-model="server.host" placeholder="please input redis server's host..."></Input>
                </Form-item>
                <Form-item prop="port" label="redis server port">
                    <Input-number v-model="server.port" placeholder="please input redis server's port ..." :min="1" :step="1"></Input-number>
                </Form-item>
                <Form-item prop="dbNums" label="redis server dbNums">
                    <Input-number v-model="server.dbNums" placeholder="please input redis dbNums..." :min="1" :step="1"></Input-number>
                </Form-item>
                <Form-item label="redis server auth">
                    <Input v-model="server.auth" placeholder="please input redis server auth"></Input>
                </Form-item>
                <Form-item>
                    <Button type="primary"  @click="handleSubmit('server')">提交</Button>
                    <Button type="ghost" @click="handleReset('server')" style="margin-left: 8px">取消</Button>
                </Form-item>
            </Form> 
        </Col>
    </Row>
</template>

<script>
    export default {
        data(){
            return {                
                server: {},
                ruleValidate: {
                    name: [
                        { required: true, trigger: 'blur' }
                    ],
                    host: [
                        { required: true, trigger: 'blur' }
                    ],
                    port: [
                        { type: "integer", min:1, required: true, trigger: 'blur' }
                    ],
                    dbNums: [
                        { type: "integer", min:1, required: true, trigger: 'blur' }
                    ]                    
                }
            }
        },
        computed: {
            
        },
        created () {
            // 组件创建完后获取数据，
            // 此时 data 已经被 observed 了
            this.fetchData();
        },
        watch: {
            // 如果路由有变化，会再次执行该方法
            '$route': 'fetchData',           
        },
        methods: {
            fetchData(){
                var serverid = this.$route.params.serverid;
                if (serverid){
                    this.$socket.emit("QueryServer", parseInt(serverid));
                }else{
                    this.server = {
                        id: 0,
                        name: "default",
                        host: "localhost",
                        port: 6379,
                        dbNums: 12,
                        auth: "",
                    };
                }
            },
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        if (this.server.id > 0){
                            this.$socket.emit("UpdateServer", this.server)
                        }else{
                            this.$socket.emit("AddServer", this.server)
                        }
                    } else {
                        this.$Message.error('validate error!');
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            }
        }
        ,socket:{
            events:{
                AddServerSuccess(server){
                    this.server = server;
                    this.$Message.success('add success!');
                    this.$store.dispatch('addServer', server);
                },
                UpdateServerSuccess(server){
                    this.server = server;
                    this.$Message.success('update success!');
                    this.$store.dispatch('updateServer', server);
                },
                ShowServer(server){
                    this.server = server;
                }
            }
        }

    }
</script>
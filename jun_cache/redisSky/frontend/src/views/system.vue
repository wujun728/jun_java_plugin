/*<style scoped>
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
            <Form ref="sysConfigs" :rules="ruleValidate" :model="sysConfigs" :label-width="180">
                <Form-item prop="connectionTimeout" label="connectionTimeout">
                    <Input-number v-model="sysConfigs.connectionTimeout" placeholder="please input connection timeout..." :min="1" :step="100"></Input-number>
                </Form-item>
                <Form-item prop="executionTimeout" label="executionTimeout">
                    <Input-number v-model="sysConfigs.executionTimeout" placeholder="please input execution timeout..." :min="1" :step="100"></Input-number>
                </Form-item>
                <Form-item prop="keyScanLimits" label="keyScanLimits">
                    <Input-number v-model="sysConfigs.keyScanLimits" placeholder="please input key scan limits..." :min="100" :step="100"></Input-number>
                </Form-item>
                <Form-item prop="rowScanLimits" label="rowScanLimits">
                    <Input-number v-model="sysConfigs.rowScanLimits" placeholder="please input row scan limits..." :min="100" :step="100"></Input-number>
                </Form-item>
                <Form-item prop="delRowLimits" label="delRowLimits">
                    <Input-number v-model="sysConfigs.delRowLimits" placeholder="please input del row limits..." :min="100" :step="100"></Input-number>
                </Form-item>
                <Form-item>
                    <Button type="primary"  @click="handleSubmit('sysConfigs')">提交</Button>
                    <Button type="ghost" @click="handleReset('sysConfigs')" style="margin-left: 8px">取消</Button>
                </Form-item>
            </Form> 
        </Col>
    </Row>
</template>

<script>
    export default {
        data(){
            return {
                sysConfigs: {
                    connectionTimeout: 0,
                    executionTimeout: 0,
                    keyScanLimits: 0,
                    rowScanLimits: 0,
                    delRowLimits: 0,
                },
                ruleValidate: {
                    connectionTimeout: [
                        { type: "integer", min:1, required: true, trigger: 'blur' }
                    ],
                    executionTimeout: [
                        { type: "integer", min:1, required: true, trigger: 'blur' }
                    ],
                    keyScanLimits: [
                        { type: "integer", min:1, required: true, trigger: 'blur' }
                    ],
                    rowScanLimits: [
                        { type: "integer", min:1, required: true, trigger: 'blur' }
                    ],
                    delRowLimits: [
                        { type: "integer", min:1, required: true, trigger: 'blur' }
                    ],
                }
            }
        },
        computed: {
            
        },
        created () {
            // 组件创建完后获取数据，
            // 此时 data 已经被 observed 了
            this.$socket.emit("QuerySystemConfigs",{});
        },
        watch: {
            // 如果路由有变化，会再次执行该方法
            // '$route': 'fetchData',            
        },
        methods: {            
           handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.$Message.loading("loading...", 0);
                        this.$socket.emit("UpdateSystemConfigs", this.sysConfigs);
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
                LoadSystemConfigs(sysConfigs){
                    this.sysConfigs = sysConfigs;
                    this.$Message.success('load system configs success!');
                }
            }
        }

    }
</script>*/
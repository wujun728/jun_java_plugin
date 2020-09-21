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
/*瀑布流层*/
.waterfall{
    -moz-column-count:4; /* Firefox */
    -webkit-column-count:4; /* Safari 和 Chrome */
    column-count:4;
    -moz-column-gap: 1em;
    -webkit-column-gap: 1em;
    column-gap: 1em;
}
/*一个内容层*/
.item{
    -moz-page-break-inside: avoid;
    -webkit-column-break-inside: avoid;
    break-inside: avoid;
    min-width: 380px;
}
</style>
<template>
    <Row :gutter="16" class="waterfall">       
        <div v-for="item, slot in infos" class="item">
            <Card>
                <p slot="title">{{slot}}</p>
                <p v-for="val in item">{{val}}</p>
            </Card>
        </div>
    </Row>
</template>

<script>
    export default {
        data(){
            return {                
                infos: {}
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
                    this.$socket.emit("ServerInfo", parseInt(serverid));
                }else{
                    this.infos = {}
                }
            }
        }
        ,socket:{
            events:{
                ShowServerInfo(infos){
                    this.infos = infos;
                }
            }
        }

    }
</script>
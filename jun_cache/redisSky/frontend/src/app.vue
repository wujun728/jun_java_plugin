<template>
    <div>
        <router-view></router-view>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                loading: []
            };
        },
        mounted() {

        },
        beforeDestroy() {

        },
        methods: {
            
        },
        socket:{
            events:{
                loading () {
                    var tmp = this.$Message.loading({
                        content: 'loading ...',
                        duration: 0
                    });
                    this.loading.push(tmp);
                },
                loadingComplete(){
                    var tmp = this.loading.pop();
                    tmp();
                },
                tip(info){
                    this.$Message[info.type]({
                        content: info.msg,
                        duration: info.duration,
                        closable: true
                    })
                },
                ShowServers(servers){
                    this.$store.dispatch('saveServers', servers);
                }
            }
        }
    };
</script>
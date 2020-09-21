<style scoped></style>
<template>
	<Table border size="small" :columns="columns" :data="servers" :highlight-row="true"></Table>
</template>
<script>
// import { mapState } from 'vuex'
    export default {
        data(){
            return {
            	columns: [
            		{
                        title: 'ID',
                        key: 'id'
                    },
                    {
                    	title: 'name',
                    	key: 'name'
                    },
                    {
                    	title: 'host',
                    	key: 'host'
                    },
                    {
                    	title: 'port',
                    	key: 'port'
                    },
                    {
                    	title: 'modify',
                    	key: 'action',
                    	render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'info',
                                        size: 'small'
                                    } 
                                    ,style: {
                                        marginRight: '5px'
                                    }
                                    ,on: {
                                        click: () => {
                                            this.$router.push({ path: '/servers/info/'+ this.servers[params.index]['id'] })
                                        }
                                    }
                                }, 'info'),
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    } 
                                    ,style: {
                                        marginRight: '5px'
                                    }
                                    ,on: {
                                        click: () => {
                                            this.$router.push({ path: '/servers/edit/'+ this.servers[params.index]['id'] })
                                        }
                                    }
                                }, 'edit'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    }
                                    ,on: {
                                        click: () => {
                                            this.$socket.emit('DelServer', this.servers[params.index]['id']);
                                        }
                                    }
                                }, 'del')
                            ]);
                        }
                    }
                ],
            	
            }
        },
        created () {
            // 组件创建完后获取数据，
            // 此时 data 已经被 observed 了
            this.$socket.emit("QueryServers")
            // this.$store.dispatch('queryServers');
        },
        computed: {
        	servers(){
        		return this.$store.state.servers;
        	}
        },
        socket:{
            events:{
                DelServerSuccess(servers){
            		this.$store.dispatch('saveServers', servers);                	
                }
            }
        }
    }
</script>
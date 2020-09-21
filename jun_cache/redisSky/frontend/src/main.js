import Vue from 'vue';
import iView from 'iview';
import VueRouter from 'vue-router';
import Routers from './router';
import Vuex from 'vuex';
import Util from './libs/util';
import App from './app.vue';
import 'iview/dist/styles/iview.css';
import VueWebsocket from "vue-websocket";
import infiniteScroll from 'vue-infinite-scroll';

Vue.use(infiniteScroll);
Vue.use(VueRouter);
Vue.use(Vuex);

Vue.use(iView);

Vue.use(VueWebsocket, "ws://", {
// Vue.use(VueWebsocket, "ws://172.27.40.6:80/", {
    reconnection: true,
    transports: ['websocket']
});

// 路由配置
const RouterConfig = {
    mode: 'hash',
    routes: Routers
};
const router = new VueRouter(RouterConfig);

router.beforeEach((to, from, next) => {
    iView.LoadingBar.start();
    Util.title(to.meta.title);
    next();
});

router.afterEach(() => {
    iView.LoadingBar.finish();
    window.scrollTo(0, 0);
});


const store = new Vuex.Store({

    state: {
        servers: [],
    },
    getters: {
        servers: function(){
            return this.servers
        }
    },
    mutations: {
        saveServers: function(state, servers){
            state.servers = servers;
        },
        updateServer: function(state, server){
            for (var i = state.servers.length - 1; i >= 0; i--) {
                if (state.servers[i].id == server.id){
                    state.servers[i] = server;
                }
            }
        },
        addServer: function(state, server){
            state.servers.push(server);
        }
    },
    actions: {
        saveServers: function({ commit }, servers){
            commit('saveServers', servers);
        },
        updateServer: function({commit}, server){
            commit('updateServer', server);            
        },
        addServer: function({commit}, server){
            commit('addServer', server);            
        }
    }
});


new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App)
});
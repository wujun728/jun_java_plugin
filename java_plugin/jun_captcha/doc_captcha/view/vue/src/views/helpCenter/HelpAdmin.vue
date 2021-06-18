<template>
    <Markdown :filePath="`/static/markdownTxt/${fileName}.txt`"></Markdown>
</template>

<script>

import Markdown from './../../components/Markdown'

export default {
    name:'markdown-view',
    data() {
        return {
            urlArr:[
                {id:1,name:'process'},
                {id:2,name:'admin'},
                {id:3,name:'html'},
                {id:4,name:'vue'},
                {id:5,name:'flutter'},
                {id:6,name:'uni-app'},
                {id:7,name:'weex'},
                {id:8,name:'react-native'},
                {id:9,name:'android'},
                {id:10,name:'ios'},
            ],
            fileName:''
        }
    },
    watch: {
        $route:{
            handler:'getFileName',
            immediate:true
        }
    },
    methods: {
        getFileName(){
            const url = this.urlArr.find(item=>this.$route.params.id == item.id)
            this.fileName = url.name
        }
    },
    components:{
        Markdown
    },
}
</script>


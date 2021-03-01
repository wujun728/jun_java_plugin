<template>
    <div class="question">
        <h2>1. linux部署注意事项点选文字</h2>
        <p class="sub_title">1.1 字体乱码问题</p>
        <p sytle="padding-left:10px">
           点选文字中所用字体默认为宋体，linux不支持该字体，所以可能会出现以下图中情况，如图所示。
        </p>
        <img src="./../../assets/image/ziti.png" alt="" class="img">
        <p class="sub_title" style="margin-top:340px;">1.2 乱码解决方案</p>
        <p>
            宋体黑体为例
            <p> 1、安装字体库</p>
            <span class="text">在CentOS 4.x开始用fontconfig来安装字体库，所以输入以下命令即可：
            sudo yum -y install fontconfig
            这时在/usr/shared目录就可以看到fonts和fontconfig目录了（之前是没有的）：
            接下来就可以给我们的字体库中添加中文字体了。</span>
            
            <p>2、首先在/usr/shared/fonts目录下新建一个目录chinese：</p>
            <span class="text">CentOS中，字体库的存放位置正是上图中看到的fonts目录，所以我们首先要做的就是找到中文字体文件放到该目录下，而中文字体文件在我们的windows系统中就可以找到，打开c盘下的Windows/Fonts目录：</span>
            <p>3、紧接着需要修改chinese目录的权限：</p>
            <span class="text">sudo chmod -R 755 /usr/share/fonts/chinese
            接下来需要安装ttmkfdir来搜索目录中所有的字体信息，并汇总生成fonts.scale文件，输入命令：
            sudo yum -y install ttmkfdir
            然后执行ttmkfdir命令即可：
            ttmkfdir -e /usr/share/X11/fonts/encodings/encodings.dir</span>
            <p>4、最后一步就是修改字体配置文件了，首先通过编辑器打开配置文件 ：</p>
            <span class="text">vim /etc/fonts/fonts.conf
            可以看到一个Font list，即字体列表，在这里需要把我们添加的中文字体位置加进去：
            /usr/share/fonts/chinese</span>
            <p>5、然后输入:wq保存退出，最后别忘了刷新内存中的字体缓存，这样就不用reboot重启了：</p>
            <span class="text">fc-cache
            这样所有的步骤就算完成了，最后再次通过fc-list看一下字体列表：
            fc-list</span>
            

        </p>
    </div>
</template>

<script>
export default {
    
}
</script>

<style lang="less" scoped>
.question{
    position: relative;
    h2{
        background: #eee;
        line-height: 36px;
        text-indent: 1rem;
        margin-top: 20px;
    }
    p{
        line-height: 36px;
        text-indent: 2em;
    }
    .img{
        position: absolute;
        left: -90px;
        display: block;
    }
    .sub_title{
        color:red;
    }
    .text{
        line-height: 36px;
        text-indent: 4em;
        margin-left: 30px;
    }
}
</style>

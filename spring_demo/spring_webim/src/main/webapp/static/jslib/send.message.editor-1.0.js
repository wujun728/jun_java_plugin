/**
 * IM chat Send Message iframe editor
 * @author: hoojo
 * @createDate: 2012-5-24
 * @version 1.0
 **/
var agent = window.navigator.userAgent.toLowerCase();
var sendMessageEditor = {
 
     // 获取iframe的window对象
    getWin: function () {
        return /*!/firefox/.test(agent)*/false ? sendMessageEditor.iframe.contentWindow : window.frames[sendMessageEditor.iframe.name];
    },
 
    //获取iframe的document对象
    getDoc: function () {
        return !/firefox/.test(agent) ? sendMessageEditor.getWin().document : (sendMessageEditor.iframe.contentDocument || sendMessageEditor.getWin().document);
    },
 
    init: function (userJID) {
        //打开document对象，向其写入初始化内容，以兼容FireFox
        var doc = sendMessageEditor.getDoc();
        doc.open();
        var html = [
            '<html>', 
            '<head><style type="text/css">body{border:0;margin:0;padding:3px;height:98%;cursor:text;background-color:white;font-size:12px;font-family:Courier,serif,monospace;}</style></head>',
            '<body jid="', userJID, '"></body>', 
            '</html>'].join("");
        doc.write(html);
        //打开document对象编辑模式
        doc.designMode = "on";
        doc.close();
    },
 
     getContent: function () {
         var doc = sendMessageEditor.getDoc();
         //获取编辑器的body对象
        var body = doc.body || doc.documentElement;
        //获取编辑器的内容
        var content = body.innerHTML;
        //对内容进行处理，例如替换其中的某些特殊字符等等
        //Some code
        
        //返回内容
        return content;
     },
     
      //统一的执行命令方法
    execCmd: function (cmd, value, d){
        var doc = d || sendMessageEditor.getDoc();
        //doc对象的获取参照上面的代码
        //调用execCommand方法执行命令
        doc.execCommand(cmd, false, value === undefined ? null : value);
    },
    
    getStyleState: function (cmd) {
        var doc = sendMessageEditor.getDoc();
        //doc对象的获取参考上面的对面
        //光标处是否是粗体
        var state = doc.queryCommandState(cmd);
        if(state){
          //改变按钮的样式
        }
        return state;
    },
    insertAtCursor: function (text, d, w){
        var doc = d || sendMessageEditor.getDoc();
        var win = w || sendMessageEditor.getWin();
        //win对象的获取参考上面的代码
        if (/msie/.test(agent)) {
            win.focus();
            var r = doc.selection.createRange();
            if (r) {
                r.collapse(true);
                r.pasteHTML(text);      
            }
        } else if (/gecko/.test(agent) || /opera/.test(agent)) {
            win.focus();
            sendMessageEditor.execCmd('InsertHTML', text, doc);
        } else if (/safari/.test(agent)) {
            sendMessageEditor.execCmd('InsertText', text, doc);
        }
    }
};
require.config({
    // enforceDefine: true,
    baseUrl : "js",
    paths: {
    	"angular":'//cdn.bootcss.com/angular.js/1.5.5/angular',
        "jquery": '//cdn.bootcss.com/jquery/1.11.3/jquery.min',
        "bootstrap" :  "//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min" ,
        'css':'//cdn.bootcss.com/require-css/0.1.8/css',
        
        //滑块
        "bootstrapSwitch":'//cdn.bootcss.com/bootstrap-switch/3.3.2/js/bootstrap-switch.min',
        "angularBootstrapSwitch":'//cdn.bootcss.com/angular-bootstrap-switch/0.5.1/angular-bootstrap-switch',
        
        //angularStrap
        "angularStrap":'//cdn.bootcss.com/angular-strap/2.3.8/angular-strap',
        "angularStrapTpl":'//cdn.bootcss.com/angular-strap/2.3.8/angular-strap.tpl',
       
       //动画效果
        "angularanimate":'//cdn.bootcss.com/angular.js/1.5.5/angular-animate',

        "ngTagInput":'https://cdn.bootcss.com/ng-tags-input/3.1.1/ng-tags-input.min',

        "component":'./component/component.min',
        "app":'./component/app.min',
    },
    shim : {
    	"angular":{exports:"angular"},
        "bootstrap" : { "deps" :['jquery','css!//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css','css!//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css'] },
        
        'angularBootstrapSwitch':{ "deps" :['angular'] },
        'bootstrapSwitch':{ "deps" :['bootstrap','angularBootstrapSwitch','css!//cdn.bootcss.com/bootstrap-switch/3.3.2/css/bootstrap3/bootstrap-switch.css'] },
        
        'angularanimate':{"deps" :['angular']},
        
        'angularStrap':{"deps" :['angular']},
        'angularStrapTpl':{"deps" :['angularStrap','bootstrap','angularanimate','css!//cdn.bootcss.com/angular-motion/0.4.4/angular-motion.css']},

        'ngTagInput':{"deps" :['angular','bootstrap','css!https://cdn.bootcss.com/ng-tags-input/3.1.1/ng-tags-input.min.css','css!https://cdn.bootcss.com/ng-tags-input/3.1.1/ng-tags-input.bootstrap.min.css']},
        
        'app':{ "deps" :['angular','angularStrapTpl','css!../css/base.css'],exports:'app' },
        'component':{ "deps" :['angular']},
        
    },
});

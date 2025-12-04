/**
 * 柱状图、曲线图、饼图、柱状折叠图
 * @author 覃海林
 * @date 2018-11-20
 */
var Echart=function(){
	this.config={
		//容器
		divId:null,
		//主标题
		title:"",
		//副标题
		subtitle:"",
		//图例
		legend:[],	
		//x轴名称 
		xName:"",
		//x轴坐标
		xAxis:[],	 
		//y轴名称
		yName:"",			
		//y轴坐标
		yAxis:[],			
		//图例名称
		seriesName:"",		
		//柱状、折线数据
		seriesData:[],
		//饼图数据
		seriesPieData:[],
		//多组数据统计
		series:[],	
		//图表类型
		type:'',
		//单位
		tooltipText:"",
		//附属类
		stack:"",
		//子名称
		stackName:"",
		//子数据
		stackData:[],
		//是否坐标轴刻度与标签对齐
		isAlignWithLabel:false,
		//标题位置:left,center,right
		title_x:null,
		//图例位置:left,center,right
		legend_x:null,
		legend_y:null,
		//警戒值
		markLineNum:100
	}
}


/*
  	图表应用样本实例	
	<!--  默认div容器配置 -->
	<div id="chart"></div>
 */
Echart.prototype.getData=function(url,params,divId){
	$.ajax({
    	url:url,
    	// type:"POST",
    	type:"GET",
    	data:params||{},
    	success:function(result){
    		console.log(result);
    		var config=echart.config;
    		for(var i=0;i<result.length;i++){
    			var data=result[i]; 			
    			config.title=data.title;
   			    config.subtitle=data.subtitle;
   			    config.title_x=data.title_x||"center";
   			    config.legend_x=data.legend_x||"left";
   			    config.legend_y=data.legend_y||"top";
   			    config.xName=data.xName;
   			    config.yName=data.yName;
   			    config.legend=data.legend;
   			    config.xAxis=data.xAxis;
   			    config.divId=divId||"chart";
   			    config.series=data.series;
   			    config.seriesName=data.seriesName;
   				config.tooltipText=data.tooltipText;
   				config.seriesData=data.seriesData;
   				config.seriesPieData=data.seriesPieData;
   				config.markLineNum=data.markLineNum||100;
   				var chartType=data.type;
	    		if(chartType=='bar'){
	    			echart.bar(config);
	    		}else if(chartType=='line'){
	    			echart.line(config);
	    		}else if(chartType=='pie'){
	    			echart.pie(config);	    			
	    		}
    		}
    	}
    });
}

//柱状图
Echart.prototype.bar=function(config){
	echarts.init(document.getElementById(config.divId)).setOption(getOption(config,'bar'));
}

//折线图
Echart.prototype.line=function(config){
	echarts.init(document.getElementById(config.divId)).setOption(getOption(config,'line'));
}

//饼图
Echart.prototype.pie=function(config){
	echarts.init(document.getElementById(config.divId)).setOption(getOptionPie(config));
}

//折叠柱状图
Echart.prototype.stack=function(config){
	echarts.init(document.getElementById(config.divId)).setOption(getOptionStack(config));
}

//柱状图、折线图option
function getOption(config,type){	
	var pointerType='shadow';
	var alignWithLabel=config.isAlignWithLabel;
	if(type=='line'){
		pointerType='line';
		alignWithLabel=true;
	}
	
	var option = {
//			color:['#f3b743','#44bfca','#00a0e9','#80c269','#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3'],
			title: {
				text: config.title,
				subtext:config.subtitle,
				x:config.title_x||'left'
			},			 
			toolbox: {
               // x:100,
               // y:0,
                show : true,
                feature : {
                    dataView : {show: true, readOnly: true},
                    magicType : {show: true, type: ['bar','line']},
                    restore : {show: true},
                   saveAsImage : {show: true},
//                    myTool1: {
//                        show: true,
//                        title: '柱状图切换',
//                        icon: 'image://'+_path+'/static/libs/echarts/img/bar.png',
//                        onclick: function (){
//                        	onSearch('bar');                      	
//                        }
//                    },
//                    myTool2: {
//                        show: true,
//                        title: '折线图切换',
//                        icon: 'image://'+_path+'/static/libs/echarts/img/line.png',
//                        onclick: function (){
//                        	onSearch('line');                      	
//                        }
//                    },
//                    myTool: {
//                        show: true,
//                        title: '饼图切换',
//                        icon: 'image://'+_path+'/static/libs/echarts/img/pie.png',
//                        onclick: function (){
//                        	onSearch('pie');                     	
//                        }
//                    }
                }
            },
			tooltip: {
				trigger:'axis',
				axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : pointerType     // 默认为直线，可选为：'line' | 'shadow'
		        }
				//,formatter: '{a}<br />{b}:{c}'+config.tooltipText
			},			
			legend: {
				data:config.legend,
				x:config.legend_x||'center',
				y:config.legend_y||'top'
			},
			xAxis: {
				name:config.xName,
				type: 'category',
				axisTick: {
					alignWithLabel: alignWithLabel
				},
				data: config.xAxis
			},
			yAxis: {
				name:config.yName,
				type: 'value'
			},
			series: [{
				name: config.seriesName,
				type: type,
				markLine : {   //添加警戒线
                    //symbol:"none",               //去掉警戒线最后面的箭头
                    name:"警戒线",
                    silent:true,
                    label:{
                        position:"end",         //将警示值放在哪个位置，三个值“start”,"middle","end"  开始  中点 结束
                        formatter: "警戒线(" +config.markLineNum+ ")",
                        color:"red",
                        fontSize:14
                    },
                    data : [{
                        silent:true,             //鼠标悬停事件  true没有，false有
                        lineStyle:{               //警戒线的样式  ，虚实  颜色
                            type:"solid",
                            color:"red"
                        },
                        name: '警戒线',
                        yAxis: config.markLineNum
                    }]
                },
				data: config.seriesData
			}		
			]
	};

	if(config.series.length>1){
		option.series=config.series;
	}

	return option;
}

//饼图option
function getOptionPie(config){
	var option = {
	//	color:['#f3b743','#44bfca','#00a0e9','#80c269','#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3'],
	    title : {
	        text: config.title,
	        subtext: config.subtitle,
	        x:config.title_x||'center'
	    },
	    tooltip : {
	        trigger: 'item'
	        ,formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient : 'vertical',
	        x : config.legend_x||'left',
	        data:config.legend
	    },
	    toolbox: {
            x:100,
            y:0,
            show : true,
            feature : {
                //dataView : {show: true, readOnly: true},
               // magicType : {show: true, type: ['bar','line']},
                //restore : {show: true},
                //saveAsImage : {show: true},
//	              myTool1: {
//		              show: true,
//		              title: '柱状图切换',
//		              icon: 'image://'+_path+'/static/libs/echarts/img/bar.png',
//		              onclick: function (){
//		              	onSearch('bar');                      	
//		              }
//		          },
//		          myTool2: {
//		              show: true,
//		              title: '折线图切换',
//		              icon: 'image://'+_path+'/static/libs/echarts/img/line.png',
//		              onclick: function (){
//		              	onSearch('line');                      	
//		              }
//		          },
//		          myTool: {
//		              show: true,
//		              title: '饼图切换',
//		              icon: 'image://'+_path+'/static/libs/echarts/img/pie.png',
//		              onclick: function (){
//		              	onSearch('pie');                     	
//		              }
//		          }
            }
        },
	   // calculable : true,
	    series : [
	        {
	            name:config.seriesName,
	            type:'pie',
	            radius : [0,'75%'],//饼图的半径大小
	            center: ['50%', '55%'],//饼图的位置
	            
	            data:config.seriesPieData
	        }
	    ]
	}; 
	return option;
}

//折叠柱状图
function getOptionStack(config){
	//堆叠柱状图 
	var option = {
		title : {
	        text: config.title,
	        subtext: config.subtitle,
	        x:config.title_x||'left'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data:config.legend,
	        x:config.legend_x||'center'
	    },
	    xAxis :  {
            type : 'category',
            name : config.xName,
            axisTick: {
				alignWithLabel: config.isAlignWithLabel
			},
            data : config.xAxis
        },
	    yAxis : {
            type : 'value',
            name:config.yName
        },
	    series : [	        
	        {
	            name:config.seriesName,
	            type:'bar',
	            data:config.seriesData
	        },
	        {
	            name:config.stackName,
	            type:'bar',
	            barWidth : 5,
	            stack: config.stack,
	            data:config.stackData
	        }	      	       
	    ]
	};
	if(config.series.length>0){
		option.series=config.series;
	}
	
	return option;
}

//初始化对象
var echart=new Echart();


steal( 'jquery/controller','jquery/view/ejs' )
.then(function($){
/**
 * @class Main.Controllers.Home.Controllers.RingChart
 */
$.Controller('Plugins.RingChart',
/** @Static */
{
	defaults : {
		elementId:"cv"
	}
},
/** @Prototype */
{
	init : function(){		
		var env=this;	
		var elementId=this.options.elementId;
		var canvas=document.getElementById(elementId);
		
		if($.browser.msie){
		    canvas=window.G_vmlCanvasManager.initElement(canvas);
		    setTimeout(function(){	
				env.drawChart(canvas);	
			},1000);
		}		
		else{			
		    env.drawChart(canvas);
		}
	},	
	
	drawArc:function(ctx,x,y,r,text,percent,color){
		var angle=this.percentToAngle(percent);
		
		ctx.beginPath();  
	    ctx.arc(x, y, r,  Math.PI * 1.5, Math.PI * angle, false);  
	    ctx.lineWidth = 4.0;    
	    ctx.strokeStyle = color;
	    ctx.stroke(); 
	    
	    var x1=x+r*Math.cos(Math.PI * angle);
	    var y1=y+r*Math.sin(Math.PI * angle);
	    ctx.beginPath();         
        ctx.arc(x1, y1, 5, 0, Math.PI *2, false);  
        ctx.closePath();  
        ctx.fillStyle=color; 
        ctx.fill();  
        
        ctx.font = "10pt Helvetica";  
        ctx.fillStyle = color;          
        ctx.fillText(text+"  "+this.formatPercent(percent) , 130, 192-r,100);  
	},
	
	percentToAngle:function(percent){
		return percent*2-0.5;
	},
	
	formatPercent:function(percent){
		var p=percent*100
		return p.toFixed(0)+"%";
	},

	drawChart:function(canvas) {	 
		var env=this;
        var ctx=canvas.getContext("2d");  
        
        ctx.fillStyle="#ECEFF4";
        ctx.fillRect(0,0,380,380); 
        
        ctx.font = "11pt Helvetica";  
        ctx.fillStyle = "#666";  
        ctx.textAlign = 'center';  
        ctx.textBaseline = 'middle';         
        ctx.fillText("我参与的项目", 190, 190,100);         
        ctx.fillText("( 5 )", 190, 210,50);      
       
        this.drawArc(ctx,190,190,180,"1. 项目1",0.76,"#4cb210");
        this.drawArc(ctx,190,190,155,"2. 项目2",0.40,"#f25a28");
        this.drawArc(ctx,190,190,130,"3. 项目3",0.66,"#d42766");
        this.drawArc(ctx,190,190,105,"4. 项目4",0.55,"#2e3a8e");
        this.drawArc(ctx,190,190,80,"5. 项目5",0.78,"#32abe0");  
    }    
    
});

});
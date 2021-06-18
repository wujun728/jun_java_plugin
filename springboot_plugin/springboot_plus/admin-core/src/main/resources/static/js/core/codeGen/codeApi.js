/*访问后台的代码*/
layui.define([], function(exports) {
	var api={
			refresh:function(callback){
				Common.post("/core/codeGen/refresh.json", {}, function(path){
					callback();
				});
			},
			gen:function(callback){
				Common.post("/core/codeGen/getPath.json", {}, function(path){
					Common.openPrompt("代码保存路径?",path,function(newPath){
						var url ="/core/codeGen/gen.json";
						Lib.submitForm(url,$('#updateForm'),{path:newPath},callback)
					})
				});
			},
			previewHtml:function(callback){
				var form = $('#updateForm');
				var formPara = form.serializeJson();
				Common.post("/core/codeGen/html.json", formPara, callback);
			},
			previewJs:function(callback){
				var form = $('#updateForm');
				var formPara = form.serializeJson();
				Common.post("/core/codeGen/js.json", formPara, callback);
			},
			previewJava:function(callback){
				var form = $('#updateForm');
				var formPara = form.serializeJson();
				Common.post("/core/codeGen/java.json", formPara, callback);
			},
			previewSql:function(callback){
				var form = $('#updateForm');
				var formPara = form.serializeJson();
				Common.post("/core/codeGen/sql.json", formPara, callback);
			},
			genProject:function(form,callback){
			    var formPara = form.serializeJson();
                Common.post("/core/codeGen/projectGen.json", formPara, callback);
			}
		
	};
	
	 exports('codeApi',api);
	
});
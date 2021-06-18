/*
		       自定义jquery函数，完成将form 数据转换为 json字符串格式 
		*/
		$.fn.serializeJson=function(){ 
		        var serializeObj={}; 
		        var array=this.serializeArray(); 
		        // var str=this.serialize(); 
		        $(array).each(function(){ // 遍历数组的每个元素 
		                if(serializeObj[this.name]){ // 判断对象中是否已经存在 name，如果存在name 
		                      if($.isArray(serializeObj[this.name])){ 
		                             serializeObj[this.name].push(this.value); // 追加一个值 hobby : ['音乐','体育'] 
		                      }else{ 
		                              // 将元素变为 数组 ，hobby : ['音乐','体育'] 
		                              serializeObj[this.name]=[serializeObj[this.name],this.value]; 
		                      } 
		                }else{ 
		                        serializeObj[this.name]=this.value; // 如果元素name不存在，添加一个属性 name:value 
		                } 
		        }); 
		        return JSON.stringify(serializeObj); 
		}; 
mywebdb={
   db:null,
   create:function(mydb){
     var request = window.indexedDB.open(mydb.db,mydb.version);
     request.onupgradeneeded=function(event){
	console.log("create table:",mydb.table.name);
	try{
	   var table = mydb.table;
           columns = table.columns;
    	   var store = event.currentTarget.result.createObjectStore(table.name,columns.keyPath);
	   for(var column in columns){
  	      if(column == 'keyPath') continue;
 	      c = columns[column];
	      store.createIndex(column,c.name,c.index);
	   }//end for
	   console.log("create table success");
	}catch(ex){
	   console.error("create :",ex);
        }//end try
     };//end onupgradeneeded
    request.onsuccess=function(event){
        mywebdb.db = this.result;
	if(mydb.success == undefined) return;
	mydb.success(event);
     };//end onsuccess
    request.onerror=function(event){
	conlse.error("init DB:",event.target.errorCode);
     	if(mydb.error == undefined) return;
	mydb.error(event);
     };//end onerror
   },//end create
 
   each:function(callback){
       var objectStore = mywebdb.db.transaction(callback.name,"readonly").objectStore(callback.name);
       objectStore.openCursor().onsuccess=function(event){
   	    var cursor = event.target.result;
   	    if(callback.success == undefined)return;
	    callback.success(cursor);
	    if(cursor)
	    	cursor.continue();
       };//end onsuccess 
   },//end each
   getAll:function(callback){
        var objectStore = mywebdb.db.transaction(callback.name,"readonly").objectStore(callback.name);
        var datas = [];
        objectStore.openCursor().onsuccess=function(event){
   	    var cursor = event.target.result;
	    if(cursor){
	    	datas.push(cursor.value);
	    	cursor.continue();
	    }else{
	      if(callback.sort != undefined){
	    	  callback.sort(datas);
	      }//end if
	      if(callback.success == undefined)return;
	      callback.success(datas);
	    }//end if
       };//end onsuccess 
   },//end getAll
   add:function(callback){
      var objectStore = mywebdb.db.transaction(callback.name,"readwrite").objectStore(callback.name);
      objectStore.oncomplete=function(event){
      if(callback.complete == undefined)return;
 		callback.complete(event);
      };//end oncomplete
      objectStore.onerror=function(event){
          console.error("transaction:",event.target.errorCode);
	  if(callback.error == undefined)return;
	  callback.error();
      };//end onerror
      objectStore.add(callback.value);
   },//end add
   put:function(callback){
      var objectStore = mywebdb.db.transaction(callback.name,"readwrite").objectStore(callback.name);
      objectStore.oncomplete=function(event){
    	  if(callback.complete == undefined)return;
    	  	callback.complete(event);
      };//end oncomplete
      objectStore.onerror=function(event){
          console.error("transaction:",event.target.errorCode);
          if(callback.error == undefined)return;
          	callback.error();
      };//end onerror
      objectStore.put(callback.value);
   },//end put
   get:function(callback){
	var objectStore = mywebdb.db.transaction(callback.name,"readonly").objectStore(callback.name);
        var request = null;
	if(callback.column != undefined){
	   var index = objectStore.index(callback.column);
   	   request = index.get(callback.key);
	}else{
	   request = objectStore.get(callback.key);
	}//end if
        request.onerror=function(event){
          console.error("transaction:",event.target.errorCode);
	  if(callback.error == undefined)return;
	  callback.error(event);
	};
	request.onsuccess=function(event){
	   if(callback.success == undefined) return;
	   callback.success(request.result);
	};
   },//en get
   delete:function(callback){
	 var request = mywebdb.db.transaction(callback.name,"readwrite").objectStore(callback.name);
	 request.delete(callback.key);
	 request.onsuccess = function(event){
		console.log("delete success");
		if(callback.success == undefined) return;
	 	callback.success(event);
	 };
   },//end delete  
   deleteDB:function(mydb){
	   var req = window.indexedDB.deleteDatabase(mydb.name);
	   req.onsuccess=function(evt){
		   console.log("Database delete success ");
	   };
	   req.onerror=function(evt){
		   console.error("Database error"+e.target.errorCode);
	   };
   },//end deleteDB
   //更新离线缓存文件
   updateCacheFile:function(){
       if(window.applicationCache.status == window.applicationCache.UPDATEREADY){
	    window.applicationCache.update();
       }//end if
   },//end updateCache
   //在线返回true,离线返回false
   webOnline:function(){
     return navigator.onLine;
   }
};

function quickSort(datas,low,high,condition){
	try{
	var i=low,j=high;
	if(datas == undefined || datas.lenght == 0)
		return;
	if(low<high){
		var key = datas[low];
		var stop = 0;
		while(i<j && stop != 100){
			for(;i<j && condition(key,datas[j]);j--,stop=0);
			datas[i] = datas[j];
			for(;i<j && condition(datas[i],key);i++,stop=0);
			datas[j] = datas[i];
			stop ++;
		}
		datas[i] = key;
		quickSort(datas,low,i-1,condition);
		quickSort(datas,i+1,high,condition);
	}//end if
	return datas;
	}catch(ex){
		alert(ex);
	}
}


steal("jquery/model").then(
		function($) {
			$.Model('Plugins.DataGrid.Models.Paginate', {
				defaults : {
					dataList: [],
					totalRecords : 0,
					pageNo : 1,
					pageSize : 10,
					totalPages : 1
				}
			}, {
				calculatePages: function(){			
					this.totalPages = Math.ceil(this.totalRecords / this.pageSize);
					while (this.pageNo>this.totalPages && this.pageNo>1){
						this.pageNo=parseInt(this.pageNo)-1;
					}
				},		
				setTotalRecords: function(totalRecords){
					this.totalRecords=totalRecords;
				},
				setCurrentPageNo : function(newPageNo) {		
					if (!newPageNo){
						newPageNo=1;
					}
				    this.pageNo=newPageNo;
				},				
				next : function() {						
					this.pageNo=parseInt(this.pageNo)+1;
				},
				prev : function() {					
					this.pageNo=parseInt(this.pageNo)-1;
				},
				canNext : function() {				
					return this.pageNo < this.totalPages;
				},
				canPrev : function() {
					return this.pageNo >1;
				},					
				getCurrentPageNo: function(){
					return this.pageNo;
				},
				getTotalPages:function(){
					return this.totalPages;
				},
				setPageSize:function(pageSize){
					this.pageSize=pageSize;
				},
				getPageSize:function(){
					return this.pageSize;
				},
				setDataList:function(dataList){
					this.dataList=dataList;
				},
				getDataList:function(){
					return this.dataList;
				},
				setTotalRecords:function(totalRecords){
					this.totalRecords=totalRecords;
				}
			})
		})
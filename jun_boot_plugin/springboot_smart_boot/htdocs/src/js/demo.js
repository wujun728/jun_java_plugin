(function() {
	angular.module('demoModule', ['niceComponent', 'app'])
		.controller('demoCtrl', ['$scope', '$http', '$pagination', '$url', '$sce', '$alert','$modal', function($scope, $http, $pagination, $url, $sce, $alert,$modal) {

			//键值对详情界面
			var kvDetailModal = $modal({scope: $scope,animation:'am-fade-and-slide-top',templateUrl: 'component/demo.tpl.html',show: false});
			 

			$scope.tenant = "";
			$scope.tenants = [];

			//加载租户信息
			$http.get($url.tenant_list)
				.success(function(response) {
					if (response.code != 0) {
						return;
					}
					var tenants = response.data.data;
					$scope.tenants = [];
					for (var i = 0; i < tenants.length; i++) {
						console.log(tenants[i]);
						$scope.tenants.push({
							'value': tenants[i].code,
							'label': $sce.trustAsHtml(tenants[i].name)
						});
					}
				});

			$scope.listMessage = null;
			//键值对列表
			$scope.page = $pagination({
				load: function(page) {
					$http.get($url.kv_list, {
						// method: 'POST',
						params: angular.extend({
							tenant: $scope.tenant,
							key: $scope.searchKey,
						}, $scope.queryParams),
					}).success(function(response) {
						$scope.page.options.currentPage=1;
						if (response.code != 0) {
							$scope.listMessage = response.message;
							$scope.page.dataList = {};
							$scope.page.options.total = 0;
							$scope.page.options.pageSize = 10;
							$scope.page.options.pages = 1;
							return;
						}
						$scope.listMessage = null;
						$scope.page.dataList = response.data.data;
						$scope.page.options.total = response.data.total;
						$scope.page.options.pageSize = response.data.pageSize;
						$scope.page.options.pages = Math.ceil(response.data.total / response.data.pageSize);
					}).error(function(response) {
						console.log(response);
					});
				}
			});

			//租户详情
			$scope.infoDetail = {

			};
			$scope.isEdit = false;
			/**
			 * 新增/修改租户详情界面
			 * @param {[type]} id [description]
			 */
			$scope.addOrEditDetail = function(key) {
					if (key) {
						$http.get($url.kv_get, {
							// method: 'POST',
							params: angular.extend({
								key: key,
								tenant: $scope.tenant
							}),
						}).success(function(response) {
							$scope.isEdit = true;
							console.log(response);
							$scope.infoDetail = response.data;
							kvDetailModal.$promise.then(kvDetailModal.show);
						});
					} else {
						$scope.isEdit = false;
						$scope.infoDetail = {};
						kvDetailModal.$promise.then(kvDetailModal.show);
					}
				}
				/**
				 * 保存资讯信息
				 * @return {[type]} [description]
				 */
			$scope.submit = function() {
				$http.post($scope.isEdit ? $url.kv_update : $url.kv_add, angular.extend({
						tenant: $scope.tenant,
					}, $scope.infoDetail))
					.success(function(response) {
						if (response.code != 0) {
							// alert(response.message);
							$alert({
								title: response.message,
								container: '.modal-footer',
								// content: 'Best check yo self, you\'re not looking too good.',
								placement: 'top-right',
								duration: "3",
								type: 'danger',
								show: true
							});
							return;
						}
						$alert({
							title: '保存成功!',
							container: 'body',
							// content: 'Best check yo self, you\'re not looking too good.',
							placement: 'top-right',
							duration: "3",
							type: 'info',
							show: true
						});
						$scope.page.loadPage(1);
						kvDetailModal.$promise.then(kvDetailModal.hide);
					});
			}

			$scope.delete = function(key) {
				if (!key) {
					alert('请指定Key');
				}
				if (!confirm("确定删除该键值对吗?")) {
					return;
				}
				$http.get($url.kv_del, {
					// method: 'POST',
					params: angular.extend({
						key: key,
						tenant: $scope.tenant
					}),
				}).success(function(response) {
					if (response.code != 0) {
						// alert(response.message);
						return;
					}
					console.log(response);
					$alert({
						title: '删除成功!',
						container: 'body',
						// content: 'Best check yo self, you\'re not looking too good.',
						placement: 'top-right',
						duration: "3",
						type: 'info',
						show: true
					});
					// alert('删除成功');
					$scope.page.loadPage(1);
				});
			}
		}]);
})();
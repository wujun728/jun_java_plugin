(function(window, document, undefined) {
	'use strict';
	angular.module('app', ['mgcrea.ngStrap','ngAnimate'])
		.config(['$provide', '$httpProvider', '$urlProvider', function($provide, $httpProvider, $urlProvider) {
			$httpProvider.defaults.xsrfCookieName = 'ctoken';
			$httpProvider.defaults.xsrfHeaderName = 'X-CSRFToken';
			// 作用于 get post 等所有类型请求
			$httpProvider.interceptors.push(['$q', function($q) {
				// var token = getCookie('ctoken');
				return {
					'request': function(config) {
						if (!config.params) config.params = {};

						if (config.method == 'POST' && $urlProvider.defaults.model == 'dev') { //be
							config.method = 'GET';
						}
						return config || $q.when(config);
					}
				}
			}]);

			// get 类型 不起作用
			$httpProvider.defaults.transformRequest = function(params) {
				var str = [];
				if (params) {
					if (angular.isObject(params)) {
						for (var p in params) {
							str.push(encodeURIComponent(p) + "=" + encodeURIComponent(params[p]));
						}
						return str.join("&");
					}
				}
				return params;
			};

			$httpProvider.defaults.transformResponse.push(function(res) {
				return res;
			});

			$httpProvider.defaults.headers.post = {
				'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
			}
		}])
		.provider('$url', function() {
			this.defaults = {
				model: 'remote_dev',
				data: {}
			};
			var domain = {
				localDevDomain: 'http://localhost:3000/test/', //本地测试域名
				remoateDevDomain: 'http://localhost:3000/api/', //远程开发环境域名
				stableDomain: 'http://localhost:3000/api/', //正式环境
			};

			//正式环境URL配置
			var stable = {
				tenant_list: domain.stableDomain + 'tenant/list', //服务号资讯列表
				tenant_add: domain.stableDomain + 'tenant/add', //添加租户信息
				tenant_del: domain.stableDomain + 'tenant/del', //删除租户信息
				tenant_get: domain.stableDomain + 'tenant/get', //获取租户信息
				tenant_update: domain.stableDomain + 'tenant/update', //更新租户信息
				kv_list: domain.stableDomain + 'kv/list', //键值列表查询
				kv_add: domain.stableDomain + 'kv/add', //添加键值信息
				kv_del: domain.stableDomain + 'kv/del', //删除键值信息
				kv_get: domain.stableDomain + 'kv/get', //获取键值信息
				kv_update: domain.stableDomain + 'kv/update', //更新键值信息
				wx_info_submit: domain.stableDomain + 'raiderSubmit.json',
			};

			//开发环境URL配置
			var local_dev = {
				tenant_list: domain.localDevDomain + 'raiderList.json', //租户信息列表
				wx_info_detail: domain.localDevDomain + 'raiderInfo.json', //服务号资讯详情
				wx_info_submit: domain.localDevDomain + 'raiderSubmit.json',
			};


			switch (this.defaults.model) {
				case 'local_dev':
					this.defaults.data = local_dev;
					break;
				case 'remote_dev':
					this.defaults.data = remote_dev;
					break;
				case 'stable':
					this.defaults.data = stable;
					break;
				default:
					console.error('unsupport model:' + this.defaults.model);
			};
			this.$get = function() {
				return this.defaults.data;
			};
		})
		.provider('$pagination', function() {
			var defaults = this.defaults = {
				currentPage: 1,
				total: 0,
				pageSize: 10,
				dataList: {},
				pages: 0,
				load: function() {},
			};
			this.$get = ['$http', function($http) {
				function AlertFactory(config) {
					var $pagination = {};
					// Common vars
					var options = angular.extend({}, defaults, config);
					$pagination.options = options;
					$pagination.next = function() {
						$pagination.loadPage(options.currentPage + 1);
					};
					$pagination.prev = function() {
						if (options.currentPage > 1) {
							$pagination.loadPage(options.currentPage - 1);
						}
					};
					$pagination.loadPage = function(page) {
						options.currentPage = page;
						options.load(page);
					};
					return $pagination;
				}
				return AlertFactory;
			}];

		}).run(['$templateCache', function($templateCache) {
			$templateCache.put('pageTemplate', '<ul class="pager">' +
				'<li class="pager-prev" ng-show="pagination.options.currentPage>1"><a ng-click="pagination.prev()" href="#">上一页</a></li>' +
				'<li>记录数:{{pagination.options.total}},共{{pagination.options.pages}}页,当前第{{pagination.options.currentPage}}页</li>' +
				'<li class="pager-next" ng-show="pagination.options.pages>pagination.options.currentPage"><a ng-click="pagination.next()" href="#">下一页</a></li>' +
				'</ul>');
		}]).directive('pagination', ['$http', '$templateCache', '$compile', function($http, $templateCache, $compile) {
			return {
				restrict: 'A',
				link: function(scope, element, attrs) {
					var tpl = $templateCache.get('pageTemplate');
					var pScope = scope.$new();
					pScope.pagination = scope[attrs.pagePlugin];
					var comEle = $compile(tpl)(pScope);
					element.html(comEle);
				},
			}
		}]);;
})(window, document);
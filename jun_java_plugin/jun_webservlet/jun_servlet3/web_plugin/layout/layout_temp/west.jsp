<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="UTF-8">
	var tree;
	$(function() {
		tree = $('#tree').tree({
			url : 'menuController.do?tree',
			animate : false,
			lines : !sy.isLessThanIe8(),
			onClick : function(node) {
				if (node.attributes && node.attributes.src && node.attributes.src != '') {
					var href;
					if (/^\//.test(node.attributes.src)) {/*以"/"符号开头的,说明是本项目地址*/
						href = node.attributes.src.substr(1);
						$.messager.progress({
							text : '请求数据中....',
							interval : 100
						});
					} else {
						href = node.attributes.src;
					}
					addTabFun({
						src : href,
						title : node.text
					});
				} else {
					addTabFun({
						src : 'test/err.jsp',
						title : '工程建设'
					});
				}
			},
			onLoadSuccess : function(node, data) {
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.tree('expandAll');
						}
					});
				}
			}
		});

	});
	function collapseAll() {
		var node = tree.tree('getSelected');
		if (node) {
			tree.tree('collapseAll', node.target);
		} else {
			tree.tree('collapseAll');
		}
	}
	function expandAll() {
		var node = tree.tree('getSelected');
		if (node) {
			tree.tree('expandAll', node.target);
		} else {
			tree.tree('expandAll');
		}
	}
</script>
<div class="easyui-panel" fit="true" border="false">
	<div class="easyui-accordion" fit="true" border="false">
		<div title="系统菜单" iconCls="icon-tip">
			<div class="easyui-layout" fit="true">
				<div region="north" border="false" style="overflow: hidden;">
					<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="expandAll();">展开</a><a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="collapseAll();">折叠</a><a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-reload" onclick="tree.tree('reload');">刷新</a>
					<hr style="border-color: #fff;" />
				</div>
				<div region="center" border="false">
					<ul id="tree" style="margin-top: 5px;"></ul>
				</div>
			</div>
		</div>
		<div title="EasyUiDemo" href="test/easyuidemo.html"></div>
	</div>
</div>
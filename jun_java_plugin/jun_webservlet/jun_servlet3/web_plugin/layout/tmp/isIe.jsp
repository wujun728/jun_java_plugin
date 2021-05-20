<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8">
	$(function() {
		if (sy.isLessThanIe8()) {
			$.messager.show({
				title : '警告',
				msg : '您使用的浏览器版本太低！<br/>建议您使用谷歌浏览器来获得更快的页面响应效果！',
				timeout : 1000 * 30
			});
		}
	});
</script>
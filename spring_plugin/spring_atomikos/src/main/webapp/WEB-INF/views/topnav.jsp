<%@ page contentType="text/html;charset=UTF-8"%>
<!---顶部状态栏 star-->
<div class="row ">
	<nav class="navbar navbar-fixed-top" role="navigation" style="margin-bottom: 0">
		<div class="navbar-header">
			<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
		</div>
		<ul class="nav navbar-top-links navbar-right notification-menu">
			<li class="user-dropdown"><a href="#" class="btn  dropdown-toggle" data-toggle="dropdown"> wangxin<span class="caret"></span>
			</a>
				<ul class="dropdown-menu dropdown-menu-usermenu pull-right">
					<!-- <li><a href="#"><i class="fa fa-cog"></i> 修改密码</a></li> -->
					<li><a href="${ctx }/logout"><i class="fa fa-sign-out"></i> 退出</a></li>
				</ul></li>
		</ul>
	</nav>
</div>
<!---顶部状态栏 end-->
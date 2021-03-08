<%@ page contentType="text/html;charset=UTF-8"%>

<input type="hidden" name="id" value="${ma.id }" />
<div class="form-group">
	<label class="col-sm-4 control-label" for="mailType">收件类型 <span class="text-danger">*</span></label>
	<div class="col-sm-8">
		<input type="text" id="mailType" name="mailType" value="${ma.mailType}" required class="form-control" disabled="disabled">
	</div>
</div>
<div class="form-group">
	<label class="col-sm-4 control-label" for="toAddress">收件人地址 <span class="text-danger">*</span></label>
	<div class="col-sm-8">
		<input type="text" id="toAddress" name="toAddress" value="${ma.toAddress }" required class="form-control">
	</div>
</div>
<div class="form-group">
	<label class="col-sm-4 control-label" for="toCc">抄送地址 <span class="text-danger">*</span></label>
	<div class="col-sm-8">
		<input type="text" id="toCc" name="toCc" value="${ma.toCc}" required class="form-control">
	</div>
</div>
<div class="form-group">
	<label class="col-sm-4 control-label" for="toBcc">秘送地址 <span class="text-danger">*</span></label>
	<div class="col-sm-8">
		<input type="text" id="toBcc" name="toBcc" value="${ma.toBcc}" required class="form-control" >
	</div>
</div>
<div class="form-group m-t-sm">
	<div class="col-sm-6 col-sm-push-4">
		<button class="btn btn-md btn-primary " type="submit">
			<strong>保存</strong>
		</button>
		<button type="button" class="btn btn-white m-l-sm" data-dismiss="modal">取消</button>
	</div>
</div>
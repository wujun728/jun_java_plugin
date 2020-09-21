package com.yzm.vo;

import java.io.Serializable;
import java.util.Date;


public class ResponseResult implements Serializable {
	private static final long serialVersionUID = -6719552454006038350L;

	private boolean success;
	private int code;
	private String msg;
	private Object data;

	private ResponseResult(boolean success, int code, String msg, Object data) {
		this.success = success;
		this.msg = msg;
		this.data = data;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}

	public int getCode() {
		return code;
	}

	public static class Builder {
		private boolean success;
		private String msg;
		private Object data;
		private int code;

		public Builder() {
		}

		public Builder(boolean success, String msg) {
			this.success = success;
			this.msg = msg;
		}

		public Builder(boolean success, int code, String msg) {
			this.success = success;
			this.msg = msg;
			this.code = code;
		}

		public Builder(ResponseResult rr) {
			if (rr != null) {
				this.code = rr.getCode();
				this.success = rr.isSuccess();
				this.data = rr.getData();
				this.msg = rr.getMsg();
			}
		}

		public Builder success(boolean suc) {
			this.success = suc;
			return this;
		}

		public Builder message(String msg) {
			this.msg = msg;
			return this;
		}

		public Builder data(Object obj) {
			this.data = obj;
			return this;
		}

		public Builder code(int code) {
			this.code = code;
			return this;
		}

		public Builder success() {
			this.success = Boolean.TRUE;
			this.code = 200;
			this.msg = "操作成功";
			return this;
		}

		public Builder success(String msg) {
			this.success = Boolean.TRUE;
			this.code = 200;
			this.msg = msg;
			return this;
		}

		public Builder success(String msg, int code) {
			this.success = Boolean.TRUE;
			this.code = code;
			this.msg = msg;
			return this;
		}

		public Builder fail() {
			this.success = Boolean.FALSE;
			this.code = -1;
			this.msg = "操作失败";
			return this;
		}

		public Builder fail(String msg) {
			this.success = Boolean.FALSE;
			this.code = -1;
			this.msg = msg;
			return this;
		}

		public Builder fail(String msg, int code) {
			this.success = Boolean.FALSE;
			this.code = code;
			this.msg = msg;
			return this;
		}

		public ResponseResult build() {
			return new ResponseResult(this.success, this.code, this.msg, this.data);
		}
	}

}

package com.jun.admin;

import java.io.Serializable;


public class Status implements Serializable{

		private static final long serialVersionUID = 3969334232834793862L;
		
		/**
		 * 返回状态
		 */
		private String status = "";
		

		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @param status the status to set
		 */
		public Status setStatus(String status) {
			this.status = status;
			return this;
		}

		public Status() {
			super();
		}

		public Status(String status) {
			super();
			this.status = status;
		}
}

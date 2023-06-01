
package com.springboot.util;

public class Pager {
	private Integer startRecord;
	private Integer endRecord;

	private Integer pageIndex;
	private Integer pageSize;

	public Pager() {

	}

	public Pager(Integer startRecord, Integer endRecord) {
		this.startRecord = startRecord;
		this.endRecord = endRecord;
		if (null == startRecord || startRecord < 0 || null == endRecord || endRecord < 0 || endRecord < startRecord) {
			this.pageSize = 10;
			this.pageIndex = 1;
		} else {
			this.pageSize = endRecord - startRecord + 1;
			this.pageIndex = endRecord / this.pageSize;
			if (endRecord % this.pageSize > 0) {
				this.pageIndex += 1;
			}
		}
	}

	public Integer getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(Integer startRecord) {
		this.startRecord = startRecord;
	}

	public Integer getEndRecord() {
		return endRecord;
	}

	public void setEndRecord(Integer endRecord) {
		this.endRecord = endRecord;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public Integer getPageIndex() {
		return this.pageIndex;
	}
}

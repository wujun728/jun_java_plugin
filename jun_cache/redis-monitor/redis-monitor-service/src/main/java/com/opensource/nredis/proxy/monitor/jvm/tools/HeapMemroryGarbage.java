/**
 * 
 */
package com.opensource.nredis.proxy.monitor.jvm.tools;

/**
 * @author liubing
 *
 */
public class HeapMemroryGarbage {
	
	/**
	 * 年轻代回收总量
	 */
	private Long youngCollectionCount;
	
	/**
	 * 年轻代回收时间
	 */
	private Long youngCollectionTime;
	
	/**
	 * 老年代回收总量
	 */
	private Long oldCollectionCount;
	
	/**
	 * 老年代回收时间
	 */
	private Long oldCollectionTime;
	

	/**
	 * @return the youngCollectionCount
	 */
	public Long getYoungCollectionCount() {
		return youngCollectionCount;
	}

	/**
	 * @param youngCollectionCount the youngCollectionCount to set
	 */
	public void setYoungCollectionCount(Long youngCollectionCount) {
		this.youngCollectionCount = youngCollectionCount;
	}

	/**
	 * @return the youngCollectionTime
	 */
	public Long getYoungCollectionTime() {
		return youngCollectionTime;
	}

	/**
	 * @param youngCollectionTime the youngCollectionTime to set
	 */
	public void setYoungCollectionTime(Long youngCollectionTime) {
		this.youngCollectionTime = youngCollectionTime;
	}

	/**
	 * @return the oldCollectionCount
	 */
	public Long getOldCollectionCount() {
		return oldCollectionCount;
	}

	/**
	 * @param oldCollectionCount the oldCollectionCount to set
	 */
	public void setOldCollectionCount(Long oldCollectionCount) {
		this.oldCollectionCount = oldCollectionCount;
	}

	/**
	 * @return the oldCollectionTime
	 */
	public Long getOldCollectionTime() {
		return oldCollectionTime;
	}

	/**
	 * @param oldCollectionTime the oldCollectionTime to set
	 */
	public void setOldCollectionTime(Long oldCollectionTime) {
		this.oldCollectionTime = oldCollectionTime;
	}
	
	
}

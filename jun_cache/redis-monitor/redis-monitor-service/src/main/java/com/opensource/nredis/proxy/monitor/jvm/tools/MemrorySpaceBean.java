/**
 * 
 */
package com.opensource.nredis.proxy.monitor.jvm.tools;

/**
 * 内存分配信息
 * @author liubing
 *
 */
public class MemrorySpaceBean {
	
		private  long edenInit;
	    private  long edenUsed;
	    private  long edenMax;
	    private  long edenCommitted;
	    private  long survivorInit;
	    private  long survivorUsed;
	    private  long survivorMax;
	    private  long survivorCommitted;
	    private  long oldInit;
	    private  long oldUsed;
	    private  long oldMax;
	    private  long oldCommitted;
	    private  long permInit;
	    private  long permUsed;
	    private  long permMax;
	    private  long permCommitted;
		/**
		 * @return the edenInit
		 */
		public long getEdenInit() {
			return edenInit;
		}
		/**
		 * @param edenInit the edenInit to set
		 */
		public void setEdenInit(long edenInit) {
			this.edenInit = edenInit;
		}
		/**
		 * @return the edenUsed
		 */
		public long getEdenUsed() {
			return edenUsed;
		}
		/**
		 * @param edenUsed the edenUsed to set
		 */
		public void setEdenUsed(long edenUsed) {
			this.edenUsed = edenUsed;
		}
		/**
		 * @return the edenMax
		 */
		public long getEdenMax() {
			return edenMax;
		}
		/**
		 * @param edenMax the edenMax to set
		 */
		public void setEdenMax(long edenMax) {
			this.edenMax = edenMax;
		}
		/**
		 * @return the survivorInit
		 */
		public long getSurvivorInit() {
			return survivorInit;
		}
		/**
		 * @param survivorInit the survivorInit to set
		 */
		public void setSurvivorInit(long survivorInit) {
			this.survivorInit = survivorInit;
		}
		/**
		 * @return the survivorUsed
		 */
		public long getSurvivorUsed() {
			return survivorUsed;
		}
		/**
		 * @param survivorUsed the survivorUsed to set
		 */
		public void setSurvivorUsed(long survivorUsed) {
			this.survivorUsed = survivorUsed;
		}
		/**
		 * @return the survivorMax
		 */
		public long getSurvivorMax() {
			return survivorMax;
		}
		/**
		 * @param survivorMax the survivorMax to set
		 */
		public void setSurvivorMax(long survivorMax) {
			this.survivorMax = survivorMax;
		}
		/**
		 * @return the oldInit
		 */
		public long getOldInit() {
			return oldInit;
		}
		/**
		 * @param oldInit the oldInit to set
		 */
		public void setOldInit(long oldInit) {
			this.oldInit = oldInit;
		}
		/**
		 * @return the oldUsed
		 */
		public long getOldUsed() {
			return oldUsed;
		}
		/**
		 * @param oldUsed the oldUsed to set
		 */
		public void setOldUsed(long oldUsed) {
			this.oldUsed = oldUsed;
		}
		/**
		 * @return the oldMax
		 */
		public long getOldMax() {
			return oldMax;
		}
		/**
		 * @param oldMax the oldMax to set
		 */
		public void setOldMax(long oldMax) {
			this.oldMax = oldMax;
		}
		/**
		 * @return the permInit
		 */
		public long getPermInit() {
			return permInit;
		}
		/**
		 * @param permInit the permInit to set
		 */
		public void setPermInit(long permInit) {
			this.permInit = permInit;
		}
		/**
		 * @return the permUsed
		 */
		public long getPermUsed() {
			return permUsed;
		}
		/**
		 * @param permUsed the permUsed to set
		 */
		public void setPermUsed(long permUsed) {
			this.permUsed = permUsed;
		}
		/**
		 * @return the permMax
		 */
		public long getPermMax() {
			return permMax;
		}
		/**
		 * @param permMax the permMax to set
		 */
		public void setPermMax(long permMax) {
			this.permMax = permMax;
		}
		/**
		 * @return the edenCommitted
		 */
		public long getEdenCommitted() {
			return edenCommitted;
		}
		/**
		 * @param edenCommitted the edenCommitted to set
		 */
		public void setEdenCommitted(long edenCommitted) {
			this.edenCommitted = edenCommitted;
		}
		/**
		 * @return the survivorCommitted
		 */
		public long getSurvivorCommitted() {
			return survivorCommitted;
		}
		/**
		 * @param survivorCommitted the survivorCommitted to set
		 */
		public void setSurvivorCommitted(long survivorCommitted) {
			this.survivorCommitted = survivorCommitted;
		}
		/**
		 * @return the oldCommitted
		 */
		public long getOldCommitted() {
			return oldCommitted;
		}
		/**
		 * @param oldCommitted the oldCommitted to set
		 */
		public void setOldCommitted(long oldCommitted) {
			this.oldCommitted = oldCommitted;
		}
		/**
		 * @return the permCommitted
		 */
		public long getPermCommitted() {
			return permCommitted;
		}
		/**
		 * @param permCommitted the permCommitted to set
		 */
		public void setPermCommitted(long permCommitted) {
			this.permCommitted = permCommitted;
		}
	    
	    
}

package cn.skynethome.redisx.common.ms;

import java.util.Set;

import redis.clients.jedis.HostAndPort;
/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common.ms]    
  * 文件名称:[MasterSlaveHostAndPort]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2016年12月5日 下午6:05:28]   
  * 修改人:[陆文斌]   
  * 修改时间:[2016年12月5日 下午6:05:28]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class MasterSlaveHostAndPort {
	
	private final String masterName;
	
	private final HostAndPort master;
	
	private final Set<HostAndPort> slaves;

	public MasterSlaveHostAndPort(String masterName, HostAndPort master,
			Set<HostAndPort> slaves) {
		super();
		this.masterName = masterName;
		this.master = master;
		this.slaves = slaves;
	}

	public String getMasterName() {
		return masterName;
	}

	public HostAndPort getMaster() {
		return master;
	}

	public Set<HostAndPort> getSlaves() {
		return slaves;
	}


	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((master == null) ? 0 : master.hashCode());
		result = prime * result
				+ ((masterName == null) ? 0 : masterName.hashCode());
		result = prime * result
				+ ((slaves == null) ? 0 : slaves.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterSlaveHostAndPort other = (MasterSlaveHostAndPort) obj;
		if (master == null) {
			if (other.master != null)
				return false;
		} else if (!master.equals(other.master))
			return false;
		if (masterName == null) {
			if (other.masterName != null)
				return false;
		} else if (!masterName.equals(other.masterName))
			return false;
		if (slaves == null) {
			if (other.slaves != null)
				return false;
		} else if (!slaves.equals(other.slaves))
			return false;
		return true;
	}

	public String toString() {
		return "{masterName=" + masterName + ", master=" + master + ", slaves="
				+ slaves + "}";
	}
	
}

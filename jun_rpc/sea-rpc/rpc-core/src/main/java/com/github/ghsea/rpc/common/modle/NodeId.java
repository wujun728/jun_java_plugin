package com.github.ghsea.rpc.common.modle;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class NodeId implements Serializable {
	private static final long serialVersionUID = -4778329527016331036L;

	private String host;

	private int port;

	public NodeId() {

	}

	public NodeId(String host,int port){
		this.host=host;
		this.port=port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof NodeId)) {
			return false;
		}

		NodeId another = (NodeId) obj;
		return new EqualsBuilder().append(this.getHost(), another.getHost()).append(this.getPort(), another.getPort())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}

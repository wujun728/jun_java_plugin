package cn.rjzjh.commons.util.assistbean;

import org.xml.sax.Attributes;

public class NodeSax {
	private final String nodeName;
	private String nodeValue;
	private Attributes attributes;

	public NodeSax(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
}

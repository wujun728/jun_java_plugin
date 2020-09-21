package cn.rjzjh.commons.util.assistbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import cn.rjzjh.commons.util.apiext.StringUtil;

public class EasyUINode implements Comparable<EasyUINode> {

	private String id;// 扩展，easyui可以没有id，但在真实环境中必须要有id

	private String text;

	public EasyUINode(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public EasyUINode(String id) {
		this.id = id;
	}

	private String iconCls;

	private boolean isClose;// 默认false是打开的

	private Boolean checked;

	private EasyUINode parent;

	private int index = 99999;// 默认排到后面

	private Map<String, String> attributes;

	private List<EasyUINode> childrens;// SortedSet不允许排序相同的对象。

	private final Map<String, EasyUINode> allSubMap = new HashMap<String, EasyUINode>();// 所有的子孙节点都放到此map中方便检索

	public EasyUINode getSubById(String id) {
		return allSubMap.get(id);
	}

	public Map<String, EasyUINode> getSubs() {
		return allSubMap;
	}

	public void addRootMap(EasyUINode add) {
		allSubMap.put(add.getId(), add);
	}

	public JSONObject toJson() {
		Validate.notBlank(this.id);
		Validate.notBlank(this.text);
		JSONObject retobj = new JSONObject("id", this.id, "text", this.text,
				"index", index);
		retobj.put(
				"parentId",
				parent == null ? "" : StringUtil.hasNull(parent.getIconCls(),
						""));
		if (StringUtils.isNotBlank(this.iconCls))
			retobj.put("iconCls", this.iconCls);
		if (checked != null)
			retobj.put("checked", checked.booleanValue());
		if (MapUtils.isNotEmpty(attributes)) {
			JSONObject attr = new JSONObject();
			for (String key : attributes.keySet()) {
				attr.put(key, attributes.get(key));
			}
			retobj.put("attributes", attr);
		}
		if (CollectionUtils.isNotEmpty(childrens)) {// 是目录
			if (isClose)
				retobj.put("state", "closed");
			else
				retobj.put("state", "open");

			Collections.sort(childrens);
			JSONArray childAry = new JSONArray();
			for (EasyUINode easyUINode : childrens) {
				childAry.put(easyUINode.toJson());
			}
			retobj.put("children", childAry);
		}
		return retobj;
	}

	@Override
	public int compareTo(EasyUINode o) {
		EasyUINode tempobj = (EasyUINode) o;
		return this.index - tempobj.index;
	}

	public void addChildres(EasyUINode addNode) {
		if (childrens == null) {
			childrens = new ArrayList<EasyUINode>();
		}
		List<EasyUINode> list = new ArrayList<EasyUINode>();
		list.add(addNode);
		putAllSubMap(list);
		childrens.add(addNode);
	}

	public void addChildres(List<EasyUINode> addNodes) {
		if (CollectionUtils.isNotEmpty(addNodes)) {
			if (childrens == null) {
				childrens = new ArrayList<EasyUINode>();
			}
			putAllSubMap(addNodes);
			childrens.addAll(addNodes);
		}
	}

	private void putAllSubMap(List<EasyUINode> addNodes) {
		if (CollectionUtils.isEmpty(addNodes)) {
			return;
		}
		for (EasyUINode subNode : addNodes) {
			subNode.setParent(this);// 设置真正的父节点
			putAncestor(this, subNode);
		}
	}

	/***
	 * 把子节点加到祖先节点的Map中
	 * 
	 * @param parent
	 * @param sub
	 */
	private static void putAncestor(EasyUINode parent, EasyUINode sub) {
		if (parent == null || sub == null) {
			return;
		}
		parent.addRootMap(sub);
		if (parent.getParent() != null) {
			putAncestor(parent.getParent(), sub);
		}
	}

	/*
	 * @Override public boolean equals(Object obj) { EasyUINode temp =
	 * (EasyUINode) obj; return this.id.equals(temp.id); }
	 */

	/*
	 * @Override public int hashCode() { return this.id.hashCode(); }
	 */

	public void addAttributes(String... attr) {
		if (attributes == null) {
			attributes = new HashMap<String, String>();
		}
		if (ArrayUtils.isNotEmpty(attr)) {
			for (int i = 0; i < attr.length / 2; i++) {
				attributes.put(attr[2 * i], attr[2 * i + 1]);
			}
		}
	}

	public String getAttribute(String attrName) {
		return attributes.get(attrName);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	public EasyUINode getParent() {
		return parent;
	}

	public void setParent(EasyUINode parent) {
		this.parent = parent;
	}

}

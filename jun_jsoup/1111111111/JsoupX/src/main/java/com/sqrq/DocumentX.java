package com.sqrq;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 */
public class DocumentX {

  /** 封装 Jsoup Document */
  private Document doc;

  /** 主节点集 */
  private Elements all;

  /** 临时节点集 */
  private Elements currentEles;


  //
  // 构造方法
  //

  public DocumentX() {
    // this.all();
  }

  public DocumentX(Document doc) {
    this.currentEles = doc.getAllElements();
    this.doc = doc;
    this.all();
  }

  /**
   * @param eles
   * @return 不产生新实例
   */
  public DocumentX parse(Elements eles) {
    currentEles = eles;
    return this;
  }

  /**
   * @return 现有节点数
   */
  public int getEleCount() {
    return this.currentEles.size();
  }

  
  //
  // 所有已实现方法
  //

  /**
   * 返回主节点集(初始化主节点)
   * 
   * @return
   */
  public Elements all() {
    if (all == null || all.size() <= 0)
      if (doc != null)
        all = doc.getAllElements();
    
    return all;
  }

  /**
   * 设置主节点集
   * 
   * @param allEle
   * @return all
   */
  public Elements all(Elements allEle) {
    this.all = allEle;
    return all;
  }

  /**
   * 返回本次查找节点集
   * 
   * @return currentEles
   */
  public Elements currentEles() {
    return this.currentEles;
  }

  /**
   * 按属性过虑
   * 
   * @param propertyName
   * @param propertyVal
   * @return
   */
  public DocumentX byAttr(String propertyName, String propertyVal) {
    Elements eles = new Elements();
    for (Element e : this.currentEles) {
      String val = e.attr(propertyName);
      if (val != null && val.length() > 0 && val.equalsIgnoreCase(propertyVal))
        eles.add(e);
    }
    
    return parse(eles);
  }

  /**
   * 过虑掉属性不匹配
   * 
   * @param propertyName
   * @param propertyVal
   * @return
   */
  public DocumentX byAttrValNot(String propertyName, String propertyVal) {
    Elements els = new Elements();
    for (Element el : currentEles) {
      String val = el.attr(propertyName);
      if(val != propertyVal)
        els.add(el);
    }

    return parse(els);
  }

  /**
   * 按属性值开始特征部分过虑
   * 
   * @param propertyName
   * @param propertyValStart
   * @return
   */
  public DocumentX byAttrValueStarting(String propertyName, String propertyValStart) {
    Elements eles = new Elements();
    for (Element e : this.currentEles) {
      String val = e.attr(propertyName);
      if (val != null && val.length() > 0 && val.startsWith(propertyValStart))
        eles.add(e);
    }

    return parse(eles);
  }

  /**
   * 按节点属性结束特征过虑
   * 
   * @param propertyName
   * @param propertyValEnd
   * @return
   */
  public DocumentX byAttrValueEnding(String propertyName, String propertyValEnd) {
    Elements eles = new Elements();
    for (Element e : this.currentEles) {
      String val = e.attr(propertyName);
      if (val != null && val.length() > 0 && val.endsWith(propertyValEnd))
        eles.add(e);
    }

    return parse(eles);
  }

  /**
   * 按属性包含特征字串过虑
   * 
   * @param propertyName
   * @param propertyValContain
   * @return
   */
  public DocumentX byAttrValueContaining(String propertyName, String propertyValContain) {
    Elements eles = new Elements();
    for (Element e : this.currentEles) {
      String val = e.attr(propertyName);
      if (val != null && val.length() > 0 && val.contains(propertyValContain))
        eles.add(e);
    }

    
    return parse(eles);
  }

  /**
   * 按属性值匹配正则表达式进行过虑
   * 
   * @param name
   * @param val
   * @return
   */
  public DocumentX byAttrValueMatching(String name, String val) {
    Elements eles = new Elements();
    for (Element e : this.currentEles)
      if (e.attr(name) != null && e.attr(name).equals(val))
        eles.add(e);

    return parse(eles);
  }

  /**
   * 从当前节点集(currentEles)中过虑出包含特征代码的节点
   * 
   * @param html
   * @return
   */
  public DocumentX containText(String html) {
    Elements eles = new Elements();
    for (Element e : this.currentEles) {
      String htm = e.html();
      if (htm != null && htm.length() > 0 && htm.contains(html))
        eles.add(e);
    }

    return parse(eles);
  }

  /**
   * 按标签名过虑当前所有节点
   * 
   * @param tagName
   * @return
   */
  public DocumentX byTag(String tagName) {
    Elements eles = new Elements();
    for (Element e : this.currentEles)
      if (e.tagName().equalsIgnoreCase(tagName)) eles.add(e);

    return parse(eles);
  }

/**
   * 解析当前临时节点集的第 [idx] 个节点的子节点
   * 
   * @param idx 下标(从 0 起始)
   * @return
   * @throws Exception
   */
  public DocumentX subEle(int idx) throws Exception {
    if (idx < 0 || idx > this.currentEles.size())
      throw new Exception("idx 超出当前节点数范围   (节点长度：" + 0 + " -> " + this.currentEles.size() + ")");

    parse(this.currentEles.get(idx).getAllElements());
    return this;
  }

  /**
   * 取当前节点集的第一个子节点来解析
   * 
   * @return
   * @throws Exception
   */
  public DocumentX subEle() throws Exception {
    return this.subEle(0);
  }

  /**
   * 解析当前临时节点集的所有子节点
   * 
   * @return
   */
  public DocumentX subEles() {
    Elements els = new Elements();
    if (this.currentEles.size() > 0) {
      parse(this.currentEles.get(0).getAllElements());
      for (int i = 0; i < currentEles.size(); i++) {
        els.add(currentEles.get(i));
      }
    }
    return this;
  }

  //
  // 其它附加方法
  //


  /**
   * 
   * @return 您修改后, 整个页面的html 代码
   */
  public String html() {
    return all.toString();
  }

  public String text() {
    return all.text();
  }

  /**
   * 从临时节点集，取 <tagName 标签
   * 
   * @param tagName
   * @return
   */
  public Elements getEle(String tagName) {
    Elements eles = new Elements();
    Elements all = this.currentEles.get(0).getAllElements();

    for (Element e : all)
      if (e.tagName().equalsIgnoreCase(tagName)) eles.add(e);

    return eles;
  }

  public Elements getSubEleTag(String tagName) {
    Elements eles = new Elements();
    Elements all = this.all();
    for (Element e : all)
      if (e.tagName().equalsIgnoreCase(tagName)) eles.add(e);
    return eles;
  }

  public Elements getSubEleTag(String tagName, Integer idx) {
    Elements eles = new Elements();
    Elements all = this.all();
    Element e = all.get(idx);
    if (e.tagName().equalsIgnoreCase(tagName)) eles.add(e);
    return eles;
  }

  //
  // 附加
  //

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    
    result = prime * result + ((all == null) ? 0 : all.hashCode());
    result = prime * result + ((currentEles == null) ? 0 : currentEles.hashCode());
    result = prime * result + ((doc == null) ? 0 : doc.hashCode());
    
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    
    DocumentX other = (DocumentX) obj;
    
    if (all == null) {
      if (other.all != null) return false;
    } else if (!all.equals(other.all)) return false;
    
    if (currentEles == null) {
      if (other.currentEles != null) return false;
    } else if (!currentEles.equals(other.currentEles)) return false;
    
    if (doc == null) {
      if (other.doc != null) return false;
    } else if (!doc.equals(other.doc)) return false;
    return true;
  }

  public Element get(int idx) throws Exception {
    if(idx < 0 || idx > currentEles.size())
      throw new Exception("传入的下标(" + idx + ") 超出当前节点集的范围(" + currentEles.size() + ")");
    
    return currentEles.get(idx);
  }



}

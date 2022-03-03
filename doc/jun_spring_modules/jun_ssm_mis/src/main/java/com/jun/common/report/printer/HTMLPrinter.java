package com.jun.common.report.printer;

import java.io.IOException;
import java.io.OutputStream;

import com.jun.common.report.CssEngine;
import com.jun.common.report.Rectangle;
import com.jun.common.report.Report;
import com.jun.common.report.ReportBody;
import com.jun.common.report.ReportException;
import com.jun.common.report.Table;
import com.jun.common.report.TableCell;
import com.jun.common.report.TableRow;

public class HTMLPrinter implements Printer {
  /**
   * 编码
   */
  private String encoding = "UTF-8";
  public HTMLPrinter() {}
  
  public HTMLPrinter(String encoding) {
    this.encoding=encoding;
  }
  
  private static String getHexStr(int i) {
    String result = Integer.toHexString(i);
    if (i <= 0xf) {
      result = "0" + result;
    }
    return result;
  }

  private static String getRGBHexStr(java.awt.Color c) {
    String result = "#";
    result += getHexStr(c.getRed());
    result += getHexStr(c.getGreen());
    result += getHexStr(c.getBlue());
    return result;
  }

  private String getAlignStr(int i) {
    switch (i) {
      case Rectangle.ALIGN_LEFT:
        return "left";
      case Rectangle.ALIGN_CENTER:
        return "center";
      case Rectangle.ALIGN_RIGHT:
        return "right";
      default:
        throw new RuntimeException("�޷�ʶ���align����");
    }
  }

  private String getVAlignStr(int i) {
    switch (i) {
      case Rectangle.VALIGN_TOP:
        return "top";
      case Rectangle.VALIGN_MIDDLE:
        return "middle";
      case Rectangle.VALIGN_BOTTOM:
        return "bottom";
      default:
        throw new RuntimeException("�޷�ʶ���valign����");
    }
  }

  private String getMultiParamStr(Rectangle r) {
    StringBuffer buf = new StringBuffer();
    buf.append(" border='").append(r.getBorder()).append("'")
        .append(" bordercolor='").append(getRGBHexStr(r.getBackgroudColor()))
        .append("'")
        .append(" bgcolor='").append(getRGBHexStr(r.getBackgroudColor()))
        .append("'")
        .append(" align='").append(getAlignStr(r.getAlign())).append("'")
        .append(" valign='").append(getVAlignStr(r.getValign())).append("'")
        .append(" height='").append(r.getHeight()).append("%'");
    return buf.toString();
  }

  public void print(Report r, OutputStream result) throws ReportException, IOException {
    print(r, new HTMLCss(), result);
  }

  public void print(Report r, HTMLCss css, OutputStream result) throws ReportException, IOException {
      result.write(css.toString().getBytes(encoding));
      if (r.getHeaderTable() != null) {
    	  print(r.getHeaderTable(), result);
      }
      if (r.getBody() != null) {
          print(r.getBody(), result);
      }
      if (r.getFooterTable() != null) {
          print(r.getFooterTable(), result);
      }
  }
  private void print(ReportBody body, OutputStream result) throws ReportException, IOException {
    Table data = body.getData().deepClone();
    Table header = body.getTableColHeader();
    header = CssEngine.applyCss(header);
    if (header != null) {
      for (int i = header.getRowCount() - 1; i >= 0; i--) {
        data.insertRow(0, header.getRow(i));
      }
    }
    print(data, result);
  }

  private void print(Table t,OutputStream result) throws ReportException, IOException {
    StringBuffer buf = new StringBuffer();
    t = CssEngine.applyCss(t);
    buf.append("\n<table ")
        .append(" style='").append(t.getStyle()).append("' ")
        .append(getMultiParamStr(t))
        .append(" cellPadding='").append(t.getCellpadding()).append("'")
        .append(" cellSpacing='").append(t.getCellspacing()).append("'")
        .append(" width='").append(t.getWidth()).append("%'")        
        .append(">\n");
    result.write(buf.toString().getBytes(encoding));

    for (int i = 0; i < t.getRowCount(); i++) {
        print(t.getRow(i), t.getWidths(),result);
    }
    result.write("</table>\n".getBytes(encoding));
  }

  private void print(TableRow tr,int[] widths, OutputStream result) throws ReportException, IOException {
    StringBuffer buf = new StringBuffer();
    buf.append("<tr ").append(">\n");
    result.write(buf.toString().getBytes(encoding));
    int width=-1;
    for (int j = 0; j < tr.getCellCount(); j++) {
        TableCell tc = tr.getCell(j);
        if(widths!=null && widths.length>j){
            width=widths[j];
        }
        print(tc,width, result);
    }
    result.write("</tr>\n".getBytes(encoding));

  }

  private void print(TableCell tc,int width, OutputStream result) throws ReportException, IOException {
      if (!tc.isHidden()) {
    	  StringBuffer buf = new StringBuffer();
	      buf.append("<th height='20' rowspan=").append(tc.getRowSpan())
	          .append(" colspan=").append(tc.getColSpan()).append(" ");
	      if (!tc.getCssClass().equals(Report.NONE_TYPE)){
	          buf.append(" class='").append(tc.getCssClass()).append("'");
	      }
	      buf.append(" width=\""+width+"%\"");

	      if(tc.getCssClass().equals(Report.CROSS_HEAD_HEAD_TYPE)){
	          buf.append(" nowrap=\"true\"");
	      }else{
	          buf.append(" nowrap=\""+tc.isNoWrap()+"\"");
	      }
	      buf.append(" style='").append(tc.getCssStyle()).append("'")
	          .append(getMultiParamStr(tc)).append(">");
	      result.write(buf.toString().getBytes(encoding));

	      if(tc.getCssClass().equals(Report.CROSS_HEAD_HEAD_TYPE)){
	        String blank="&nbsp;&nbsp;&nbsp;&nbsp;";
	        String[] strs=PrinterUtil.getCrossHeadHeadContent(tc);
	        for (int i = 0; i < strs.length; i++) {
	          if(i!=0){
	              result.write("<br>".getBytes(encoding));
	          }
	          for (int j = 0; j < strs.length-i-1; j++) {
	              result.write(blank.getBytes(encoding));
	          }
	          if(strs[i]!=null){
	              result.write(strs[i].getBytes(encoding));
	          }
	        }
	      }else{
	        result.write(((String)tc.getContent()).getBytes(encoding));
	      }
	      result.write("</th>\n".getBytes(encoding));
      }
  }
}

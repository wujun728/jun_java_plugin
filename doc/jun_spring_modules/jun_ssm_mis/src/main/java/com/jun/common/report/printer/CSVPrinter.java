package com.jun.common.report.printer;

import java.io.IOException;
import java.io.OutputStream;

import com.jun.common.report.Report;
import com.jun.common.report.ReportBody;
import com.jun.common.report.ReportException;
import com.jun.common.report.Table;
import com.jun.common.report.TableCell;
import com.jun.common.report.TableRow;

public class CSVPrinter implements Printer {
  public CSVPrinter() {}

  /**
   * 打印报表到输出流。
   * @param r 报表
   * @param result 输出流
   * @throws com.amos.report.ReportException
   * @throws IOException
   */
  public void print(Report r, OutputStream result) throws ReportException,IOException {
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

  /**
   * 打印报表主体到输出流。
   * @param body 报表主体
   * @param result 输出流
   * @throws com.amos.report.ReportException
   * @throws IOException
   */
  private void print(ReportBody body, OutputStream result) throws ReportException,IOException {
    Table data = body.getData();
    Table header = body.getTableColHeader();
    if (header != null) {
      for (int i = header.getRowCount() - 1; i >= 0; i--) {
        data.insertRow(0, header.getRow(i));
      }
    }
    print(data, result);

  }

  /**
   * 获得单元格的内容
   * @param tc
   * @return
   * @throws ReportException
   */
  private String print(TableCell tc) throws ReportException {
    StringBuffer buf = new StringBuffer();
    if(tc.getCssClass().equals(Report.CROSS_HEAD_HEAD_TYPE)){
      String[] strs = PrinterUtil.getCrossHeadHeadContent(tc);
      for(int i=strs.length-1;i>=0;i--){
        if(i!=strs.length-1){
            buf.append("/");
        }
        buf.append(strs[i]);
      }
    }else{
        buf.append( (String) tc.getContent());
    }
    String result = buf.toString();
    //替换关键符号。将英文的逗号替换为中文的逗号；将换行符、回车符号替换为空格
    result = result.replaceAll(",", "，");
    result = result.replaceAll("\r\n", " ");
    result = result.replaceAll("\n", " ");
    return result;
  }

  /**
   * 打印表格到输出流
   * @param t 表格
   * @param result 输出流
   * @throws com.amos.report.ReportException
   * @throws IOException
   */
  private void print(Table t, OutputStream result) throws ReportException,IOException {
    StringBuffer buf = new StringBuffer();
    final String delimiter = ",";
    TableRow tempTR = null;
    TableCell tempCell = null;
    int colCount = 0;
    for (int i = 0; i < t.getRowCount(); i++) {
      tempTR = t.getRow(i);
      colCount = t.getColCount();
      for (int j = 0; j < colCount; j++) {
        tempCell = tempTR.getCell(j);
        if (tempCell.isHidden() == false) {
          buf.append(print(tempCell));
        }
        if (j < colCount - 1) {
          buf.append(delimiter);
        } else {
          buf.append("\r\n");
        }
      }
    }
    result.write(buf.toString().getBytes());
  }
}

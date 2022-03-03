package com.jun.common.report;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import com.jun.common.report.cross.CrossTable;
import com.jun.common.report.cross.HeadCol;
import com.jun.common.report.grouparithmetic.GroupArithmetic;

public class ReportManager {
  private boolean debug = false;

  public static final int ROW_ORIENTATION = 1;

  public static final int COLUMN_ORIENTATION = 2;
  
  private boolean isAmonge(int a, int[] col) {
    for (int i = 0; i < col.length; i++) {
      if (a == col[i]) {
        return true;
      }
    }
    return false;
  }

  public TableRow getTotalRow(Table t, int from, int end, String memo,
      int[] totalCols, GroupArithmetic arith) throws ReportException {
    TableRow totalRow = new TableRow();

    int[] tempTotalCols = (int[]) totalCols.clone();
    Arrays.sort(tempTotalCols);
    int nonTotal = tempTotalCols[0] - 1;

    TableCell desc = new TableCell(memo);
    desc.setColSpan(nonTotal + 1);
    totalRow.addCell(desc);
    for (int i = 0; i < nonTotal; i++) {
      TableCell nullCell = new TableCell("");
      nullCell.setHidden(true);
      totalRow.addCell(nullCell);
    }

    for (int k = 0; k < t.getColCount(); k++) {
      if (k > nonTotal) {
        TableCell c = new TableCell("");
        if (isAmonge(k, totalCols)) {
          double[] values = new double[end - from];
          for (int j = from; j < end; j++) {
            try {
              if (debug) {
                System.out.println("row:" + j + " col:" + k);
              }
              if(t.getRow(j).getCell(k).getContent()!=null){
            	  values[j - from] =Double.parseDouble( (String) t.getRow(j).getCell(k).getContent());
              }else{
            	  values[j - from] =0;
              }
            } catch (NumberFormatException e) {

            } catch (Exception ex){
            
            }
          }
          c.setContent(arith.getResult(values) + "");
        }
        totalRow.addCell(c);
      }
    }

    return totalRow;
  }

  public Table split(Table t, int[] cols) throws ReportException {
    for (int i = 1; i < cols.length; i++) {
      TableColumn pre = t.getCol(i - 1);
      TableColumn curr = t.getCol(i);
      for (int j = 0; j < pre.getCellCount(); j++) {
        if (pre.getCell(j).isHidden() == false) {
          if (curr.getCell(j).isHidden() == false || j == 0) {
            continue;
          }
          for (int k = j - 1; k >= 0; k--) {
            if (curr.getCell(k).isHidden() == false) {
              curr.getCell(j).setRowSpan(curr.getCell(k).getRowSpan() - (j - k));
              curr.getCell(k).setRowSpan(j - k);
              break;
            }
          }
          curr.getCell(j).setHidden(false);
        }
      }
    }
    return t;
  }

  public Table formatData(Table t, CrossTable crossTab, Formatter f) throws ReportException {
    return formatData(t, crossTab.getColHeader().length, t.getRowCount(),
           crossTab.getRowHeader().length, t.getColCount(), f);
  }


  public Table formatData(Table t, int fromRow, int toRow, int fromCol,
    int toCol, Formatter f) throws ReportException {
    String str;
    for (int i = fromRow; i < toRow; i++) {
      for (int j = fromCol; j < toCol; j++) {
        try {
          str = f.format( (String) t.getCell(i, j).getContent());
          t.getCell(i, j).setContent(str);
        } catch (IllegalArgumentException e) {
             throw new ReportException("IllegalArgumentException at formatData");
        } catch (ParseException e) {
        	throw new ReportException("ParseException at formatData");
        }
      } 
    }
    return t;
  }

  public Table formatData(Table t, int[] cols, Formatter f) throws
      ReportException {
    for (int i = 0; i < cols.length; i++) {
      formatData(t, 0, t.getRowCount(), cols[i], cols[i] + 1, f);
    }
    return t;
  }

  public TableLine mergeSameCell(TableLine line) throws ReportException {
    String curr = null;
    String pre = null; 
    int pointer = 0;
    int count = 1;
    for (int i = 0; i < line.getCellCount(); i++) {
      curr = (String) line.getCell(i).getContent();
      if (i > 0) {
        if ( (curr != null && curr.equals(pre)) || (curr == null && pre == null)) {
          count++;
          line.setSpan(line.getCell(pointer), count);
          line.getCell(i).setHidden(true);
        } else {
          pointer = i; 
          count = 1; 
        }
      }
      pre = curr;
    }
    return line;
  }

  public static TableLine mergeSameCell(TableLine line,int height) throws ReportException {
    String curr = null;
    String pre = null;
    int pointer = 0;
    int count = 1; 
    for (int i = 0; i < height; i++) {
      curr = (String) line.getCell(i).getContent();
      if (i > 0) {
        if ( (curr != null && curr.equals(pre)) || (curr == null && pre == null)) {
          count++;
          line.setSpan(line.getCell(pointer), count);
          line.getCell(i).setHidden(true);
        } else {
          pointer = i;
          count = 1; 
        }
      }
      pre = curr;
    }
    curr = null;
    pre = null;
    pointer = 0;
    count = 1;
    for (int i = line.getCellCount()-height; i < line.getCellCount(); i++) {
        curr = (String) line.getCell(i).getContent();
        if (i > 0) {
          if ( (curr != null && curr.equals(pre)) || (curr == null && pre == null)) {
            count++;
            line.setSpan(line.getCell(pointer), count);
            line.getCell(i).setHidden(true);
          } else { 
            pointer = i; 
            count = 1; 
          }
        }
        pre = curr;
      }
    return line;
  }
  
  public Table mergeSameCells(Table t, int[] lines, int orientation) throws
      ReportException {
    for (int i = 0; i < lines.length; i++) {
      if (orientation == this.ROW_ORIENTATION) {
        t.setRow(lines[i], (TableRow) mergeSameCell(t.getRow(lines[i])));
      } else if (orientation == this.COLUMN_ORIENTATION) {
        t.setCol(lines[i], (TableColumn) mergeSameCell(t.getCol(lines[i])));
      }
    }
    return t;
  }

  public Set getDistinctSet(TableLine line, Vector seq) throws ReportException {
    Comparator comp = new SortComparator(seq);
    Vector contents = new Vector();
    for (int i = 0; i < line.getCellCount(); i++) {
        contents.add(line.getCell(i).getContent());
    }
    TreeSet temp = new TreeSet(contents);
    TreeSet result = new TreeSet(comp);
    result.addAll(temp);
    return result;
  }

  private int searchRow(int[] col, Object[] val, Table t) throws
      ReportException {
    if (col.length != val.length) {
      throw new ReportException("The length between col and val is not same!");
    }
    int rt = -1;
    if (t == null || t.getRowCount() <= 0) {
      return rt;
    }

    for (int i = 0; i < t.getRowCount(); i++) {
      boolean tag = true;
      for (int j = 0; j < col.length; j++) {
        if (debug) {
          System.out.println(t.getCell(i, col[j]).getContent() + "--" + val[j]);
        }
        if (!t.getCell(i, col[j]).getContent().equals(val[j])) {
          tag = false;
          break;
        }
      }
      if (tag == true) {
        if (debug){
          System.out.println("kk:" + tag);
        }
        return i;
      }
    }
    if (debug) {
      System.out.println("searchRow end");
    }
    return rt;
  }

  public Table generateCrossTabColTotal(Table t, CrossTable crossTab,
                                   boolean isSubTotal,GroupArithmetic arith) throws ReportException {
    int[] cols = new int[t.getRowCount() - crossTab.getColHeader().length];
    for (int i = 0; i < cols.length; i++) {
        cols[i] = i + crossTab.getColHeader().length;
    }
    Table result = CssEngine.applyCss(t);
    result = result.getRotateTable();
    result = generateRowTotal(result, crossTab.getRowHeader().length,
                              result.getRowCount(), cols, isSubTotal, arith);
    result = CssEngine.applyCss(result);
    result = result.getRotateTable();
    return result;
  }

  public Table generateCrossTabRowTotal(Table t, CrossTable crossTab,
                                    boolean isSubTotal,GroupArithmetic arith) throws ReportException {
    int[] totalCols = new int[t.getColCount() - crossTab.getRowHeader().length];
    for (int i = 0; i < totalCols.length; i++) {
        totalCols[i] = i + crossTab.getRowHeader().length;
    }
    Table result = generateRowTotal(t, crossTab.getColHeader().length,t.getRowCount(), totalCols, isSubTotal,arith);
    return result;
  }

  public Table generateRowTotalEx(Table t, int[] totalCols, String total,
			GroupArithmetic arith) throws ReportException {
      return generateRowTotalEx(t, 0, t.getRowCount(), totalCols, total,arith);
  }

	private Table generateRowTotalEx(Table t, int fromRow, int toRow,
			int[] totalCols, String total, GroupArithmetic arith)
			throws ReportException {
		if (t == null || t.getRowCount() == 0){
		    return t;
		}
		Table tempTable = (Table) t.deepClone(); 
		TableRow insertTR = getTotalRow(tempTable, fromRow, toRow, total,totalCols, arith);
		insertTR.setType(Report.TOTAL_TYPE);
		t.addRow(insertTR);
		return t;
	}

  public Table generateRowTotal(Table t,int[] totalCols,
                            boolean isSubTotal, GroupArithmetic arith) throws ReportException {
      return generateRowTotal(t,0,t.getRowCount(),totalCols,isSubTotal,arith);
  }

  private Table generateRowTotal(Table t, int fromRow, int toRow, int[] totalCols,
          boolean isSubTotal, GroupArithmetic arith) throws ReportException {
    if (t == null || t.getRowCount() == 0) {
        return t;
    }
    Table tempTable = (Table) t.deepClone();
    int count = t.getRowCount();
    int tempToRow = toRow;

    if (isSubTotal) {
      int pointer = fromRow;
      int prePointer = pointer; 
      while (pointer <= tempToRow) {
        if (debug){
            System.out.println("p:" + pointer + " pre:" + prePointer + " rowCount:" + t.getRowCount());
        }
        TableColumn tc = t.getCol(0);
        if (pointer > 0) {
          if (pointer == t.getRowCount()) {
            TableRow insertTR = getTotalRow(t, prePointer, pointer, "subtotal",totalCols, arith);
            insertTR.setType(Report.GROUP_TOTAL_TYPE);
            t.insertRow(pointer, insertTR);
            if (debug){
              System.out.println("get one group while pre=" + prePointer + " and p=" + pointer);
            }
            break; 
          } else if (!tc.getCell(pointer).getContent().equals(
              tc.getCell(prePointer).getContent())) {
            TableRow insertTR = getTotalRow(t, prePointer, pointer, "subtotal",totalCols, arith);
            insertTR.setType(Report.GROUP_TOTAL_TYPE);
            t.insertRow(pointer, insertTR);
            if (debug){
                System.out.println("get one group while pre=" + prePointer + " and p=" + pointer);
            }
	        tempToRow++;
            pointer++;
            prePointer = pointer;
          }
        }
        pointer++;
      }
    }

    TableRow insertTR = getTotalRow(tempTable, fromRow, toRow, "total", totalCols,arith);
    insertTR.setType(Report.TOTAL_TYPE);
    t.addRow(insertTR);
    return t;
  }

  public Table generateCrossTab(Table srcTab, CrossTable crossTab) throws ReportException {
    Table result = new Table();
    Vector tempRH = new Vector();
    for (int i = 0; i < crossTab.getRowHeader().length; i++) {
      tempRH.add(getDistinctSet(srcTab.getCol(crossTab.getRowHeader()[i].
          getIndex()),crossTab.getRowHeader()[i].getSortSeq()));
    }
    
    Vector tempCH = new Vector();
    for (int i = 0; i < crossTab.getColHeader().length; i++) {
      tempCH.add(getDistinctSet(srcTab.getCol(crossTab.getColHeader()[i].getIndex()),
          crossTab.getColHeader()[i].getSortSeq()));
    }

    TableLine[] rowHead = getHeadForCross(tempRH, TableColumn.class);
    TableLine[] colHead = getHeadForCross(tempCH, TableRow.class);

    for (int i = 0; i < rowHead.length; i++) {
        result.addCol( (TableColumn) rowHead[i]);
    }

    for (int i = 0; i < ( (TableLine) colHead[0]).getCellCount(); i++) {
        result.addCol(new TableColumn(rowHead[0].getCellCount()));
    }

    for (int i = colHead.length - 1; i >= 0; i--) {
      TableRow tempTR = (TableRow) colHead[i];
      for (int j = 0; j < rowHead.length; j++) {
        TableCell cell = new TableCell("");
        tempTR.insertCell(cell, 0);
      }
      result.insertRow(0, tempTR);
    }

    for (int i = colHead.length; i < result.getRowCount(); i++) {
      for (int j = rowHead.length; j < result.getColCount(); j++) {
      	String tm = getCrossValByHead(srcTab, result,crossTab, i,j);
      	if (Double.parseDouble(tm)==0){
      	    tm = "";
      	}
        result.getCell(i,j).setContent(tm);
      }
    }
    
    for (int i = 0; i < colHead.length; i++) {
      for (int j = 0; j < rowHead.length; j++) {
        if (! (i == 0 && j == 0)) {
          result.getCell(i, j).setHidden(true);
        }
      }
    }
    int len = crossTab.getColHeader().length + crossTab.getRowHeader().length;

    TableCell headHeadCell = result.getCell(0, 0);
    headHeadCell.setColSpan(rowHead.length);
    headHeadCell.setRowSpan(colHead.length);
    headHeadCell.setCssStyle("margin: 0px;padding: 0px;");
    headHeadCell.setCssClass(Report.CROSS_HEAD_HEAD_TYPE);
    headHeadCell.setContent(crossTab);
    return result;
  }


  private double[] findRows(Table t, int[] cols, String[] cmpVals,
                            int valueCol) throws ReportException {
    if (cols == null || cmpVals == null) {
        throw new ReportException("cols or cmpValues can not be null");
    }
    if (cols.length != cmpVals.length) {
        throw new ReportException("The length between cols and cmpVals is not same!");
    }
    Vector tmpResult = new Vector();
    for (int i = 0; i < t.getRowCount(); i++) {
      boolean pass = true;
      for (int j = 0; j < cols.length; j++) {
        if (! ( (String) t.getCell(i, cols[j]).getContent()).equals(cmpVals[j])) {
          pass = false;
          break;
        }
      }
      if (pass) {
        tmpResult.add(t.getRow(i).getCell(valueCol).getContent());
      }
    }
    double[] result = new double[tmpResult.size()];
    for (int i = 0; i < result.length; i++) {
        try{
        	result[i] = Double.parseDouble( (String) tmpResult.elementAt(i));
        }catch(Exception ex){
            result[i] = 0;
        }
    }
    return result;
  }

  
  private String getCrossValByHead(Table srcTab, Table destTab,
      CrossTable crossTab, int row, int col) throws ReportException {
    HeadCol[] headCols = crossTab.getColHeader();
    HeadCol[] headRows = crossTab.getRowHeader();

    String[] cmpVals = new String[headCols.length + headRows.length];
    int[] cols = new int[headCols.length + headRows.length];

    for (int i = 0; i < headCols.length; i++) {
      cols[i] = headCols[i].getIndex();
      cmpVals[i] = (String) destTab.getCell(i, col).getContent();

    }
    for (int i = 0; i < headRows.length; i++) {
      cols[i + headCols.length] = headRows[i].getIndex();
      cmpVals[i +headCols.length] = (String) destTab.getCell(row, i).getContent();

    }
    double[] values = findRows(srcTab, cols, cmpVals,
             crossTab.getCrossCol().getIndex());
    
    if (!crossTab.getCrossCol().getArith().getResult(values).equals("0")){
    	return crossTab.getCrossCol().getArith().getResult(values);
    }else{
        return "";
    }
  }

  private TableLine[] getHeadForCross(Vector orglLine, Class cls) throws
      ReportException {
    TableLine[] lines = new TableLine[orglLine.size()];
    try {
      for (int i = 0; i < orglLine.size(); i++) {
        int span = 1;
        for (int j = i + 1; j < orglLine.size(); j++) {
          span *= ( (Set) orglLine.elementAt(j)).size();
        }

        int repeat = 1;
        for (int j = 0; j < i; j++) {
          repeat *= ( (Set) orglLine.elementAt(j)).size();
        }

        lines[i] = (TableLine) cls.newInstance();
        for (int j = 0; j < repeat; j++) {
          Iterator itr = ( (Set) orglLine.elementAt(i)).iterator();
          while (itr.hasNext()) {
            String value = (String) itr.next();
            TableCell cell = new TableCell(value);
            lines[i].setSpan(cell, span);
            lines[i].addCell(cell);
            for (int k = 0; k < span - 1; k++) {
              TableCell hiddenCell = new TableCell(value);
              hiddenCell.setHidden(true);
              lines[i].addCell(hiddenCell);
            }
          } 
        } 
        lines[i].setType(Report.HEAD_TYPE);

        for (int k = 0; k < lines[i].getCellCount(); k++) {
            lines[i].getCell(k).setCssClass(lines[i].getType());
        }

        if (debug){
            System.out.println("count:" + lines[i].getCellCount() + " repeat:" +
                repeat + " span:" + span + " size:" +
                ((Set)orglLine.elementAt(i)).size());
        }
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new ReportException(ex.toString());
    }
    return lines;
  }
}
package com.jun.common.report;

import java.util.*;


public class SortComparator implements Comparator {
  /**按照此序列中的顺序排序.*/
  private Vector sequence;


  public SortComparator(Vector seq) {
    sequence = seq;
  }


  private int getIndex(Object o) {
    if (sequence != null) {
      for (int i = 0; i < sequence.size(); i++) {
        if (o.equals(sequence.elementAt(i))) {
          return i;
        }
      }
    }
    return -1;
  }


  public int compare(Object o1, Object o2) {
    int ind1 = getIndex(o1);
    int ind2 = getIndex(o2);
    int result = 0;
    if (ind1 >= 0) {
      if (ind2 >= 0) {
        if (ind1 < ind2) {
          result = -1;
        } else if (ind1 == ind2) {
          result = 0;
        } else {//即：(ind1 > ind2)
          result = 1;
        }
      } else {
        result = -1;
      }
    } else {
      result = 1;
    }
    return result;
  }

}
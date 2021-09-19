package com.scala.sort

/**
 * Created by Lemonjing on 2015/4/24.
 * Github: Lemonjing
 */
object QuickSortScala {
  def quickSort(a: Array[Int], low: Int, high: Int): Unit = {
    if (a == null || a.length < 2) {
      return
    }
    if (low < high) {
      val mid = partition(a, low, high)
      quickSort(a, low, mid - 1)
      quickSort(a, mid + 1, high)
    }
  }

  private def partition(a: Array[Int], low: Int, high: Int): Int = {
    val pivot = a(low)
    var _low = low
    var _high = high

    while (_low < _high) {
      while (_low < _high && a(_high) >= pivot) {
        _high -= 1
      }
      a(_low) = a(_high)
      while (_low < _high && a(_low) <= pivot) {
        _low += 1
      }
      a(_high) = a(_low)
    }
    a(_low) = pivot
    _low
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(28, 16, 32, 12, 60, 2, 5, 72)
    quickSort(arr, 0, arr.length - 1)
    arr.foreach(println)
  }
}

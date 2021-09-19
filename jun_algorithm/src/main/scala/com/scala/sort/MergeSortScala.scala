package com.scala.sort

/**
 * Created by Lemonjing on 2018/4/30.
 * Github: Lemonjing
 */
object MergeSortScala {

  def mergeSort(a: Array[Int], low:Int, high:Int): Unit = {
    if (a == null || a.length < 2) {
      return
    }
    val mid = (low + high) /2
    if (low < high) {
      mergeSort(a, low, mid)
      mergeSort(a, mid+1, high)
      merge(a, low, mid, high)
    }
  }

  def merge(a:Array[Int], low:Int, mid:Int, high:Int):Unit = {
    val temp:Array[Int] = new Array[Int](high - low + 1)
    var i = low
    var j = mid + 1
    var k  = 0

    while (i <= mid && j<= high) {
      if (a(i) < a(j)) {
        temp(k) = a(i)
        i += 1
        k += 1
      } else {
        temp(k) = a(j)
        j += 1
        k += 1
      }
    }

    while (i <= mid) {
      temp(k) = a(i)
      i += 1
      k += 1
    }

    while (j <= high) {
      temp(k) = a(j)
      j += 1
      k += 1
    }

    for (t <- 0 until temp.length) {
      a(low + t) = temp(t)
    }
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(28, 16, 32, 12, 60, 2, 5, 72)
    mergeSort(arr, 0, arr.length - 1)
    arr.foreach(println)
  }
}

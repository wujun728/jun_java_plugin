package com.scala.sort

/**
 * Created by Lemonjing on 2018/4/18.
 * Github: Lemonjing
 * 折半查找
 */
object HalfSearchScala {
  def halfSearch(a: Array[Int], k: Int):Int = {
    if (a == null || a.length <= 0) {
      return -1
    }
    var low = 0
    var high = a.length -1
    while (low <= high) {
      val mid = (low + high) / 2
      if (k < a(mid)) {
        high = mid - 1
      } else if (k > a(mid)) {
        low = mid + 1
      } else {
        return mid
      }
    }
    -1
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(1,2,3,4,5,6)
    println(java.util.Arrays.binarySearch(arr, 4))
    println(halfSearch(arr, 4))
  }
}

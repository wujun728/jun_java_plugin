package com.scala.sort

/**
 * Created by Lemonjing on 2018/4/30.
 * Github: Lemonjing
 */
object SelectSortScala {

  def selectSort(a:Array[Int]):Unit = {
    if (a == null || a.length < 2) {
      return
    }
    for (i <- 0 until a.length) {
      var k = i
      for (j <- i + 1 until a.length) {
        if (a(j) < a(k)) {
          k = j
        }
      }
      if (k != i) {
        val temp = a(i)
        a(i) = a(k)
        a(k) = temp
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(28, 16, 32, 12, 60, 2, 5, 72)
    selectSort(arr)
    arr.foreach(println)
  }
}

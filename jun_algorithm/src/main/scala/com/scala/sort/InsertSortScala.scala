package com.scala.sort

/**
 * Created by Lemonjing on 2018/4/30.
 * Github: Lemonjing
 */
object InsertSortScala {

  def insertSort(a: Array[Int]): Unit = {
    if (a == null || a.length < 2) {
      return
    }
    for (i <- 1 until a.length) {
      val temp = a(i)
      var j = i - 1
      while (j >= 0 && temp < a(j)) {
        a(j+1) = a(j)
        j -= 1
      }
      a(j + 1) = temp
    }
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(28, 16, 32, 12, 60, 2, 5, 72)
    insertSort(arr)
    arr.foreach(println)
  }
}

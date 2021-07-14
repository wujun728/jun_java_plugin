package com.scala.sort

/**
 * Created by Lemonjing on 2018/4/29.
 * Github: Lemonjing
 */
object BubbleSortScala {

  def bubbleSort(a: Array[Int]): Unit = {
    if (a == null || a.length < 2) {
      return
    }
    for (i <- 0 until a.length) {
      var flag: Boolean = false
      for (j <- 0 until a.length -1 - i) {
        if (a(j) > a(j+1)) {
          val temp = a(j+1)
          a(j+1) = a(j)
          a(j) = temp
          flag = true
        }
      }
      if (flag == false) {
        return
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(28, 16, 32, 12, 60, 2, 5, 72)
    bubbleSort(arr)
    arr.foreach(println)
  }
}

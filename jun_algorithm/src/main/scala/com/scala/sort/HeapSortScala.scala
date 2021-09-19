package com.scala.sort

/**
 * Created by Lemonjing on 2018/4/30.
 * Github: Lemonjing
 */
object HeapSortScala {

  def heapSort(a: Array[Int]): Unit = {
    if (a == null || a.length < 2) {
      return
    }
    buildMaxHeap(a)
    for (i <- 0 until a.length reverse) {
      val temp = a(i)
      a(i) = a(0)
      a(0) = temp
      adjustHeap(a, i, 0)
    }
  }

  private def buildMaxHeap(a: Array[Int]): Unit = {
    val mid = a.length / 2
    for (i <- 0 until mid reverse) {
      adjustHeap(a, a.length, i)
    }
  }

  private def adjustHeap(a: Array[Int], size: Int, parent: Int):Unit = {
    val left = 2 * parent + 1
    val right = 2 * parent + 2
    var largest = parent
    while (left < size && a(left) > a(largest)) {
      largest = left
    }
    while (right < size && a(right) > a(largest)) {
      largest = right
    }
    if (largest != parent) {
      val temp = a(largest)
      a(largest) = a(parent)
      a(parent) = temp
      adjustHeap(a, size, largest)
    }
  }

  def main(args: Array[String]): Unit = {
    val arr = Array(28, 16, 32, 12, 60, 2, 5, 72)
    heapSort(arr)
    arr.foreach(println)
  }
}

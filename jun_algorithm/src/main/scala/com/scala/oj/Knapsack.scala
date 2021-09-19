package com.scala.oj

/**
 * Created by Lemonjing on 2018/6/5.
 * Github: Lemonjing
 *
 * 01背包状态转移方程：dp(i,j) = Max(dp(i-1,j), dp(i-1, j-w[i]) + v[i]) 一维数组法内循环逆序
 * 完全背包（物品数目无限）状态转移方程：f[i][j] = Max(f[i-1][j],f[i][j-w(i)]+v[i]) 一维数组法内循环正序
 * 多重背包解法：多重背包问题限定了一种物品的个数，解决多重背包问题，只需要把它转化为0-1背包问题即可。
 * 比如，有2件价值为5，重量为2的同一物品，我们就可以分为物品a和物品b，a和b的价值都为5，重量都为2，但我们把它们视作不同的物品。
 */
object Knapsack {

  // 二维数组法
  def Knapsack01(w: Array[Int], v: Array[Int], capacity: Int, n: Int): Int = {
    val maxValue = Array.ofDim[Int](n + 1, capacity + 1)
    for (i <- 1 until n + 1) {
      for (j <- 1 until capacity + 1) {
        if (j >= w(i - 1)) {
          maxValue(i)(j) = math.max(maxValue(i - 1)(j), maxValue(i - 1)(j - w(i - 1)) + v(i - 1))
        } else {
          // 二维数组放不下要加入这一行
          maxValue(i)(j) = maxValue(i - 1)(j)
        }
      }
    }
    maxValue(n)(capacity)
  }

  // 一维数组法（数组压缩）
  def Knapsack01WithCompact(w: Array[Int], v: Array[Int], capacity: Int, n: Int): Int = {
    val maxValue = Array.ofDim[Int](capacity + 1)
    for (i <- 1 until n + 1) {
      for (j <- w(i - 1) until capacity + 1 reverse) {
          maxValue(j) = math.max(maxValue(j), maxValue(j - w(i - 1)) + v(i - 1))
      }
    }
    maxValue(capacity)
  }

  def KnapsackComplete(w: Array[Int], v: Array[Int], capacity: Int, n: Int): Int = {
    val maxValue = Array.ofDim[Int](capacity + 1)
    for (i <- 1 until n + 1) {
      // 注意完全背包一维数组法内循环的正序
      for (j <- w(i - 1) until capacity + 1) {
        maxValue(j) = math.max(maxValue(j), maxValue(j - w(i - 1)) + v(i - 1))
      }
    }
    maxValue(capacity)
  }

  def main(args: Array[String]): Unit = {
    val capacity = 10
    val n = 3
    val w = Array(3, 11, 5)
    val v = Array(4, 5, 6)
    println(Knapsack01(w, v, capacity, n))
    println(Knapsack01WithCompact(w, v, capacity, n))
  }
}

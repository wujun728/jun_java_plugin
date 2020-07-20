package com.zccoder.java.book2.ch3.basic;

/**
 * <br>
 * 标题: 在二维数组中存储表格数据<br>
 * 描述: 程序清单3-8<br>
 * 时间: 2018/11/04<br>
 *
 * @author zc
 */
public class CompoundInterest {

    public static void main(String[] args) {
        final double startRate = 10;
        final int rates = 6;
        final int years = 10;

        // 初始化利率 10% 至 15%
        double[] interestRate = new double[rates];
        for (int j = 0; j < interestRate.length; j++) {
            interestRate[j] = (startRate + j) / 100.0;
        }

        double[][] balances = new double[years][rates];

        // 初始化余额为10000
        for (int j = 0; j < balances[0].length; j++) {
            balances[0][j] = 10000;
        }

        // 计算利息
        for (int i = 1; i < balances.length; i++) {
            for (int j = 0; j < balances[i].length; j++) {
                // 获取上一年的余额
                double oldBalance = balances[i - 1][j];
                // 计算利息
                double interest = oldBalance * interestRate[j];
                // 计算今年的余额
                balances[i][j] = oldBalance + interest;
            }
        }

        // 打印利息利率行
        for (double anInterestRate : interestRate) {
            System.out.printf("%9.0f%%", 100 * anInterestRate);
        }

        System.out.println();

        // 打印每年余额表
        for (double[] row : balances) {
            // 打印行
            for (double b : row) {
                System.out.printf("%10.2f", b);
            }
            System.out.println();
        }
    }

}

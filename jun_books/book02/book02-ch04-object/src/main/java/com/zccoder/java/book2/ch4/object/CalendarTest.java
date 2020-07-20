package com.zccoder.java.book2.ch4.object;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * <br>
 * 标题: 显示当前月的日历<br>
 * 描述: 程序清单4-1<br>
 * 时间: 2018/11/06<br>
 *
 * @author zc
 */
public class CalendarTest {

    public static void main(String[] args) {

        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();

        // 设置 data 为当月 1 号
        date = date.minusDays(today - 1);
        // 获取 星期几
        DayOfWeek weekDay = date.getDayOfWeek();
        // 1：星期一；2：星期二；...；7：星期日
        int value = weekDay.getValue();

        System.out.println("星期一 星期二 星期三 星期四 星期五 星期六 星期日");
        for (int i = 1; i < value; i++) {
            System.out.print("       ");
        }

        while (date.getMonthValue() == month) {
            System.out.printf("%6d", date.getDayOfMonth());
            if (date.getDayOfMonth() == today) {
                System.out.print("*");
            } else {
                System.out.print(" ");
            }
            date = date.plusDays(1);

            if (date.getDayOfWeek().getValue() == 1) {
                System.out.println();
            }
        }

        if (date.getDayOfWeek().getValue() != 1) {
            System.out.println();
        }
    }

}

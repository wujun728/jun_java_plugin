package com.jun.plugin.tool.test.core.time;

import java.util.Date;

import com.jun.plugin.tool.core.time.DateTool;

public class DateToolTest {

    public static void main(String[] args) {
        System.out.println(DateTool.formatDateTime(new Date()));
        System.out.println(DateTool.formatDateTime(DateTool.addYears(new Date(), 1)));
    }

}

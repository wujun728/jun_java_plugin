package com.jun.common.report;

import java.text.*;


public interface Formatter {
    public abstract String format(String str) throws ParseException;
}
package com.lxhc.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created on 2020/5/21.
 *
 * @author 拎小壶冲
 */
public class EmpBalance extends Model<EmpBalance> {
    public static final EmpBalance dao = new EmpBalance().dao();
}

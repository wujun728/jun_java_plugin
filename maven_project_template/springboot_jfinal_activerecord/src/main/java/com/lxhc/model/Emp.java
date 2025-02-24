package com.lxhc.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created on 2020/5/19.
 *
 * @author 拎小壶冲
 */
public class Emp extends Model<Emp> {
    public static final Emp dao = new Emp().dao();
}

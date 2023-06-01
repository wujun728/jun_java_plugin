package com.lzb.onlinejava.complier.subject;

import com.lzb.onlinejava.complier.dto.SubjectResult;

public interface Subject {
//    public String title = "title";
//    public String initcode = "initcode";
//    public String describe = "describe";
//    public String example = "example";

    public String getTitle();
    public String getIntitCode();
    public String getDescribe();
    public String getExample();


    /**
     * 对应界面 执行代码
     * @param javaCode
     * @return
     */
    public SubjectResult execueMethod(String javaCode);

    /**
     * 对应界面 提交
     * @param javaCode
     * @return
     */
    public SubjectResult submit(String javaCode);
}

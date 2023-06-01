package com.lzb.onlinejava.complier.subject;

/**
 * 根据 subjectid 创建 subject 的工厂类
 */
public class SubjectFactory {

    public static Subject getBySubjectId(int subjectId) {
        switch (subjectId) {
            case 1:
                return new Subject1Impl();
        }


        return null;

    }

}

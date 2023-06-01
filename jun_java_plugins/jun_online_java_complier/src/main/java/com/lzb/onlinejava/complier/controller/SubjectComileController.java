package com.lzb.onlinejava.complier.controller;

import com.lzb.onlinejava.complier.dto.SubjectDetail;
import com.lzb.onlinejava.complier.dto.SubjectResult;
import com.lzb.onlinejava.complier.subject.Subject;
import com.lzb.onlinejava.complier.subject.SubjectFactory;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectComileController {

    private Logger log = LoggerFactory.getLogger(SubjectComileController.class);

    /**
     * 对应界面 执行代码
     * @param javaSource
     * @param subjectId
     * @return
     */
    @ApiOperation("compiles")
    @PostMapping("/executeMethod")
    public SubjectResult executeMethod(String javaSource,
                                       @RequestParam(value = "subjectId", required = true) int subjectId) {
        javaSource = javaSource.replace("\"", "");
        javaSource = javaSource.replace("\\n", "");
        javaSource = javaSource.replace("+", "");
        log.info("javaSource:" + javaSource);
        log.info("subjectId:" + subjectId);
        Subject subject = SubjectFactory.getBySubjectId(subjectId);

        SubjectResult subjectResult = subject.execueMethod(javaSource);

        return subjectResult;

    }

    @PostMapping("/submit")
    public SubjectResult submit(String javaSource,
                                   @RequestParam(value = "subjectId", required = true) int subjectId) {

        javaSource = javaSource.replace("\"", "");
        javaSource = javaSource.replace("\\n", "");
        javaSource = javaSource.replace("+", "");
        log.info("javaSource:" + javaSource);
        Subject subject = SubjectFactory.getBySubjectId(subjectId);

        SubjectResult subjectResult = subject.submit(javaSource);

        return subjectResult;
    }



    @PostMapping("/getSubject")
    public SubjectDetail getSujbect(@RequestParam(value = "subjectId", required = false, defaultValue = "1") int subjectId) {
        Subject subject = SubjectFactory.getBySubjectId(subjectId);

        SubjectDetail subjectDetail = new SubjectDetail();
        subjectDetail.setTitle(subject.getTitle());
        subjectDetail.setDescribe(subject.getDescribe());
        subjectDetail.setExample(subject.getExample());
        subjectDetail.setInitcode(subject.getIntitCode());
        return subjectDetail;
    }


}

package com.jun.plugin.jpa.repository;

import java.util.List;

import com.jun.plugin.jpa.entity.OpenSurvey;

/**
 * 
 * @author Wujun
 *
 */
public interface OpenSurveyRepository {

    /**
     * 全量查询公开问卷
     * 
     */
    public OpenSurvey findOpenSurveyById(String id);

    /**
     * 全量查询公开问卷
     * 
     */
    public List<OpenSurvey> findOpenSurveyAll();

}

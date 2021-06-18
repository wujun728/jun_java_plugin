package cn.springmvc.jpa.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.springmvc.jpa.entity.OpenSurvey;
import cn.springmvc.jpa.repository.BaseRepository;
import cn.springmvc.jpa.repository.OpenSurveyRepository;

/**
 * @author Vincent.wang
 *
 */
@Repository
public class OpenSurveyRepositoryImpl implements OpenSurveyRepository {

    @Autowired
    protected BaseRepository baseRepository;

    @Override
    public OpenSurvey findOpenSurveyById(String id) {
        return baseRepository.getByPrimaryKey(OpenSurvey.class, id);
    }

    @Override
    public List<OpenSurvey> findOpenSurveyAll() {
        return baseRepository.query(" from cn.springmvc.jpa.entity.OpenSurvey ");
    }
}

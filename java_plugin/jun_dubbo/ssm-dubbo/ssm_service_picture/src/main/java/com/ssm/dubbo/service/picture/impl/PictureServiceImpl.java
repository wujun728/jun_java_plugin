package com.ssm.dubbo.service.picture.impl;

import com.ssm.dubbo.api.picture.entity.Picture;
import com.ssm.dubbo.api.picture.service.PictureService;
import com.ssm.dubbo.service.picture.dao.PictureDao;
import com.ssm.dubbo.util.AntiXssUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
@Service("pictureService")
public class PictureServiceImpl implements PictureService {
    @Resource
    private PictureDao pd;

    @Override
    public List<Picture> findPicture(Map<String, Object> map) {
        return pd.findPictures(map);
    }

    @Override
    public Long getTotalPicture(Map<String, Object> map) {
        return pd.getTotalPictures(map);
    }

    @Override
    public int addPicture(Picture picture) {
        if (picture.getPath() == null || getTotalPicture(null) > 90 || picture.getPath().length() > 100 || picture.getUrl().length() > 100) {
            return 0;
        }
        picture.setUrl(AntiXssUtil.replaceHtmlCode(picture.getUrl()));
        return pd.insertPicture(picture);
    }

    @Override
    public int updatePicture(Picture picture) {
        if (picture.getPath() == null || getTotalPicture(null) > 90 || picture.getPath().length() > 100 || picture.getUrl().length() > 100) {
            return 0;
        }
        picture.setUrl(AntiXssUtil.replaceHtmlCode(picture.getUrl()));
        return pd.updPicture(picture);
    }

    @Override
    public int deletePicture(String id) {
        return pd.delPicture(id);
    }

    @Override
    public Picture findById(String id) {
        return pd.findPictureById(id);
    }
}

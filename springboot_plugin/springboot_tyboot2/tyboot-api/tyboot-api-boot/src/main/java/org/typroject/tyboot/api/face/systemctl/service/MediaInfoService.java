package org.typroject.tyboot.api.face.systemctl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.typroject.tyboot.api.face.systemctl.enumeration.MediaType;
import org.typroject.tyboot.api.face.systemctl.model.MediaInfoModel;
import org.typroject.tyboot.api.face.systemctl.orm.dao.MediaInfoMapper;
import org.typroject.tyboot.api.face.systemctl.orm.entity.MediaInfo;
import org.typroject.tyboot.component.cache.Redis;
import org.typroject.tyboot.component.opendata.storage.Storage;
import org.typroject.tyboot.core.foundation.exception.BaseException;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.core.restful.exception.instance.BadRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 多媒体信息关联 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-12-04
 */
@Component
public class MediaInfoService extends BaseService<MediaInfoModel, MediaInfo, MediaInfoMapper> {


    //@Value("${qiniu.baseurl}")
    private String baseUrl;

    //@Value("${qiniu.bucket}")
    private String spaceName;


    @Autowired
    private RedisTemplate redisTemplate;

    //@Autowired
    private Storage storage;


    public static final String CACHEKEY_MEDIAINFO = "MEDIAINFO";

    public static final String ENTITYTYPE_SYSTEM_CONFIG = "SYSTEM_CONFIG";//系统配置图片存储

    public static final String ENTITYID_AD = "LANCHER_AD";//启动页图片存储
    public static final String ENTITYID_BANNER = "BANNER";//首页banner存储

    public static final String DEFAULT_IMAGE = "defaultImage_240x180.png";


    public MediaInfoModel createByEntity(String entityType, String entityId, String mediaType, String mediaFilename, String mediaAlias) throws Exception {

        MediaInfoModel model = new MediaInfoModel();
        model.setEntityId(entityId);
        model.setEntityType(entityType);
        model.setMediaType(mediaType);
        model.setMediaFilename(mediaFilename);
        model.setMediaAlias(mediaAlias);
        model.setMediaUrl(this.getUrl(model.getMediaType(), model.getMediaFilename()));
        model = this.createWithModel(model);
        return model;
    }


    public MediaInfoModel createMediaInfo(MediaInfoModel model) throws Exception {

        model.setMediaUrl(this.getUrl(model.getMediaType(), model.getMediaFilename()));
        model = this.createWithModel(model);
        return model;
    }


    /**
     * 按列表保存媒体信息；
     *
     * @param entityType
     * @param entityId
     * @param models
     * @return
     * @throws Exception
     */
    public List<MediaInfoModel> createByList(String entityType, String entityId, List<MediaInfoModel> models) throws Exception {
        List<MediaInfoModel> returnModels = new ArrayList<>();
        for (MediaInfoModel model : models) {
            model.setEntityId(entityId);
            model.setEntityType(entityType);
            if (ValidationUtil.isEmpty(model.getMediaType()))
                model.setMediaType(MediaType.image.name());
            returnModels.add(this.createMediaInfo(model));
        }
        return returnModels;
    }

    private String getUrl(String mediaTypeStr, String fileName) throws Exception {
        String url = "";

        if (ValidationUtil.isEmpty(mediaTypeStr) || ValidationUtil.isEmpty(fileName))
            return url;

        MediaType mediaType = MediaType.valueOf(mediaTypeStr);
        switch (mediaType) {
            case image:
                url = baseUrl + "/" + fileName;
                break;
            case audio:
                url = baseUrl + "/" + fileName;
                break;
            case video:
                url = baseUrl + "/" + fileName;
                break;
            default:
                throw new Exception("媒体类型有误.");
        }
        return url;
    }


    /**
     * 查询 媒体列表；@TODO
     *
     * @param entityType
     * @param entityId
     * @return
     * @throws Exception
     */
    public List<MediaInfoModel> queryByEntityList(String entityType, String entityId) throws Exception {
        List<MediaInfoModel> returnModels = this.queryForListWithCache("ORDER_NUM", false, entityType, entityId);

        if (!ValidationUtil.isEmpty(returnModels)) {
            for (MediaInfoModel model : returnModels) {
                model.setMediaUrl(this.getUrl(model.getMediaType(), model.getMediaFilename()));
            }
        }
        return returnModels;
    }


    public MediaInfoModel queryByAlias(String entityType, String entityId, String mediaAlias) throws Exception {
        MediaInfoModel postMediaInfoModel = this.queryModelByParamsWithCache(entityType, entityId, mediaAlias);
        if (!ValidationUtil.isEmpty(postMediaInfoModel))
            postMediaInfoModel.setMediaUrl(this.getUrl(postMediaInfoModel.getMediaType(), postMediaInfoModel.getMediaFilename()));
        return postMediaInfoModel;
    }

    public List<MediaInfoModel> queryByAliasForList(String entityType, String entityId, String mediaAlias) throws Exception {

        List<MediaInfoModel> list = this.queryForListWithCache("ORDER_NUM", false, entityType, entityId, mediaAlias);
        if (!ValidationUtil.isEmpty(list))
            for (MediaInfoModel model : list)
                model.setMediaUrl(this.getUrl(model.getMediaType(), model.getMediaFilename()));
        return list;
    }


    public MediaInfoModel queryByAliasAndFileName(String entityType, String entityId, String mediaFilename, String mediaAlias) throws Exception {
        MediaInfoModel postMediaInfoModel = new MediaInfoModel();
        postMediaInfoModel.setEntityId(entityId);
        postMediaInfoModel.setEntityType(entityType);
        postMediaInfoModel.setMediaFilename(mediaFilename);
        postMediaInfoModel.setMediaAlias(mediaAlias);
        postMediaInfoModel = this.queryByModel(postMediaInfoModel);
        if (!ValidationUtil.isEmpty(postMediaInfoModel))
            postMediaInfoModel.setMediaUrl(this.getUrl(postMediaInfoModel.getMediaType(), postMediaInfoModel.getMediaFilename()));
        return postMediaInfoModel;
    }


    public List<Long> deleteMedia(List<MediaInfoModel> mediaInfoModels) throws Exception {
        List<Long> ids = new ArrayList<>();

        if (ValidationUtil.isEmpty(mediaInfoModels))
            return new ArrayList<>();
        for (MediaInfoModel model : mediaInfoModels)
            ids.add(model.getSequenceNbr());

        this.deleteMediaInfo(ids);
        return ids;
    }


    @Transactional(rollbackFor = {Exception.class, BaseException.class})
    public List<Long> deleteMediaInfo(List<Long> ids) throws Exception {
        for (Long id : ids) {
            MediaInfoModel mediaInfoModel = this.queryBySeq(id);
            if (!ValidationUtil.isEmpty(mediaInfoModel)) {
                //从mysql中删除
                this.deleteBySeqWithCache(id, genCacheKeyForModelList(Redis.genKey(mediaInfoModel.getEntityType(), mediaInfoModel.getEntityId())));

                //从七牛中删除
                //storage.deleteFile(MediaType.valueOf(mediaInfoModel.getMediaType()).getQiniuSpaceName(), mediaInfoModel.getMediaFilename());

            }

        }
        return ids;
    }




   /* public boolean disabledMedia(Long sequenceNbr)throws Exception
    {
        PostMediaInfoModel mediaInfoModel = this.get(sequenceNbr);
        if(!ValidationUtil.isEmpty(mediaInfoModel))
        {
            //更新数据库中的媒体信息
            if(MediaType.image.name().equals(mediaInfoModel.getMediaType()))
            {
                mediaInfoModel.setMediaInfoModels(DEFAULT_IMAGE);
                mediaInfoModel.setMediaUrl(this.getUrl(mediaInfoModel.getMediaType(),DEFAULT_IMAGE));
                this.update(mediaInfoModel);
            }

            //清理缓存
            this.deleteFromCache(mediaInfoModel);
        }
        return true;
    }*/

    @Transactional(rollbackFor = {Exception.class, BaseException.class})
    public boolean disabledMedia(String url) throws Exception {
        String[] urlSplit = url.split("/");
        String fileName = urlSplit[urlSplit.length - 1];

        if (DEFAULT_IMAGE.equals(fileName))
            throw new BadRequest("该文件已经被禁用.");

        MediaInfoModel mediaInfoModel = this.queryByFilename(fileName);
        if (!ValidationUtil.isEmpty(mediaInfoModel)) {
            //更新数据库中的媒体信息
            if (MediaType.image.name().equals(mediaInfoModel.getMediaType())) {
                mediaInfoModel.setMediaFilename(DEFAULT_IMAGE);
                mediaInfoModel.setMediaUrl(this.getUrl(mediaInfoModel.getMediaType(), DEFAULT_IMAGE));
                this.updateWithModel(mediaInfoModel);
            }

            //从七牛中删除
            storage.deleteFile(spaceName, fileName);

            //清理缓存
        } else {
            throw new BadRequest("图片不存在.");
        }
        return true;
    }


    private MediaInfoModel queryByFilename(String filename) throws Exception {
        MediaInfoModel model = new MediaInfoModel();
        model.setMediaFilename(filename);
        model = this.queryByModel(model);
        if (!ValidationUtil.isEmpty(model))
            model.setMediaUrl(this.getUrl(model.getMediaType(), model.getMediaFilename()));
        return model;

    }


    public List<Long> deleteByEntity(String entityType, String entityId) throws Exception {
        List<Long> returnArray = new ArrayList<>();
        List<MediaInfoModel> models = this.queryByEntityList(entityType, entityId);
        if (!ValidationUtil.isEmpty(models))
            returnArray = this.deleteMedia(models);
        return returnArray;
    }


}

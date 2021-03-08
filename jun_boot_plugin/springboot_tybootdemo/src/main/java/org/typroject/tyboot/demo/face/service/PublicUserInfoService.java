package org.typroject.tyboot.demo.face.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.typroject.tyboot.core.foundation.constans.CoreConstans;
import org.typroject.tyboot.core.foundation.utils.Sequence;
import org.typroject.tyboot.core.rdbms.annotation.Condition;
import org.typroject.tyboot.core.rdbms.annotation.Operator;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.demo.face.model.PublicUserInfoModel;
import org.typroject.tyboot.demo.face.orm.dao.PublicUserInfoMapper;
import org.typroject.tyboot.demo.face.orm.entity.PublicUserInfo;

import java.util.Date;
import java.util.List;


@Service
public class PublicUserInfoService extends BaseService<PublicUserInfoModel, PublicUserInfo, PublicUserInfoMapper> {


    /**
     * 创建数据
     * 使用 BaseService 提供的方法 直接保存到关系数据库   或者同时根据业务主键保存到redis
     */
    public PublicUserInfoModel createUser(PublicUserInfoModel model) throws Exception
    {
        //临时使用的userId初始化
        String userId = Sequence.generatorSmsVerifyCode6();
        model.setUserId(userId);
        model.setCreateTime(new Date());
        model.setAgencyCode(CoreConstans.CODE_SUPER_ADMIN);
        //保存到数据库,并按业务主键 userId  进行缓存,并删除按agencyCode缓存的列表数据.,业务主键不填的话默认按物理主键缓存
        //return this.createWithCache(model,model.getUserId(),genCacheKeyForModelList(model.getAgencyCode()));

        // 直接保存到数据库
        return createWithModel(model);
    }

    /**
     * 根据物理主键更新数据
     * 使用 BaseService 提供的方法 直接保存到关系数据库   或者同时根据业务主键 更新对象到redis
     */
    public PublicUserInfoModel updateUser(PublicUserInfoModel model) throws Exception
    {
        //保存到数据库,并按业务主键 userId  更新 缓存,并删除按agencyCode缓存的列表数据.
        //this.updateWithCache(model,model.getUserId(),genCacheKeyForModelList(model.getAgencyCode()));

        //直接保存到数据库
        return this.updateWithModel(model);
    }

    /**
     * 按条件 查询单个对象
     * 方法参数名需要和 PublicUserInfoModel 中定义的属性名保持一直,
     *  才能使用 queryModelByParamsWithCache 和 queryModelByParams 方法.
     *  并且 调用的方法传参顺序 要和 当前方法参数顺序保持一致.
     */
    public PublicUserInfoModel queryByUserId(String userId) throws Exception
    {
        //查询单个对象 并按参数缓存结果,下一次同样参数就直接从缓存获取.
        //this.queryModelByParamsWithCache(userId);

        return this.queryModelByParams(userId);
    }


    /**
     * 按条件查询列表，按创建时间倒叙排列
     */
    public List<PublicUserInfoModel> queryByAgencyCode(String agencyCode) throws Exception
    {
        //按参数 缓存查询结果 下一次同样参数 就直接读取缓存
        //this.queryForListWithCache("CREATE_TIME",false,agencyCode);

        //查询top列表
        //this.queryForTopList(10,"CREATE_TIME",false,agencyCode);

        return this.queryForList("CREATE_TIME",false,agencyCode);
    }


    /**
     * 按条件分页查询,
     */
    public Page<PublicUserInfoModel> queryUserPage(Page<PublicUserInfoModel> page,String agencyCode,@Condition(Operator.like) String nickName) throws Exception
    {
        return this.queryForPage(page,"CREATE_TIME",false,agencyCode,nickName);
    }

}

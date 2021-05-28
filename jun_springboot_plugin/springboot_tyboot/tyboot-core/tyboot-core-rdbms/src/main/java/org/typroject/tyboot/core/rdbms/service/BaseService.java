package org.typroject.tyboot.core.rdbms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.typroject.tyboot.component.cache.Redis;
import org.typroject.tyboot.component.cache.enumeration.CacheType;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.annotation.Condition;
import org.typroject.tyboot.core.rdbms.annotation.Operator;
import org.typroject.tyboot.core.rdbms.model.BaseModel;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by 子杨 on 2017/4/28.
 */
@SuppressWarnings("Duplicates")
public   class BaseService<V,P, M extends BaseMapper<P>> extends ServiceImpl<M,P> implements IService<P> {


    private Class<P> poClass = null;

    private Class<V> modelCalss = null;

    /**
     * 默认缓存一天
     */
    private long cacheExpire =  24*3600L;

    private boolean refreshCache = true;


    @SuppressWarnings("unchecked")
    public final   Class<P> getPoClass() {
        if (poClass == null) {
            poClass = this.currentModelClass();
        }
        return poClass;
    }


    @SuppressWarnings("unchecked")
    public final   Class<V> getModelClass() {
        if (modelCalss == null) {

            boolean superFlag = this.getClass().getSuperclass().getGenericSuperclass() instanceof ParameterizedType;
            boolean flag   = this.getClass().getGenericSuperclass() instanceof ParameterizedType;

            if(flag)
                modelCalss = (Class<V>)ReflectionKit.getSuperClassGenericType(this.getClass(),0);
            else if(superFlag)
                modelCalss = (Class<V>)ReflectionKit.getSuperClassGenericType(this.getClass().getSuperclass(),0);
        }
        return modelCalss;
    }

    /**
     * 设置默认缓存时间
     * @param cacheExpire
     */
    protected void setCacheExpire(long cacheExpire) {
        this.cacheExpire = cacheExpire;
    }

    public void setRefreshCache(boolean refreshCache) {
        this.refreshCache = refreshCache;
    }

    /**
     * 简化service层的创建操作,填充公有字段
     * @param model
     * @return  P
     */
    public final   P prepareEntity(V model) {
        P entity = null;
        entity = Bean.toPo(model,newInstance( this.getPoClass()));
        if (entity instanceof BaseEntity) {
            BaseEntity temp = (BaseEntity) entity;
            temp.setRecDate(new Date());
            if(ValidationUtil.isEmpty(temp.getRecUserId()))
                temp.setRecUserId(RequestContext.getExeUserId());
        }
        return entity;
    }


    /**
     * 拼接默认的对象缓存前缀   ERASABLE+ModelClassName+propertyValue
     * @param propertiesValue  model的属性值作为后缀，多个值得拼接起来即可
     * @return String
     */
    protected String genCacheKeyForModel(String propertiesValue)
    {
        if(ValidationUtil.isEmpty(propertiesValue))
            throw new RuntimeException("propertiesValue can not be empty or null");
        return Redis.genKey(CacheType.ERASABLE.name(),this.getModelClass().getSimpleName().toUpperCase(),propertiesValue);
    }

    /**
     * 生成列表缓存key
     * @param propertiesValue model的属性值作为后缀，多个值得拼接起来即可
     * @return
     */
    protected String genCacheKeyForModelList(String propertiesValue)
    {
        String suffix = this.getModelClass().getSimpleName().toUpperCase()+"_LIST";
        return Redis.genKey(CacheType.ERASABLE.name(),suffix,propertiesValue);
    }


    /**
     * 保存缓存信息  ,默认保存都使用Value类型
     * @param cacheKey  完整key
     * @param t         要缓存的值
     * @param <T>
     */
    public   <T>  void saveCache(String cacheKey, T t)
   {
       if(!ValidationUtil.isEmpty(cacheKey))
            Redis.getRedisTemplate().opsForValue().set(cacheKey,t,cacheExpire, TimeUnit.SECONDS);
   }

    /**
     * 删除缓存
     * @param cacheKey 完整的key
     */
   public  static void deleteFromCache(String... cacheKey)
   {
       Boolean flag = true;
       if(!ValidationUtil.isEmpty(cacheKey))
           for(String key:cacheKey)
               flag = flag && Redis.getRedisTemplate().delete(key);
   }

    /**
     * 从缓存中获取值，  默认都使用Value类型
     * @param cacheKey   完整的key
     * @param <T>    要返回的对象类型
     * @return
     */

    public  <T> T  queryFromCache(String cacheKey)
    {
        T t = null;
        if(!ValidationUtil.isEmpty(cacheKey))
        {
            t = (T)Redis.getRedisTemplate().opsForValue().get(cacheKey);
            if(this.refreshCache)
                Redis.getRedisTemplate().expire(cacheKey,cacheExpire,TimeUnit.SECONDS);
        }
        return t;
    }




    /**
     * 更新model，并更新缓存。并更新或删除指定的缓存
     * @param model         要更新的model
     * @param propertyValueAsCacheKey 缓存model所用的其属性值  为null的时候默认使用sequenceNbr，用作key的组成
     * @param deleteCacheKey        要删除的缓存key，删除使用完整的key
     * @return model
     */
    protected final   V updateWithCache(V model,String propertyValueAsCacheKey,String... deleteCacheKey) {
        model = this.updateWithModel(model);

        String cacheKey = genCacheKeyForModel(propertyValueAsCacheKey);
        if(ValidationUtil.isEmpty(propertyValueAsCacheKey))
            cacheKey =  genCacheKeyForModel(String.valueOf(((BaseModel) model).getSequenceNbr()));
        saveCache(cacheKey,model);
        deleteFromCache(deleteCacheKey);
        return model;
    }


    /**
     * 根据物理主键删除对象，一并删除所指定的缓存
     * @param seq
     * @param cacheKeyForDelete  要删除的缓存key，删除使用完整的key
     * @return
     */
    protected final   boolean deleteBySeqWithCache(Long seq,String... cacheKeyForDelete)
    {
        boolean flag =  this.deleteBySeq(seq);
        deleteFromCache(cacheKeyForDelete);
        return flag;
    }


    /**
     * 根据物理主键查询model，先以物理主键为键值从缓存中查询
     * @param seq 物理主键
     * @return
     */
    protected final    V queryBySeqWithCache(Long  seq) {
       V v = queryFromCache(genCacheKeyForModel(String.valueOf(seq)));
       if(ValidationUtil.isEmpty(v))
       {
           v = this.queryBySeq(seq);
           if(!ValidationUtil.isEmpty(v))
               saveCache(genCacheKeyForModel(String.valueOf(seq)),v);
       }
        return v;
    }


    /**
     * 根据model中的有值得字段查询对象，先根据指定字段拼接缓存键值从缓存中获取
     * @param model
     * @param propertyValueAsCacheKey
     * @return
     */
    protected final   V queryByModelWithCache(V model,String propertyValueAsCacheKey)
    {
        V v = queryFromCache(genCacheKeyForModel(propertyValueAsCacheKey));
        if(ValidationUtil.isEmpty(v))
        {
            v = this.queryByModel(model);
            if(!ValidationUtil.isEmpty(v))
                saveCache(genCacheKeyForModel(propertyValueAsCacheKey),v);
        }
        return v;
    }



    /**
     * 使用Model保存对象到RDB
     * @param model
     * @return
     */
    public final   V createWithModel(V model){
        P entity = this.prepareEntity(model);
        this.save(entity);
        return Bean.toModel(entity, model);
    }

    /**
     * 保存model包RDBMS，并缓存。和更新或删除指定的缓存
     * @param model         要保存的model
     * @param propertyValueAsCacheKey model的缓存key
     * @param deleteCacheKey        要删除的缓存key
     * @return model
     */
    protected final   V createWithCache(V model,String propertyValueAsCacheKey,String... deleteCacheKey) {
        model = this.createWithModel(model);
        String cacheKey = genCacheKeyForModel(propertyValueAsCacheKey);
        if(ValidationUtil.isEmpty(propertyValueAsCacheKey))
            cacheKey =  genCacheKeyForModel(String.valueOf(((BaseModel) model).getSequenceNbr()));
        saveCache(cacheKey,model);
        deleteFromCache(deleteCacheKey);
        return model;
    }


    /**
     * 更新对象到db TODO 处理要设置为null的情况
     * @param model
     * @return
     */
    public final   V updateWithModel(V model) {
        P entity = this.prepareEntity(model);
        this.updateById(entity);
        return Bean.toModel(entity, model);
    }


    /**
     * 根据物理主键删除对象
     * @param seq
     * @return true/false
     */
    public final   boolean deleteBySeq(Long seq)
    {
        return this.removeById(seq);
    }

    /**
     * 批量删除
     * @param seqs 物理主键列表
     * @return
     */
    public final   boolean deleteBatchSeq(List<Long> seqs)
    {
        return removeByIds(seqs);
    }

    /**
     * 根据物理主键查询对象
     * @param seq   物理主键
     * @return
     */
    public final    V queryBySeq(Long  seq) {
        return Bean.toModel(this.getById(seq), newInstance(getModelClass()));
    }


    /**
     * 根据物理主键批量查询
     * @param seqs  物理主键列表
     * @return  List<V>
     */
    public final    List<V> queryBatchSeq(List<Long>  seqs) {
        return Bean.toModels(this.getBaseMapper().selectBatchIds(seqs), getModelClass());
    }

    protected final   V queryByModel(V model)
    {
        P  entity = null;
        entity = Bean.toPo(model,newInstance(this.getPoClass()));
        QueryWrapper<P> wrapper = new QueryWrapper<>();
        wrapper.setEntity(entity);
        return Bean.toModel(this.getOne(wrapper),model);
    }



    protected final boolean queryForExits(Object... params)
    {
        if(allParamsIsNull(params))
            throw new RuntimeException("parameter params can not be empty or null  for method queryModelByParams.");
        P entity    = null;
        entity =    newInstance( this.getPoClass());
        Class clzz  = Bean.getClassByName(Thread.currentThread().getStackTrace()[2].getClassName());
        Method crurrntMethod                = Bean.getMethodByName(Thread.currentThread().getStackTrace()[2].getMethodName(),clzz);
        String[] parameterNames             = Bean.getMethodParameterNamesByAsm4(clzz, crurrntMethod);

        for(int i=0;i<params.length;i++)
        {
            if(!ValidationUtil.isEmpty(params[i]))
                Bean.setPropertyValue(parameterNames[i],params[i],entity);
        }
        QueryWrapper<P> wrapper  = new QueryWrapper<>();
        wrapper.setEntity(entity);
        wrapper.select("SEQUENCE_NBR");
        List list = this.list(wrapper);
        return !ValidationUtil.isEmpty(list);
    }



    /**
     *
     * 按指定参数获取单个对象
     * 简化service层操作，查询单个对象，参数顺序必须和前置方法一直，参数名称需要和对象的属性名称保持一样
     *
     * @param params    前置方法参数列表
     * @return
     */
    protected final    V queryModelByParams(Object... params)
    {
        if(allParamsIsNull(params))
            throw new RuntimeException("parameter params can not be empty or null  for method queryModelByParams.");

        P entity    = newInstance(this.getPoClass());
        Class clzz  = Bean.getClassByName(Thread.currentThread().getStackTrace()[2].getClassName());
        Method crurrntMethod                = Bean.getMethodByName(Thread.currentThread().getStackTrace()[2].getMethodName(),clzz);
        String[] parameterNames             = Bean.getMethodParameterNamesByAsm4(clzz, crurrntMethod);

        for(int i=0;i<params.length;i++)
        {
            if(!ValidationUtil.isEmpty(params[i]))
                Bean.setPropertyValue(parameterNames[i],params[i],entity);
        }
        QueryWrapper<P> wrapper  = new QueryWrapper<>();
        wrapper.setEntity(entity);
        return  Bean.toModel(this.getOne(wrapper),newInstance(this.getModelClass()));
    }


    protected final    V queryModelByParamsWithCache(Object... params)
    {
        if(allParamsIsNull(params))
            throw new RuntimeException("parameter params can not be empty or null  for method queryModelByParamsWithCache.");

        String [] asKey = new String[params.length];
        for(int i=0;i<params.length;i++)
            asKey[i]=String.valueOf(params[i]);

        V v = queryFromCache(genCacheKeyForModel(Redis.genKey(asKey)));
        if(ValidationUtil.isEmpty(v))
        {
            P entity    =newInstance( this.getPoClass());
            Class clzz  = Bean.getClassByName(Thread.currentThread().getStackTrace()[2].getClassName());
            Method crurrntMethod                = Bean.getMethodByName(Thread.currentThread().getStackTrace()[2].getMethodName(),clzz);
            String[] parameterNames             = Bean.getMethodParameterNamesByAsm4(clzz, crurrntMethod);

            for(int i=0;i<params.length;i++)
            {
                if(!ValidationUtil.isEmpty(params[i]))
                    Bean.setPropertyValue(parameterNames[i],params[i],entity);
            }

            QueryWrapper<P> wrapper = new QueryWrapper<>();
            wrapper.setEntity(entity);
            v = Bean.toModel(this.getOne(wrapper),newInstance(this.getModelClass()));
            if(!ValidationUtil.isEmpty(v))
                saveCache(genCacheKeyForModel(Redis.genKey(asKey)),v);
        }
        return v;
    }


    private <T> T newInstance(Class<T> clzz)
    {
        try {
            return clzz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(),e.getCause());
        }
    }

    /**
     * 只查询指定属性的值
     * @param indexName  业务主键名称
     * @param indexValue 业务主键值
     * @param propertyNames 指定属性名
     * @return V
     */
    protected final V queryForPropertiesValue(String indexName,Object indexValue,String... propertyNames)
    {
        queryFromCache(this.genCacheKeyForModel(String.valueOf(indexValue)));
        QueryWrapper<P> wrapper = new QueryWrapper<>();
        wrapper.select(Bean.propertyToColums(propertyNames));
        wrapper.eq(Bean.propertyToColum(indexName),indexValue);
        return Bean.toModel(this.getOne(wrapper),newInstance(this.getModelClass()));
    }

    /**
     * 只查询指定属性的值
     * @param indexName  业务主键名称
     * @param indexValue  业务主键值
     * @param propertyNames 指定属性名
     * @return List<V>
     */
    public final List<V> queryForMultPropertyValue(String indexName,Object indexValue,String... propertyNames)
    {
        QueryWrapper<P> wrapper = new QueryWrapper<>();
        wrapper.select(Bean.propertyToColums(propertyNames));
        wrapper.eq(Bean.propertyToColum(indexName),indexValue);
        return Bean.toModels(this.list(wrapper),this.getModelClass());
    }


    /**
     * 记录数查询
     * @param params
     * @return
     */
    protected final int queryCount(Object... params)
    {
        Wrapper<P> wrapper = this.assemblyWrapperParams(
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Bean.getClassByName(Thread.currentThread().getStackTrace()[2].getClassName()),
                params
        );
        return this.count(wrapper);
    }

    /**
     * 简化service单表查询，@TODO orderBy可以是一个字段数组
     * 列表查询，
     * @param orderBy 排序字段（数据库字段名）
     * @param isAsc   排序规则
     * @param params 前置方法参数列表，參數順序保持一致，參數名稱需要和數據庫字段名稱相互對應符合轉換規則
     * @return List<V>
     */
    protected final   List<V> queryForList(String orderBy,boolean isAsc,Object... params)
    {
        QueryWrapper<P> wrapper = this.assemblyWrapperParams(
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Bean.getClassByName(Thread.currentThread().getStackTrace()[2].getClassName()),
                params
        );
        if(!ValidationUtil.isEmpty(orderBy))
            wrapper.orderBy(true,isAsc,orderBy);
        return Bean.toModels(this.list(wrapper),this.getModelClass());
    }

    /**
     * 简化service单表查询，@TODO orderBy可以是一个字段数组
     * 限制数量的列表查询
     * @param orderBy 排序字段（数据库字段名）
     * @param isAsc   排序规则
     * @param top     取top数量
     * @param params 前置方法参数列表，參數順序保持一致，參數名稱需要和數據庫字段名稱相互對應符合轉換規則
     * @return List<V>
     */
    protected final   List<V> queryForTopList(int top,String orderBy,boolean isAsc,Object... params)
    {
        QueryWrapper<P> wrapper = this.assemblyWrapperParams(
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Bean.getClassByName(Thread.currentThread().getStackTrace()[2].getClassName()),
                params
        );
        if(!ValidationUtil.isEmpty(orderBy))
            wrapper.orderBy(true,isAsc,orderBy);

        wrapper.last("LIMIT 0,"+top);
        return Bean.toModels(this.list(wrapper),this.getModelClass());
    }

    /**
     * 列表查询，先从缓存中获取
     * @param orderBy 排序字段
     * @param isAsc 排序规则
     * @param params 参数胡列表
     * @return 返回model列表
     */
    protected final   List<V> queryForListWithCache(String orderBy,boolean isAsc,Object... params)
    {

        String [] asKey = new String[params.length];
        for(int i=0;i<params.length;i++)
            asKey[i]=String.valueOf(params[i]);


        String cacheKey = genCacheKeyForModelList(Redis.genKey(asKey));
        ArrayList<V>  list = queryFromCache(cacheKey);
        if(ValidationUtil.isEmpty(list))
        {
            QueryWrapper<P> wrapper = this.assemblyWrapperParams(
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Bean.getClassByName(Thread.currentThread().getStackTrace()[2].getClassName()),
                    params
            );
            if(!ValidationUtil.isEmpty(orderBy))
                wrapper.orderBy(true,isAsc,orderBy);
            list = Bean.toModels(this.list(wrapper),this.getModelClass());
            if(!ValidationUtil.isEmpty(list))
                saveCache(cacheKey,list);
        }
        return list;
    }


    /**
     * 简化service单表查询
     * 分页查询，只能被子类调用
     * @param page  分页实体
     * @param orderBy 排序字段
     * @param isAsc 排序规则
     * @param params 前置方法参数列表，參數順序保持一致，參數名稱需要和數據庫字段名稱相互對應符合轉換規則
     * @return  分页实体
     */
    protected final Page<V> queryForPage(Page<V> page, String orderBy, boolean isAsc, Object... params)
    {
        Page<P> entiryPage = new Page<>(page.getCurrent(),page.getSize());

        QueryWrapper<P> wrapper = this.assemblyWrapperParams(
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Bean.getClassByName(Thread.currentThread().getStackTrace()[2].getClassName()),
                params
        );

        if(!ValidationUtil.isEmpty(orderBy))
            wrapper.orderBy(true,isAsc,orderBy);
        entiryPage =(Page<P>)this.page (entiryPage,wrapper);
        if(!ValidationUtil.isEmpty(entiryPage.getRecords()))
        {

            page.setTotal(entiryPage.getTotal());
            page.setPages(entiryPage.getPages());
            page.setCurrent(entiryPage.getCurrent());
            page.setSize(entiryPage.getSize());

            if(!ValidationUtil.isEmpty(entiryPage.getRecords()))
                page.setRecords(Bean.toModels(entiryPage.getRecords(),this.getModelClass()));
        }
        return page;
    }



    /**
     * 组装查询条件，过滤为空的参数,參數順序保持一致
     * 參數名稱需要和數據庫字段名稱相互對應符合轉換規則
     * @param methodName  前置类当中不可以有方法重载
     * @param clzz        前置方法所在类
     * @param params      前置方法参数列表
     * @return Map<数据库字段,参数值>
     */
    protected  final   Map<String,Object> assemblyMapParams(String methodName, Class clzz, Object... params)
    {
        Map<String,Object> queryParamMap    = new HashMap<>();
        Method crurrntMethod                = Bean.getMethodByName(methodName,clzz);


        if(ValidationUtil.isEmpty(params) ||  allParamsIsNull(params))
            return queryParamMap;

        if(!ValidationUtil.isEmpty(crurrntMethod))
        {
            String[] parameterNames    = Bean.getMethodParameterNamesByAsm4(clzz, crurrntMethod);

            //將參數名稱轉換為字段名稱  map<属性名,字段名>
            Map<String,String> cloMap  = Bean.propertyToColum(parameterNames);

            int offset                 = parameterNames.length-params.length;//参数位置偏移量
            //組裝map格式的查詢條件
            for(int i=offset;i<parameterNames.length;i++)
            {
                if(!ValidationUtil.isEmpty(params[i-offset]))
                {
                    queryParamMap.put(
                            cloMap.get(parameterNames[i]),
                            params[i-offset]
                    );
                }
            }
        }else{

            throw new RuntimeException("can not find the method with name:"+methodName);
        }
        return queryParamMap;
    }





    /**
     *
     * 根据方法参数组装查询条件，参数名称需要与对应实体的属性名称一致，
     * 过滤为空的参数,object数组元素需要与前置方法參數順序保持一致
     * 參數名稱需要和數據庫字段名稱相互對應符合轉換規則
     * @param methodName 前置方法名称，前置类当中不可以有方法重载
     * @param clzz       前置方法所在类
     * @param params     参数列表
     * @return Wrapper   返回mybatisplus的查询组装器
     */
    protected  final QueryWrapper<P> assemblyWrapperParams(String methodName, Class clzz, Object... params)
    {

        QueryWrapper<P> returnWrapper         = new QueryWrapper<>();
        //returnWrapper.where("1=1");
        Method crurrntMethod                = Bean.getMethodByName(methodName,clzz);

        if(ValidationUtil.isEmpty(params) || allParamsIsNull(params))
            return new QueryWrapper<>();

        if(!ValidationUtil.isEmpty(crurrntMethod))
        {


            Annotation [] [] annotations    = crurrntMethod.getParameterAnnotations();

            //獲得方法的參數名稱列表
            String[] parameterNames         = Bean.getMethodParameterNamesByAsm4(clzz, crurrntMethod);

            //將參數名稱轉換為字段名稱
            Map<String,String> cloMap       = Bean.propertyToColum(parameterNames);

            int offset                      = parameterNames.length - params.length;//参数位置偏移量
            //組裝map格式的查詢條件
            for(int i=offset;i<parameterNames.length;i++)
            {
                if(!ValidationUtil.isEmpty(params[i-offset]))
                {
                    if(!ValidationUtil.isEmpty(annotations[i]) && annotations[i][0] instanceof Condition)
                    {
                        Condition operator = (Condition)annotations[i][0];
                        returnWrapper = this.paresWrapper(returnWrapper,operator.value(),cloMap.get(parameterNames[i]),params[i-offset]);
                    }else
                    {
                        //默认AND操作符
                        returnWrapper = this.paresWrapper(returnWrapper, Operator.eq,cloMap.get(parameterNames[i]),params[i-offset]);
                    }
                }
            }
        }else{
            throw new RuntimeException("can not find the method :"+methodName);
        }

        return returnWrapper;
    }

    private boolean allParamsIsNull(Object[] params)
    {
        boolean flag = true;
        if(!ValidationUtil.isEmpty(params))
            for(Object obj:params)
                if(!ValidationUtil.isEmpty(obj))
                    flag = false;
        return flag;
    }



    /**
     * 构建条件组装器，目前只实现了几个常用的条件操作符，后续继续扩展，
     * 不过不会考虑复杂的操作，和条件逻辑关系，满足常用的大部分场景即可
     * @param wrapper   条件组装器
     * @param operator  条件操作符
     * @param cloumn     对应的数据库列名
     * @param paramValue     对应的值
     * @return
     */
    private  QueryWrapper<P> paresWrapper(QueryWrapper<P> wrapper,Operator operator,String cloumn,Object paramValue)
    {
        switch (operator)
        {
            case eq:
                wrapper.eq(cloumn,paramValue);//=
                break;
            case ne:
                wrapper.ne(cloumn,paramValue);//!=  <>
                break;
            case gt:
                wrapper.gt(cloumn,paramValue);
                break;
            case ge:
                wrapper.ge(cloumn,paramValue);
                break;
            case lt:
                wrapper.lt(cloumn,paramValue);
                break;
            case le:
                wrapper.le(cloumn,paramValue);
                break;
            case like:
                wrapper.like(cloumn,String.valueOf(paramValue));
                break;
            case notLike:
                wrapper.notLike(cloumn,String.valueOf(paramValue));
                break;
            case likeLeft:
                wrapper.likeLeft(cloumn,String.valueOf(paramValue));
                break;
            case likeRight:
                wrapper.likeRight(cloumn,String.valueOf(paramValue));
                break;
            case isNull:
                wrapper.isNull(cloumn);
                break;
            case isNotNull:
                wrapper.isNotNull(cloumn);
                break;
            case in:
                if(paramValue instanceof Object[])
                {
                    wrapper.in(cloumn, ((Object[])paramValue));
                }else if(paramValue instanceof Collection)
                {
                    wrapper.in(cloumn, ((Collection)paramValue));
                }else
                {
                    throw new RuntimeException("can not be case  to Object[] or Collection");
                }
                break;
            case notIn:
                if(paramValue instanceof Object[])
                {
                    wrapper.notIn(cloumn ,((Object[])paramValue));
                }else if(paramValue instanceof Collection)
                {
                    wrapper.notIn(cloumn ,((Collection)paramValue));
                }else
                {
                    throw new RuntimeException("can not be case to Object[] or Collection");
                }
                break;
            case between:
                if(paramValue instanceof Object[] && ((Object[])paramValue).length == 2)
                {
                    Object[] objArray = (Object[])paramValue;
                    wrapper.between(cloumn,objArray[0],objArray[1]);
                }else{
                    throw new RuntimeException("can not be case to Object Array");
                }
                break;
            case notBetween:
                if(paramValue instanceof Object[] && ((Object[])paramValue).length == 2)
                {
                    Object[] objArray = (Object[])paramValue;
                    wrapper.notBetween(cloumn,objArray[0],objArray[1]);
                }else{
                    throw new RuntimeException("can not be case to Object Array");
                }
                break;
            default:
                throw new RuntimeException("not support operator: "+operator.name());
        }
        return wrapper;
    }

}

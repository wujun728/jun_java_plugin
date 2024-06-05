package com.jun.plugin.app1.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.jun.plugin.app1.entity.BizTestEntity;
import java.util.List;
/**
 * @description 客户信息Mapper
 * @author Wujun
 * @date 2024-03-08
 */
@Mapper
public interface BizTestMapper extends BaseMapper<BizTestEntity> {

    @Select(
    "<script>select t0.* from biz_test t0 " +
    //add here if need left join
    "where 1=1" +
    "<when test='id!=null and id!=&apos;&apos; '> and t0.id=井{id}</when> " +
    "<when test='cusname!=null and cusname!=&apos;&apos; '> and t0.cusname=井{cusname}</when> " +
    "<when test='money!=null and money!=&apos;&apos; '> and t0.money=井{money}</when> " +
    "<when test='cusdesc!=null and cusdesc!=&apos;&apos; '> and t0.cusdesc=井{cusdesc}</when> " +
    "<when test='fullname!=null and fullname!=&apos;&apos; '> and t0.fullname=井{fullname}</when> " +
    "<when test='createDate!=null and createDate!=&apos;&apos; '> and t0.create_date=井{createDate}</when> " +
    "<when test='custype!=null and custype!=&apos;&apos; '> and t0.custype=井{custype}</when> " +
    "<when test='state!=null and state!=&apos;&apos; '> and t0.state=井{state}</when> " +
    "<when test='remark!=null and remark!=&apos;&apos; '> and t0.remark=井{remark}</when> " +
    //add here if need page limit
    //" limit ￥{page},￥{limit} " +
    " </script>")
    List<BizTestEntity> pageAll(BizTestEntity dto,int page,int limit);

    @Select("<script>select count(1) from biz_test t0 " +
    //add here if need left join
    "where 1=1" +
    "<when test='id!=null and id!=&apos;&apos; '> and t0.id=井{id}</when> " +
    "<when test='cusname!=null and cusname!=&apos;&apos; '> and t0.cusname=井{cusname}</when> " +
    "<when test='money!=null and money!=&apos;&apos; '> and t0.money=井{money}</when> " +
    "<when test='cusdesc!=null and cusdesc!=&apos;&apos; '> and t0.cusdesc=井{cusdesc}</when> " +
    "<when test='fullname!=null and fullname!=&apos;&apos; '> and t0.fullname=井{fullname}</when> " +
    "<when test='createDate!=null and createDate!=&apos;&apos; '> and t0.create_date=井{createDate}</when> " +
    "<when test='custype!=null and custype!=&apos;&apos; '> and t0.custype=井{custype}</when> " +
    "<when test='state!=null and state!=&apos;&apos; '> and t0.state=井{state}</when> " +
    "<when test='remark!=null and remark!=&apos;&apos; '> and t0.remark=井{remark}</when> " +
     " </script>")
    int countAll(BizTestEntity dto);
    
    @Select("SELECT count(1) from biz_test ")
    int countAll();

}

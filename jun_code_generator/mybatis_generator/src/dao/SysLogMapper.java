package dao;

import java.util.List;
//import org.apache.ibatis.annotations.Param;
import pojo.SysLog;
import pojo.SysLogExample;

public interface SysLogMapper {
    int countByExample(SysLogExample example);

    int deleteByExample(SysLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    List<SysLog> selectByExampleWithBLOBs(SysLogExample example);

    List<SysLog> selectByExample(SysLogExample example);

    SysLog selectByPrimaryKey(Integer id);
//
//    int updateByExampleSelective(@Param("record") SysLog record, @Param("example") SysLogExample example);
//
//    int updateByExampleWithBLOBs(@Param("record") SysLog record, @Param("example") SysLogExample example);
//
//    int updateByExample(@Param("record") SysLog record, @Param("example") SysLogExample example);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKeyWithBLOBs(SysLog record);

    int updateByPrimaryKey(SysLog record);
}
package mybatis.mate.sm.mysql.aes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mybatis.mate.sm.mysql.aes.entity.AttrVO;
import mybatis.mate.sm.mysql.aes.entity.ComAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ComAttrMapper extends BaseMapper<ComAttr> {

    /**
     * 处理复杂情况
     */
    @Select("SELECT attr_id,email,mobile,(SELECT  CURRENT_DATE FROM DUAL) time FROM attr")
    List<AttrVO> selectVO();

    @Select("SELECT email FROM attr")
    List<ComAttr> selectVO2();
}

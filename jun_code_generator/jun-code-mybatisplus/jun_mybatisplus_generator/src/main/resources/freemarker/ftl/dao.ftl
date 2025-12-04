/**
 * @filename:${entityName}Dao ${createTime}
 * @project ${project}  ${version}
 * Copyright(c) 2020 ${author} Co. Ltd. 
 * All right reserved. 
 */
package ${daoUrl};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ${entityUrl}.${entityName};

/**   
 * @Description:TODO(${entityComment}数据访问层)
 *
 * @version: ${version}
 * @author: ${author}
 * 
 */
@Mapper
public interface ${entityName}Dao extends BaseMapper<${entityName}> {
	
}
	
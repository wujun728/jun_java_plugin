/**
 * @filename:${entityName}ServiceImpl ${createTime}
 * @project ${project}  ${version}
 * Copyright(c) 2018 ${author} Co. Ltd. 
 * All right reserved. 
 */
package ${serviceImplUrl};

import ${entityUrl}.${entityName};
import ${daoUrl}.${entityName}Dao;
import ${serviceUrl}.${entityName}Service;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * @Description:TODO(${entityComment}服务实现)
 *
 * @version: ${version}
 * @author: ${author}
 * 
 */
@Service
public class ${entityName}ServiceImpl  extends ServiceImpl<${entityName}Dao, ${entityName}> implements ${entityName}Service  {
	
}
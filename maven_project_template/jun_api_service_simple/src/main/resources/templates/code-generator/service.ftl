package ${packageService};
import java.util.Map;

import ${packageModel}.${classInfo.className};
import com.jun.plugin.codegenerator.admin.model.ReturnT;

/**
* ${classInfo.classComment}
*
* Created by wujun on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
*/
public interface ${classInfo.className}Service {

    /**
    * 新增
    */
    public ReturnT<String> insert(${classInfo.className} ${classInfo.className?uncap_first});

    /**
    * 删除
    */
    public ReturnT<String> delete(int id);

    /**
    * 更新
    */
    public ReturnT<String> update(${classInfo.className} ${classInfo.className?uncap_first});

    /**
    * Load查询
    */
    public ${classInfo.className} load(int id);

    /**
    * 分页查询
    */
    public Map<String,Object> pageList(int offset, int pagesize);

}

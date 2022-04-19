package [path_1].[path_2].service.[path_3];
import [path_1].[path_2].entity.PageResult;
import [path_1].[path_2].pojo.[path_3].[Table2];

import java.util.*;

/**
 * [comment]业务逻辑层
 */
public interface [Table2]Service {


    public List<[Table2]> findAll();


    public PageResult<[Table2]> findPage(int page, int size);


    public List<[Table2]> findList(Map<String,Object> searchMap);


    public PageResult<[Table2]> findPage(Map<String,Object> searchMap,int page, int size);


    public [Table2] findById([keyType] [key]);

    public void add([Table2] [table2]);


    public void update([Table2] [table2]);


    public void delete([keyType] [key]);

}

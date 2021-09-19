package [path_1].[path_2].service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import [path_1].[path_2].dao.[Table2]Mapper;
import [path_1].[path_2].entity.PageResult;
import [path_1].[path_2].pojo.[path_3].[Table2];
import [path_1].[path_2].service.[path_3].[Table2]Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class [Table2]ServiceImpl implements [Table2]Service {

    @Autowired
    private [Table2]Mapper [table2]Mapper;

    /**
     * 返回全部记录
     * @return
     */
    public List<[Table2]> findAll() {
        return [table2]Mapper.selectAll();
    }

    /**
     * 分页查询
     * @param page 页码
     * @param size 每页记录数
     * @return 分页结果
     */
    public PageResult<[Table2]> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<[Table2]> [table2]s = (Page<[Table2]>) [table2]Mapper.selectAll();
        return new PageResult<[Table2]>([table2]s.getTotal(),[table2]s.getResult());
    }

    /**
     * 条件查询
     * @param searchMap 查询条件
     * @return
     */
    public List<[Table2]> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return [table2]Mapper.selectByExample(example);
    }

    /**
     * 分页+条件查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<[Table2]> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<[Table2]> [table2]s = (Page<[Table2]>) [table2]Mapper.selectByExample(example);
        return new PageResult<[Table2]>([table2]s.getTotal(),[table2]s.getResult());
    }

    /**
     * 根据Id查询
     * @param [key]
     * @return
     */
    public [Table2] findById([keyType] [key2]) {
        return [table2]Mapper.selectByPrimaryKey([key2]);
    }

    /**
     * 新增
     * @param [table2]
     */
    public void add([Table2] [table2]) {
        [table2]Mapper.insert([table2]);
    }

    /**
     * 修改
     * @param [table2]
     */
    public void update([Table2] [table2]) {
        [table2]Mapper.updateByPrimaryKeySelective([table2]);
    }

    /**
     *  删除
     * @param [key]
     */
    public void delete([keyType] [key2]) {
        [table2]Mapper.deleteByPrimaryKey([key2]);
    }

    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example([Table2].class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
<条件查询.String.txt>
<条件查询.Integer.txt>
        }
        return example;
    }

}

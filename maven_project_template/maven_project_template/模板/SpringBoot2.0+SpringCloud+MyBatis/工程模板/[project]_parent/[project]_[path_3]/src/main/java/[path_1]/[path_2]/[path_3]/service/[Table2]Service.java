package [package].service;

import [package].dao.[Table2]Mapper;
import [package].pojo.[Table2];
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class [Table2]Service {

    @Autowired
    private [Table2]Mapper [table2]Mapper;

    /**
     * 全部数据
     * @return
     */
    public List<[Table2]> findAll(){
        return [table2]Mapper.selectAll();
    }

    /**
     * 根据ID查询
     * @param [key2]
     * @return
     */
    public [Table2] findById([keyType] [key2]){
        return  [table2]Mapper.selectByPrimaryKey([key2]);
    }

    /**
     * 增加
     * @param [table2]
     */
    public void add([Table2] [table2]){
        [table2]Mapper.insert([table2]);
    }

    /**
     * 修改
     * @param [table2]
     */
    public void update([Table2] [table2]){
        [table2]Mapper.updateByPrimaryKey([table2]);
    }

    /**
     * 删除
     * @param [key2]
     */
    public void delete([keyType] [key2]){
        [table2]Mapper.deleteByPrimaryKey([key2]);
    }


    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public List<[Table2]> findPage( int page, int size){
        PageHelper.startPage(page,size);
        return [table2]Mapper.selectAll();
    }

    /**
     * 条件查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    public Page<[Table2]> findPage(Map<String,Object> searchMap, int page, int size){
        PageHelper.startPage(page,size);
        Example example=new Example([Table2].class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
<条件查询.String.txt>	
        }
        return (Page<[Table2]>)[table2]Mapper.selectByExample(example);
    }

}

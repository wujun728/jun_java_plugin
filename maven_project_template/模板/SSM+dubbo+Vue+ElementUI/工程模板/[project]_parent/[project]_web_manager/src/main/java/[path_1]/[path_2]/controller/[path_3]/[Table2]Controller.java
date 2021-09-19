package [path_1].[path_2].controller.[path_3];

import com.alibaba.dubbo.config.annotation.Reference;
import [path_1].[path_2].entity.PageResult;
import [path_1].[path_2].entity.Result;
import [path_1].[path_2].pojo.[path_3].[Table2];
import [path_1].[path_2].service.[path_3].[Table2]Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/[table2]")
public class [Table2]Controller {

    @Reference
    private [Table2]Service [table2]Service;

    @GetMapping("/findAll")
    public List<[Table2]> findAll(){
        return [table2]Service.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<[Table2]> findPage(int page, int size){
        return [table2]Service.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<[Table2]> findList(@RequestBody Map<String,Object> searchMap){
        return [table2]Service.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<[Table2]> findPage(@RequestBody Map<String,Object> searchMap,int page, int size){
        return  [table2]Service.findPage(searchMap,page,size);
    }

    @GetMapping("/findById")
    public [Table2] findById([keyType] [key2]){
        return [table2]Service.findById([key2]);
    }


    @PostMapping("/add")
    public Result add(@RequestBody [Table2] [table2]){
        [table2]Service.add([table2]);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody [Table2] [table2]){
        [table2]Service.update([table2]);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete([keyType] [key2]){
        [table2]Service.delete([key2]);
        return new Result();
    }

}

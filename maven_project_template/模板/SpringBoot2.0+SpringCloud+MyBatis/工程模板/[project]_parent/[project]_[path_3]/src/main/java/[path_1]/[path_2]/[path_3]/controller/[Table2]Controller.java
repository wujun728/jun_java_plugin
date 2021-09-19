package [package].controller;

import [package].pojo.[Table2];
import [package].service.[Table2]Service;
import com.github.pagehelper.Page;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/[table2]")
@CrossOrigin
public class [Table2]Controller {


    @Autowired
    private [Table2]Service [table2]Service;

    @RequestMapping
    public Result findAll(){
        List<[Table2]> [table2]List = [table2]Service.findAll();
        return new Result(true, StatusCode.OK,"查询成功",[table2]List) ;
    }

    @RequestMapping("/{[key2]}")
    public Result findById(@PathVariable [keyType] [key2]){
        [Table2] [table2] = [table2]Service.findById([key2]);
        return new Result(true,StatusCode.OK,"查询成功",[table2]);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody [Table2] [table2]){
        [table2]Service.add([table2]);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @RequestMapping(value="/{[key2]}", method = RequestMethod.PUT)
    public Result update(@RequestBody [Table2] [table2],@PathVariable [keyType] [key2]){
        [table2].set[Key2]([key2]);
        [table2]Service.update([table2]);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/{[key2]}" ,method = RequestMethod.DELETE)
    public Result delete(@PathVariable [keyType] [key2]){
        [table2]Service.delete([key2]);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/search/{page}/{size}" ,method = RequestMethod.GET)
    public Result findPage(@PathVariable  int page, @PathVariable  int size){
        List<[Table2]> list = [table2]Service.findPage(page, size);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value = "/search/{page}/{size}" ,method = RequestMethod.POST)
    public Result findPage(@RequestBody Map searchMap, @PathVariable  int page, @PathVariable  int size){
        Page<[Table2]> pageList = [table2]Service.findPage(searchMap, page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

}

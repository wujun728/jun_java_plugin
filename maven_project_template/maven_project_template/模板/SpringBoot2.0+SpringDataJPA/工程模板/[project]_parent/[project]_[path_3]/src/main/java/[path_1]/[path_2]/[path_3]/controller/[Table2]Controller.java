package [package].controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import [package].pojo.[Table2];
import [package].service.[Table2]Service;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * [comment]控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/[table2]")
public class [Table2]Controller {

	@Autowired
	private [Table2]Service [table2]Service;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",[table2]Service.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param [key2] ID
	 * @return
	 */
	@RequestMapping(value="/{[key2]}",method= RequestMethod.GET)
	public Result findById(@PathVariable [keyType] [key2]){
		return new Result(true,StatusCode.OK,"查询成功",[table2]Service.findById([key2]));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<[Table2]> pageList = [table2]Service.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<[Table2]>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",[table2]Service.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param [table2]
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody [Table2] [table2]  ){
		[table2]Service.add([table2]);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param [table2]
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody [Table2] [table2], @PathVariable [keyType] [key2] ){
		[table2].set[Key2]([key2]);
		[table2]Service.update([table2]);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param [key2]
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable [keyType] [key2]){
		[table2]Service.deleteById([key2]);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}

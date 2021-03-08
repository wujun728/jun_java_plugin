package junit;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zt.entity.Dept;
import com.zt.service.IDeptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by CDHong on 2018/4/6.
 */
@ContextConfiguration("classpath:spring/spring-mybatis-plus.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DeptTest {

    @Autowired private IDeptService deptService;

    /**
     * 在做保存的时候，可以自己手动添加主键，也可以使用序列来完成,
     * 相应的配置需要在spring-mybatis-plus.xml配置文件中找到以下两句配置
     * <property name="idType" value="2"/>
     *  <property name="keyGenerator" ref="keyGenerator"/>
     * 如果是手动添加主键，则idType的value值改为1，keyGenerator不需要配置
     * 如果是使用序列，则使用默认配置，除此之外需要在对应的实体类上添加如下注解：
     * @KeySequence(value = "seq_dept",clazz = Integer.class)
     * value:是要使用的序列名称  clazz是接受的值类型，默认是Long类型，这个结合自己实体字段类型来更改
     */
    @Test
    public void testSave(){
        Dept dept = new Dept();
        dept.setDname("科技部");
        dept.setLoc("重庆");
        boolean flg = deptService.insert(dept);
        System.out.println(flg);
    }

    @Test
    public void testUpdate(){
        //先查询部门，再根据需求修改
        Dept dept = deptService.selectById(63);
        //设置要修改的信息
        dept.setDname("市场部");
        dept.setLoc("渝北");
        boolean flg = deptService.updateById(dept);
        System.out.println(flg);
    }

    @Test
    public void testFindAll(){
        List<Dept> deptList = deptService.selectList(null);
        for(Dept dept:deptList){
            System.out.println(dept);
        }
    }

    @Test
    public void testDel(){
        boolean flg = deptService.deleteById(62);
        System.out.println(flg);
    }

    @Test
    public void testPageInfo(){
        //Page(pageIndex,pageSize)
        Page page = new Page(1,2);
        //查询条件实体
        EntityWrapper<Dept> ew = new EntityWrapper<>();
        ew.like("dname","A").orderBy("deptno");
        Page<Dept> deptPage = deptService.selectPage(page, ew);
        System.out.println("总页数："+deptPage.getTotal());
        System.out.println("当前页码："+deptPage.getCurrent());
        System.out.println("每页显示条数："+deptPage.getSize());
        System.out.println("当前页数据："+deptPage.getRecords());
    }


}

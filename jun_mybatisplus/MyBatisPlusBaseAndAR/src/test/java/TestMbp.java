
import com.bzcoder.entity.Employee;
import com.bzcoder.mapper.EmployeeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 普通模式
 * @author BaoZhou
 * @date 2018/10/1
 */
public class TestMbp {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
    EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

    @Test
    public void insert() {
        Employee employee = new Employee();
        employee.setLastName("BZ");
        employee.setAge(27);
        employee.setEmail("123@qq.com");
        employeeMapper.insert(employee);
        System.out.println(employee.getId());
    }

    @Test
    public void select() {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 27);
        List<Employee> employees = employeeMapper.selectByMap(map);
        System.out.println(employees.toString());

        //内存分页，不是从数据表的真实分页
        IPage<Employee> employeeIPage = employeeMapper.selectPage(new Page<Employee>(2, 2), null);
        System.out.println(employeeIPage.getRecords().toString());

        //升序排列
        List<Employee> employees1 = employeeMapper
                .selectList(new QueryWrapper<Employee>()
                        .eq("gender", "1")
                        .orderByAsc("age"));
        System.out.println("===============================");
        System.out.println(employees1);
        System.out.println("===============================");


    }

    @Test
    public void selectAsc()
    {
        //升序排列
        List<Employee> employees2 = employeeMapper
                .selectList(new QueryWrapper<Employee>()
                        .eq("gender", "1")
                        .orderBy(true, true, "age"));
        System.out.println("===============================");
        System.out.println(employees2);
        System.out.println("===============================");
    }
    /**
     * 批量删除
     */
    @Test
    public void delete() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        int i = employeeMapper.deleteBatchIds(list);
        System.out.println(i);
    }

    /**
     * 根据ID删除
     */
    @Test
    public void delete2() {
        int i = employeeMapper.deleteById(6);
        System.out.println(i);
    }

    /**
     * 使用条件构造器QueryWrapper
     */
    @Test
    public void testEntityWrapperSelect() {
        IPage<Employee> page = employeeMapper.selectPage(new Page<Employee>(1, 2), new QueryWrapper<Employee>()
                .between("age", "27", "40")
                .eq("gender", "1")
                .eq("last_name", "BZ"));
        System.out.println(page.getRecords().toString());
    }

    @Test
    public void testConditionSelect() {
        List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>()
                .eq("gender", 0)
                .like("last_name", "Z")
                .or()
                .like("email", "Z"));
        System.out.println(employees.toString());

        List<Employee> employees2 = employeeMapper.selectList(new QueryWrapper<Employee>()
                .eq("gender", 0)
                .like("last_name", "Z")
                .or(i -> i.eq("last_name", "李白").ne("email", "活着")));

        System.out.println(employees2.toString());
    }
}

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzcoder.test.entity.Employee;
import com.bzcoder.test.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author BaoZhou
 * @date 2018/10/8
 */
public class TestMP {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
    EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

    @Test
    public void testPage() {
        IPage<Employee> employeeIPage = employeeMapper.selectPage(new Page<>(2, 2), null);
        System.out.println(employeeIPage.getTotal());
        System.out.println(employeeIPage.getRecords());
    }

    @Test
    public void testSQLExplain() {
        employeeMapper.delete(null);

    }

    @Test
    public void updateEmployee() {
        int id = 12;
        int version = 1;

        Employee u = new Employee();
        u.setId(id);
        u.setVersion(version);

        if (employeeMapper.updateById(u) > 0) {
            System.out.println("Update successfully");
        } else {
            System.out.println("Update failed due to modified by others");
        }
    }


    @Test
    public void insert() {
        Employee employee = new Employee();
        employee.insert();
    }
    @Test
    public void update() {
        Employee employee = new Employee();
        employee.setLastName("BZBZZ");
        employeeMapper.update(employee,new QueryWrapper<Employee>()
                .eq("id", 15));
    }

    @Test
    public void deleteLogic() {
        employeeMapper.deleteById(13);
    }

    @Test
    public void selectLogin() {
        employeeMapper.selectList(null);
    }
}

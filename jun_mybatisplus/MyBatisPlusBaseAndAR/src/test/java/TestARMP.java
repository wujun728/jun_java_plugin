import com.bzcoder.entity.Company;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AR操作
 * @author BaoZhou
 * @date 2018/10/2
 */
public class TestARMP {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
    @Test
    public void arInsert()
    {
        Company company = new Company();
        company.setOwner("bzz");
        company.setName("超级宇宙公司");
        company.setLocation("杭州");
        boolean insert = company.insert();
        System.out.println("===============================");
        System.out.println("添加状态" + insert);
        System.out.println("===============================");
    }

    @Test
    public void arSelect()
    {
        Company company = new Company();
        Company result = company.selectById(2);
        System.out.println(result);

        System.out.println("===============================");

        Company company1 = new Company();
        company1.setId(2);
        Company result2 = company1.selectById();
        System.out.println(result2);
    }

    @Test
    public  void arDelete()
    {
        Company company = new Company();
        boolean result = company.deleteById(5);
        System.out.println(result);
    }
}

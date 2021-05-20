package tqlin;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.xml.sax.SAXException;
import tqlin.entity.Address;
import tqlin.entity.Employee;

import java.io.IOException;

/**
 * 规则模块预先绑定(RulesModule接口)示例
 */
public class DigesterLoaderMain {
    private static DigesterLoader dl = DigesterLoader.newLoader(new EmployeeModule())
            .setNamespaceAware(false);

    public static void main(String[] args) throws IOException, SAXException {
        Digester digester = dl.newDigester();
        Employee employee = digester.parse(DigesterLoaderMain.class.getClassLoader().getResourceAsStream("employee.xml"));

        System.out.print(employee.getFirstName() + " ");
        System.out.print(employee.getLastName() + ", ");
        for (Address a : employee.getAddressList()) {
            System.out.print(a.getType() + ", ");
            System.out.print(a.getCity() + ", ");
            System.out.println(a.getState());
        }
    }
}

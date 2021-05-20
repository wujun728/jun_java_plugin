package tqlin;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import tqlin.entity.Address;
import tqlin.entity.Employee;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 异步解析XML示例
 */
public class AsyncParseMain {
    private static DigesterLoader dl = DigesterLoader.newLoader(new EmployeeModule())
            .setNamespaceAware(false).setExecutorService(Executors.newSingleThreadExecutor());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Digester digester = dl.newDigester();

        Future<Employee> future = digester.asyncParse(AsyncParseMain.class.getClassLoader().getResourceAsStream("employee.xml"));
        Employee employee = future.get();
        System.out.print(employee.getFirstName() + " ");
        System.out.print(employee.getLastName() + ", ");
        for (Address a : employee.getAddressList()) {
            System.out.print(a.getType() + ", ");
            System.out.print(a.getCity() + ", ");
            System.out.println(a.getState());
        }
    }
}

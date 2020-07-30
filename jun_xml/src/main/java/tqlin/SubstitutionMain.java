package tqlin;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.Substitutor;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.substitution.MultiVariableExpander;
import org.apache.commons.digester3.substitution.VariableSubstitutor;
import org.xml.sax.SAXException;
import tqlin.entity.Address;
import tqlin.entity.Employee;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * xml变量解析-Substitutor抽象类示例
 */
public class SubstitutionMain {
    private static DigesterLoader dl = DigesterLoader.newLoader(new EmployeeModule()).setNamespaceAware(false);

    public static void main(String[] args) throws IOException, SAXException {
        // set up the variables the input xml can reference
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("type", "boss");

        // map ${varname} to the entries in the var map
        MultiVariableExpander expander = new MultiVariableExpander();
        expander.addSource("$", vars);

        // allow expansion in both xml attributes and element text
        Substitutor substitutor = new VariableSubstitutor(expander);

        Digester digester = dl.newDigester();
        digester.setSubstitutor(substitutor);

        Employee employee = digester
                .parse(SubstitutionMain.class.getClassLoader().getResourceAsStream("employee$.xml"));

        System.out.print(employee.getFirstName() + " ");
        System.out.print(employee.getLastName() + ", ");
        for (Address a : employee.getAddressList()) {
            System.out.print(a.getType() + ", ");
            System.out.print(a.getCity() + ", ");
            System.out.println(a.getState());
        }
    }
}

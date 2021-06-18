package tqlin;

import org.apache.commons.digester3.binder.AbstractRulesModule;
import tqlin.entity.Address;
import tqlin.entity.Employee;

public class EmployeeModule extends AbstractRulesModule {

    @Override
    protected void configure() {
        forPattern("employee").createObject().ofType(Employee.class);
        forPattern("employee/firstName").setBeanProperty();
        forPattern("employee/lastName").setBeanProperty();

        forPattern("employee/address").createObject().ofType(Address.class).then().setNext("addAddress");
        forPattern("employee/address/type").setBeanProperty();
        forPattern("employee/address/city").setBeanProperty();
        forPattern("employee/address/state").setBeanProperty();
    }
}
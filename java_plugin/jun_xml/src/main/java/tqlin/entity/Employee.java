package tqlin.entity;


import java.util.ArrayList;
import java.util.List;

public class Employee {

    private String firstName;
    private String lastName;
    private List<Address> addressList = new ArrayList<Address>();

    public void addAddress(Address address) {
        addressList.add(address);
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the addressList
     */
    public List<Address> getAddressList() {
        return addressList;
    }

    /**
     * @param addressList the addressList to set
     */
    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}

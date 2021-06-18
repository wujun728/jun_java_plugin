package Immutable.Q4;

public final class UserInfo {
    private final StringBuffer info;
    public UserInfo(String name, String address) {
        this.info = new StringBuffer("<info name=\"" + name + "\" address=\"" + address + "\" />");
    }
    public StringBuffer getInfo() {
        return info;
    }
    public String toString() {
        return "[ UserInfo: " + info + " ]";
    }
}

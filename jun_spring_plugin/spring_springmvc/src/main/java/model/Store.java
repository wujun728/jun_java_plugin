package model;

public class Store {
    private Integer storeid;

    private String storename;

    private Integer userid;

    private Integer storetypeid;

    private Integer storedescid;

    private String storeicon;

    private Integer credent;

    private Integer credibility;

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getStoretypeid() {
        return storetypeid;
    }

    public void setStoretypeid(Integer storetypeid) {
        this.storetypeid = storetypeid;
    }

    public Integer getStoredescid() {
        return storedescid;
    }

    public void setStoredescid(Integer storedescid) {
        this.storedescid = storedescid;
    }

    public String getStoreicon() {
        return storeicon;
    }

    public void setStoreicon(String storeicon) {
        this.storeicon = storeicon;
    }

    public Integer getCredent() {
        return credent;
    }

    public void setCredent(Integer credent) {
        this.credent = credent;
    }

    public Integer getCredibility() {
        return credibility;
    }

    public void setCredibility(Integer credibility) {
        this.credibility = credibility;
    }
}
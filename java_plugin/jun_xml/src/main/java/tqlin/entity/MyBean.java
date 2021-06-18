package tqlin.entity;

public class MyBean {
    private Double rate;
    private boolean super_;

    public MyBean(Double rate, Boolean super_) {
        this.super_ = super_;
        this.rate = rate;
    }

    /**
     * @return the rate
     */
    public Double getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(Double rate) {
        this.rate = rate;
    }

    /**
     * @return the super_
     */
    public boolean isSuper_() {
        return super_;
    }

    /**
     * @param super_ the super_ to set
     */
    public void setSuper_(boolean super_) {
        this.super_ = super_;
    }
}

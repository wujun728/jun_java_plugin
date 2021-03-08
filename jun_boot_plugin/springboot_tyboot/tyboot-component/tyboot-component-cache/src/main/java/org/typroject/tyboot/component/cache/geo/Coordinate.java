package org.typroject.tyboot.component.cache.geo;

/**
 * Created by yaohelang on 2018/12/29.
 */
public class Coordinate<T> {

    private static final long serialVersionUID = 1457845418948974568L;

    private double latitude; //纬度
    private double longitude;//经度
    private T member;//位置关联对象

    public Coordinate(double latitude, double longitude, T member) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.member = member;
    }

    public Coordinate() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public T getMember() {
        return member;
    }

    public void setMember(T member) {
        this.member = member;
    }
}

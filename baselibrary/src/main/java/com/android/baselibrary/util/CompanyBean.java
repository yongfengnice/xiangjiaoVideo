package com.android.baselibrary.util;

/**
 * Created by Adminstrator on 2016/11/11.
 */

public class CompanyBean {
    String cityName;
    double longitude_x;//经度 x
    double latitude_y;//纬度 y

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLongitude_x() {
        return longitude_x;
    }

    public void setLongitude_x(double longitude_x) {
        this.longitude_x = longitude_x;
    }

    public double getLatitude_y() {
        return latitude_y;
    }

    public void setLatitude_y(double latitude_y) {
        this.latitude_y = latitude_y;
    }
}

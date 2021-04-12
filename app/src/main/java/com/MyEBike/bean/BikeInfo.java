package com.MyEBike.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwj on 21/4/5.
 */

public class BikeInfo implements Serializable {

    private static final long serialVersionUID = -758459502806858414L;
    /**
     * 精度
     */
    private double latitude;
    /**
     * 纬度
     */
    private double longitude;
    /**
     * 图片ID
     */
    private int imgId;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 距离
     */
    private String distance;
    /**
     * 赞数量
     */
    private String time;

    public static List<BikeInfo> infos = new ArrayList<BikeInfo>();


    public BikeInfo(double latitude, double longitude, int imgId, String name,
                    String distance, String time) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgId = imgId;
        this.name = name;
        this.distance = distance;
        this.time = time;
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

    public String getName() {
        return name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

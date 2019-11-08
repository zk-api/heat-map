package com.github.zk.heatmap.entity;

/**
 * @author zk
 * @date 2019/8/2 9:29
 */
public class HeatMapEntity {
    private double lat;
    private double lon;
    private double value;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

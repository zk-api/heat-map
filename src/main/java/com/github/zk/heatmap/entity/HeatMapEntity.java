package com.github.zk.heatmap.entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeatMapEntity that = (HeatMapEntity) o;
        return Double.compare(that.lat, lat) == 0 && Double.compare(that.lon, lon) == 0 && Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon, value);
    }
}

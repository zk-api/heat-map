package com.github.zk.heatmap.entity;

import java.util.Arrays;

/**
 * 图例
 *
 * @author zk
 */
public class Legend {
    /**
     * 数值范围
     */
    private Double[] value;
    /**
     * 红色
     */
    private Integer red;

    /**
     * 绿色
     */
    private Integer green;

    /**
     * 蓝色
     */
    private Integer blue;

    /**
     * 透明度
     */
    private Integer alpha;

    public Double[] getValue() {
        return value;
    }

    public void setValue(Double[] value) {
        this.value = value;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }

    @Override
    public String toString() {
        return "Legend{" +
                "value=" + Arrays.toString(value) +
                ", red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", alpha=" + alpha +
                '}';
    }
}

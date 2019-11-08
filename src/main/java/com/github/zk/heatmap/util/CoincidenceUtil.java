package com.github.zk.heatmap.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zk
 * @date 2019/8/6 13:53
 * 计算多个多变行重合部分相交点坐标
 */
public class CoincidenceUtil {
    /**
     * 计算两条线交点的坐标
     * @param line1 new Double[][]{{x1,y1},{x2,y2}}
     * @param line2 new Double[][]{{x3,y3},{x4,y4}}
     * @return
     */
    public static Double[] getIntersection(Double[][] line1, Double[][] line2) {
        Double[] intersection = new Double[2];
        double x;
        double y;
        double t = ((line2[1][1] - line2[0][1]) * (line2[0][0] - line1[0][0]) - (line2[0][1] - line1[0][1]) * (line2[1][0] - line2[0][0])) /
                ((line2[1][1] - line2[0][1]) * (line1[1][0] - line1[0][0]) - (line1[1][1] - line1[0][1]) * (line2[1][0] - line2[0][0]));
        //如果t不在[0~1]范围内，证明交点在延长线上，所以不能算做有交点
        if (t > 1 || t < 0) {
            return null;
        }
        x = line1[0][0] + (line1[1][0] - line1[0][0]) * t;
        y = line1[0][1] + (line1[1][1] - line1[0][1]) * t;
        intersection[0] = x;
        intersection[1] = y;
        return intersection;
    }

    /**
     * 计算两个面的交点坐标
     * @param plane1
     * @param plane2
     * @return
     */
    public static List<Double[]> getPlaneIntersection(Double[][] plane1, Double[][] plane2) {
        //初始化交点数组
        List<Double[]> points = new ArrayList<>();
        //初始化第一个图形的线
        Double[][] line1 = new Double[2][2];
        //初始化第二个图形的线
        Double[][] line2 = new Double[2][2];
        for (int i = 0; i < plane1.length - 1; i++) {
            //截取2位坐标确定一条线
            System.arraycopy(plane1,i,line1,0,2);
            for (int j = 0; j < plane2.length - 1; j++) {
                System.arraycopy(plane2,j,line2,0,2);
                Double[] intersection = getIntersection(line1, line2);
                if (intersection != null) {
                    //如果线相交，加入交点数组
                    points.add(intersection);
                }
            }
        }

        return points;
    }

    public static void main(String[] args) {
        Double[][] plane1 = new Double[][]{{114.1479828172934d,39.7412124543823d},{108.7427231256765d,35.4428930291832d},{111.709d,34.7777d},{114.1479828172934d,39.7412124543823d}};
        Double[][] plane2 = new Double[][]{{109.8193123456789d,38.1864123456789d},{115.9937123456789d,36.4743123456789d},{114.0381d,34.8499d},{109.8193123456789d,38.1864123456789d}};

        List<Double[]> list = getPlaneIntersection(plane1, plane2);
        for (Double[] doubles : list) {
            System.out.println(Arrays.toString(doubles));
        }
    }
}

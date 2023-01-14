package com.github.zk.heatmap.draw;

import com.github.zk.heatmap.entity.HeatMapEntity;
import com.github.zk.heatmap.entity.Legend;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 绘制处理实现
 *
 * @author zk
 * @date 2022/11/17 9:59
 */
public class DrawProcessorImpl implements IDrawProcessor {
    /** 像素宽 */
    private BigDecimal width = new BigDecimal("1");
    /** 像素高 */
    private BigDecimal height = new BigDecimal("1");
    /** 图开始经度 */
    private Integer startLon = -180;
    /** 图开始纬度 */
    private Integer startLat = 90;
    /** 颜色范围 */
    private List<Color> colors = defaultColor();
    /** 值范围 */
    private List<Double[]> values = defaultValue();

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public Integer getStartLon() {
        return startLon;
    }

    public Integer getStartLat() {
        return startLat;
    }

    public List<Color> getColors() {
        return colors;
    }

    private DrawProcessorImpl(){}

    @Override
    public List<Legend> drawImg(List<HeatMapEntity> list, String outPath) {
        // 检查像素小数位数
        int widthDecimalPlace = getNumberOfDecimalPlace(width);
        int heightDecimalPlace = getNumberOfDecimalPlace(height);
        int decimalPlace = Math.max(widthDecimalPlace, heightDecimalPlace);
        if (decimalPlace > 2) {
            throw new RuntimeException("分辨率过大，最多支持两位小数像素");
        }
        // 画板放大倍数
        int magnification = (int) Math.pow(10, decimalPlace);
        //初始化图片缓冲区
        BufferedImage bi = new BufferedImage(360 * magnification, 180 * magnification, BufferedImage.TYPE_INT_ARGB);
        //初始化画板
        Graphics2D graphics = bi.createGraphics();

        //初始化数值范围
        List<Double[]> initValue = values;

        //初始化颜色范围
        List<Color> initColor = colors;
        //为每一个点查找颜色值
        for (HeatMapEntity entity : list) {
            for (int i = 0; i < initValue.size(); i++) {
                double start = initValue.get(i)[0];
                double end = initValue.get(i)[1];
                BigDecimal startBigDecimal = BigDecimal.valueOf(start);
                BigDecimal endBigDecimal = BigDecimal.valueOf(end);
                BigDecimal valueBigDecimal = BigDecimal.valueOf(entity.getValue());
                if (valueBigDecimal.compareTo(startBigDecimal) >= 0 &&
                        valueBigDecimal.compareTo(endBigDecimal) < 0) {
                    graphics.setColor(initColor.get(i));
                    double lat = entity.getLat();
                    double lon = entity.getLon();
                    //纬度90转换为0
                    lat -= 90;
                    lat = Math.abs(lat);
                    if (lat < 0) {
                        lat += 180;
                    }
                    //经度-180转换为0·
                    lon += 180;
                    if (lon < 0) {
                        lon += 360;
                    }
                    graphics.fillRect((int) (lon * magnification), (int) (lat * magnification),
                            width.multiply(new BigDecimal(magnification)).intValue(),
                            height.multiply(new BigDecimal(magnification)).intValue());
                    break;
                }
            }
        }

        try {
            Path path = Paths.get(outPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
            }
            ImageIO.write(bi, "png", path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bi.flush();
        return getLegend();
    }

    @Override
    public List<Legend> drawImgBackground(List<HeatMapEntity> list, String background, String outPath) {
        // 检查像素小数位数
        int widthDecimalPlace = getNumberOfDecimalPlace(width);
        int heightDecimalPlace = getNumberOfDecimalPlace(height);
        int decimalPlace = Math.max(widthDecimalPlace, heightDecimalPlace);
        if (decimalPlace > 1) {
            throw new RuntimeException("分辨率过大，最多支持一位小数像素");
        }
        //初始化图片缓冲区(width:3900 height:1970)
        BufferedImage bi = new BufferedImage(3900, 1970, BufferedImage.TYPE_INT_ARGB);
        //初始化画板
        Graphics2D graphics = bi.createGraphics();

        //初始化数值范围
        List<Double[]> initValue = values;

        //初始化颜色范围
        List<Color> initColor = colors;
        //为每一个点查找颜色值
        for (HeatMapEntity entity : list) {
            for (int i = 0; i < initValue.size(); i++) {
                double start = initValue.get(i)[0];
                double end = initValue.get(i)[1];
                BigDecimal startBigDecimal = BigDecimal.valueOf(start);
                BigDecimal endBigDecimal = BigDecimal.valueOf(end);
                BigDecimal valueBigDecimal = BigDecimal.valueOf(entity.getValue());
                if (valueBigDecimal.compareTo(startBigDecimal) >= 0 &&
                        valueBigDecimal.compareTo(endBigDecimal) < 0) {
                    graphics.setColor(initColor.get(i));
                    int lat = (int) entity.getLat();
                    int lon = (int) entity.getLon();
                    //纬度90转换为0
                    lat -= 90;
                    lat = Math.abs(lat);
                    //偏移startLat后，纬度坐标
                    lat -= 90 - startLat;
                    if (lat < 0) {
                        lat += 180;
                    }
                    //经度-180转换为0·
                    lon += 180;
                    //偏移startLon后，经度坐标
                    lon += (-180 - startLon);
                    if (lon < 0) {
                        lon += 360;
                    }
                    graphics.fillRect((lon * 10) + 145, (lat * 10) + 80,
                            width.multiply(new BigDecimal("10")).intValue(),
                            height.multiply(new BigDecimal("10")).intValue());
                    break;
                }
            }
        }
        //加载地图
        if (background != null && !"".equals(background)) {
            ImageIcon ii = new ImageIcon(background);
            //绘制地图
            graphics.drawImage(ii.getImage(), 0, 0, 3900, 1970, null);
        }

        try {
            Path path = Paths.get(outPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
            }
            ImageIO.write(bi, "png", path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        bi.flush();
        return getLegend();
    }

    public static class DrawProcessorBuilder {
        private DrawProcessorImpl drawProcessor = new DrawProcessorImpl();

        public DrawProcessorBuilder pixel(BigDecimal width, BigDecimal height) {
            drawProcessor.width = width;
            drawProcessor.height = height;
            return this;
        }

        public DrawProcessorBuilder position(Integer startLon, Integer startLat) {
            drawProcessor.startLat = startLat;
            drawProcessor.startLon = startLon;
            return this;
        }

        public DrawProcessorBuilder legend(List<Color> colors, List<Double[]> values) {
            drawProcessor.colors = colors;
            drawProcessor.values = values;
            return this;
        }
        public DrawProcessorImpl build() {
            return this.drawProcessor;
        }
    }

    /**
     * 默认值范围
     *
     * @return 值范围集合
     */
    private static List<Double[]> defaultValue() {
        List<Double[]> list = new ArrayList<>(9);
        list.add(new Double[]{0d, 1d});
        list.add(new Double[]{1d, 2d});
        list.add(new Double[]{2d, 3d});
        list.add(new Double[]{3d, 4d});
        list.add(new Double[]{4d, 5d});
        list.add(new Double[]{5d, 6d});
        list.add(new Double[]{6d, 7d});
        list.add(new Double[]{7d, 8d});
        list.add(new Double[]{8d, 20d});
        return list;
    }

    /**
     * 初始化颜色范围
     *
     * @return 颜色范围集合
     */
    private static List<Color> defaultColor() {
        List<Color> list = new ArrayList<>(9);
        list.add(new Color(0, 0, 220, 255));
        list.add(new Color(40, 40, 255, 255));
        list.add(new Color(6, 79, 249, 255));
        list.add(new Color(0, 191, 255, 255));
        list.add(new Color(0, 255, 255, 255));
        list.add(new Color(0, 255, 127, 255));
        list.add(new Color(173, 255, 47, 255));
        list.add(new Color(255, 255, 0, 255));
        list.add(new Color(255, 215, 0, 255));
        return list;
    }

    /**
     * 获取数字小数位数
     *
     * @param decimal 数字
     * @return 小数位数
     */
    private int getNumberOfDecimalPlace(BigDecimal decimal) {
        int scale = decimal.stripTrailingZeros().scale();
        return Math.max(scale, 0);
    }

    /**
     * 获取图例
     *
     * @return 图例列表
     */
    private List<Legend> getLegend() {
        List<Legend> legends = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Legend legend = new Legend();
            legend.setValue(values.get(i));
            Color color = colors.get(i);
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            int alpha = color.getAlpha();
            legend.setRed(red);
            legend.setGreen(green);
            legend.setBlue(blue);
            legend.setAlpha(alpha);
            legends.add(legend);
        }
        return legends;
    }

    @Override
    public List<HeatMapEntity> interpolation(List<HeatMapEntity> sourceList, int width, int height) {
        int startLon = -180;
        int startLat = 90;
        return insertedValue(sourceList, width, height, startLon, startLat);
    }

    /**
     * 插值
     * 按经度进行插值
     *
     * @param list     待插值数据
     * @param startLon 开始经度
     * @param startLat 开始纬度
     * @return 插值后的数据
     */
    private List<HeatMapEntity> insertedValue(List<HeatMapEntity> list, int width, int height, int startLon, int startLat) {

        List<HeatMapEntity> allList = new ArrayList<>();

        for (int i = 0; i < 360 / 5 * 180 / 5; i++) {
            // 右下点
            int rightLon = startLon + width;
            int rightLat = startLat - height;
            // 4 个点进行插值
            List<int[]> points = new ArrayList<>(4);
            points.add(new int[]{startLon, startLat});
            points.add(new int[]{rightLon, startLat});
            points.add(new int[]{startLon, rightLat});
            points.add(new int[]{rightLon, rightLat});
            // 查找数据
            List<HeatMapEntity> collect = list.stream().filter(heatMapEntity -> {
                for (int[] point : points) {
                    if (point[0] == (int) heatMapEntity.getLon() &&
                            point[1] == (int) heatMapEntity.getLat()) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            /*if (collect.size() < 4) {
                // 下一个矩阵起始点
                startLon += width;
                // 如果最后一个点经度为180，需要换行插值
                if (startLon >= 180) {
                    startLon = -180;
                    startLat -= height;
                }
                continue;
            }*/
            List<HeatMapEntity> insertList = insertList(collect, points, width, height);
            // 四角点值插入
            insertList.addAll(collect);
            // 去掉重复点
            allList.removeAll(insertList);
            allList.addAll(insertList);

            // 下一个矩阵起始点
            startLon += width;
            // 如果最后一个点经度为180，需要换行插值
            if (startLon >= 180) {
                startLon = -180;
                startLat -= height;
            }
        }

        return allList;
    }

    /**
     * 对矩阵进行插值
     *
     * @param collect 待插值集合
     * @param points  四角点集合
     * @param width   像素宽
     * @param height  像素高
     * @return 插值后集合
     */
    private List<HeatMapEntity> insertList(List<HeatMapEntity> collect, List<int[]> points,
                                                  int width, int height) {
        double insertInterval = 1;
        List<HeatMapEntity> insertedList = new ArrayList<>();
        // 第一行和最后一行先插值
        // 第一行首尾值
        double[] line1Value = new double[2];
        // 最后一行首尾值
        double[] line2Value = new double[2];
        List<double[]> lineValue = new ArrayList<>();
        lineValue.add(line1Value);
        lineValue.add(line2Value);

        for (int i = 0; i < points.size(); i++) {
            for (HeatMapEntity heatMapEntity : collect) {
                if (heatMapEntity.getLon() == points.get(i)[0] &&
                        heatMapEntity.getLat() == points.get(i)[1]) {
                    double value = heatMapEntity.getValue();
                    if (i < 2) {
                        line1Value[i] = value;
                    } else {
                        line2Value[i - 2] = value;
                    }
                }
            }
        }

        /**
         * 开始插值第一行和最后一行
         */
        // =============================
        // 将四角点位值，赋值给新的两行数据
        double[] insertedLine1Value = new double[(int) (width / insertInterval) + 1];
        insertedLine1Value[0] = line1Value[0];
        insertedLine1Value[insertedLine1Value.length - 1] = line1Value[1];
        double[] insertedLine2Value = new double[(int) (width / insertInterval) + 1];
        insertedLine2Value[0] = line2Value[0];
        insertedLine2Value[insertedLine2Value.length - 1] = line2Value[1];
        //将数组放入集合，方便后续循环通用
        List<double[]> insertedLineValue = new ArrayList<>();
        insertedLineValue.add(insertedLine1Value);
        insertedLineValue.add(insertedLine2Value);

        for (int i = 0; i < lineValue.size(); i++) {
            double[] value = lineValue.get(i);
            // 计算应该递增的平均值
            double aveValue = (value[1] - value[0]) / (width / insertInterval);
            aveValue = new BigDecimal(aveValue)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN)
                    .doubleValue();
            // 两行数据插值
            for (int j = 0; j < (width / insertInterval) - 1; j++) {
                HeatMapEntity heatMapEntity = new HeatMapEntity();
                // 基于基准点第一个点（左上）进行增加
                double lon = points.get(0)[0];
                double lat = points.get(0)[1];
                // 按行插值，根据行数计算经度
                lon += (j + 1) * insertInterval;
                lat -= i * height;
                double insertedValue = value[0] + aveValue;
                //插值后的两行数据
                insertedLineValue.get(i)[j + 1] = insertedValue;

                heatMapEntity.setLon(lon);
                heatMapEntity.setLat(lat);
                heatMapEntity.setValue(insertedValue);
                insertedList.add(heatMapEntity);
            }
        }
        // =============================

        /**
         * 开始插值列
         */
        for (int i = 0; i < (width / insertInterval) + 1; i++) {
            double aveValue = (insertedLine2Value[i] - insertedLine1Value[i]) / (height / insertInterval);
            aveValue = new BigDecimal(aveValue)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN)
                    .doubleValue();
            for (int j = 0; j < (height / insertInterval) - 1; j++) {
                HeatMapEntity heatMapEntity = new HeatMapEntity();
                // 基于基准点第一个点进行增加
                double lon = points.get(0)[0];
                double lat = points.get(0)[1];
                // 按列插值，根据列数，计算经纬度
                lon += i * insertInterval;
                lat -= (j + 1) * insertInterval;
                double insertedValue = insertedLine1Value[i] + aveValue;

                heatMapEntity.setLon(lon);
                heatMapEntity.setLat(lat);
                heatMapEntity.setValue(insertedValue);
                insertedList.add(heatMapEntity);
            }
        }
        return insertedList;
    }

}

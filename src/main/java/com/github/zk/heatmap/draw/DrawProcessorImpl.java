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
import javax.imageio.ImageIO;

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
    public boolean drawImgBackground(List<HeatMapEntity> list, String background, String outPath) {
        return false;
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

}

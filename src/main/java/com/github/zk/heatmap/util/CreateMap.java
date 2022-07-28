package com.github.zk.heatmap.util;

import com.github.zk.heatmap.entity.HeatMapEntity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zk
 * @version v1.0.0
 * @date 2019/8/2 9:28
 * @since v1.0.0
 */
public class CreateMap {

    /**
     * 使用读取文件方式示例
     *
     * @param args
     * @since v1.0.0
     */
    public static void main(String[] args) {
//        dop();
        dop1();
    }

    public static void dop() {
        List<HeatMapEntity> list = new ArrayList<>();
        Path path = Paths.get("C:\\Users\\zhaokai\\Desktop\\rr_bds_pos_178_09.22r");
        try {
            Files.lines(path).filter(line -> {
                boolean flag = false;
                if (!line.startsWith("#")) {
                    flag = true;
                }
                return flag;
            }).forEach(line -> {
                HeatMapEntity hme = new HeatMapEntity();
                String[] lines = line.split("\\s+");
                hme.setLat(Double.parseDouble(lines[3]));
                hme.setLon(Double.parseDouble(lines[2]));
                hme.setValue("--".equals(lines[6]) ? 999999d : Double.parseDouble(lines[6]));
                list.add(hme);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<double[]> list0 = new ArrayList<>(9);
        list0.add(new double[]{0d, 2d});
        list0.add(new double[]{2d, 4d});
        list0.add(new double[]{4d, 6d});
        list0.add(new double[]{6d, 8d});
        list0.add(new double[]{8d, 10d});

        List<Color> list1 = new ArrayList<>();
        list1.add(new Color(0, 0, 207, 255));
        list1.add(new Color(0, 223, 255, 255));
        list1.add(new Color(0, 223, 255, 255));
        list1.add(new Color(255, 207, 0, 255));
        list1.add(new Color(255, 143, 0, 255));
        boolean b = createHeatMapByBackground(list, "D:\\work\\workspace\\github\\heat-map\\data\\dt-release.png", "C:\\Users\\zhaokai\\Desktop\\rr_bds_pos_178_09.png",
                4.0d, 2.0d, list0, list1, -180, 90);
        if (b) {
            System.out.println("成功");
        }
    }

    public static void dop1() {
        List<HeatMapEntity> list = new ArrayList<>();
        Path path = Paths.get("D:\\work\\workspace\\github\\heat-map\\data\\dop.DOP");
        try {
            Files.lines(path).filter(line -> {
                boolean flag = false;
                if (!line.startsWith("#")) {
                    flag = true;
                }
                return flag;
            }).forEach(line -> {
                HeatMapEntity hme = new HeatMapEntity();
                String[] lines = line.split("\\s+");
//                if (Double.parseDouble(lines[0]) % 1 == 0 && Double.parseDouble(lines[1]) % 1 == 0) {
//                    hme.setLat(Double.parseDouble(lines[0]));
//                    hme.setLon(Double.parseDouble(lines[1]));
//                    hme.setValue("--".equals(lines[3]) ? 999999d : Double.parseDouble(lines[3]));
//                    list.add(hme);
//                }
                hme.setLat(Double.parseDouble(lines[1]));
                hme.setLon(Double.parseDouble(lines[2]));
                hme.setValue("--".equals(lines[4]) ? 999999d : Double.parseDouble(lines[4]));
                list.add(hme);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<double[]> list0 = new ArrayList<>(9);
        list0.add(new double[]{0d, 1d});
        list0.add(new double[]{1d, 2d});
        list0.add(new double[]{2d, 3d});
        list0.add(new double[]{3d, 4d});
        list0.add(new double[]{4d, 5d});
        list0.add(new double[]{5d, 6d});
        list0.add(new double[]{6d, 7d});
        list0.add(new double[]{7d, 8d});
        list0.add(new double[]{8d, 9d});

        List<Color> list1 = new ArrayList<>();
        list1.add(new Color(134, 153, 9, 255));
        list1.add(new Color(134, 153, 9, 255));
        list1.add(new Color(44, 207, 224, 255));
        list1.add(new Color(189, 9, 207, 255));
        list1.add(new Color(255, 255, 0, 255));
        list1.add(new Color(4, 3, 193, 255));
        list1.add(new Color(224, 16, 37, 255));
        list1.add(new Color(4, 99, 36, 255));
        list1.add(new Color(255, 255, 255, 255));
        boolean b = createHeatMapByBackground(list, "D:\\work\\workspace\\github\\heat-map\\data\\dt-release.png", "C:\\Users\\zhaokai\\Desktop\\0.png",
                 list0, list1, -180, 90);
        if (b) {
            System.out.println("成功");
        }
    }

    public static void codg() {
        List<HeatMapEntity> list = new ArrayList<>();
        Path path = Paths.get("C:\\Users\\zhaokai\\Desktop\\t.txt");
        try {
            Iterator<String> iterator = Files.lines(path).iterator();
            while(iterator.hasNext()) {
                String firstLine = iterator.next();
                //纬度
                String lat = firstLine.substring(0, 8).trim();
                BigDecimal latBigDecimal = new BigDecimal(lat);
                //开始经度
                String startLon = firstLine.substring(8, 12);
                BigDecimal startLonBigDecimal = new BigDecimal(startLon);
                //结束经度
                String endLon = firstLine.substring(15, 20);
                BigDecimal endLonBigDecimal = new BigDecimal(endLon);
                //步长
                String step = firstLine.substring(23, 26);
                BigDecimal stepBigDecimal = new BigDecimal(step);

                List<String> value = new ArrayList<>();
                Collections.addAll(value, iterator.next().split("\\s+"));
                Collections.addAll(value, iterator.next().split("\\s+"));
                Collections.addAll(value, iterator.next().split("\\s+"));
                Collections.addAll(value, iterator.next().split("\\s+"));
                Collections.addAll(value, iterator.next().split("\\s+"));
                List<String> collect = value.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
                //等差数列求项数
                BigDecimal forLength = new BigDecimal("1")
                        .add(endLonBigDecimal
                                .subtract(startLonBigDecimal)
                                .divide(stepBigDecimal, BigDecimal.ROUND_UP));
                for (int i = 0; i < forLength.intValue(); i++) {
                    HeatMapEntity heatMapEntity = new HeatMapEntity();
                    heatMapEntity.setLat(latBigDecimal.doubleValue());
                    heatMapEntity.setLon(startLonBigDecimal.doubleValue());
                    heatMapEntity.setValue(Double.parseDouble(collect.get(i)));
                    startLonBigDecimal = startLonBigDecimal.add(stepBigDecimal);
                    list.add(heatMapEntity);
                }
            }
            boolean b = createHeatMap(list, "C:\\Users\\zhaokai\\Desktop\\1.png", 5.0, 2.5);
            if (b) {
                System.out.println("成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成默认位置默认颜色热力图
     *
     * @param list    元数据
     * @param outPath 输出路径
     * @return
     */
    public static boolean createHeatMap(List<HeatMapEntity> list, String outPath) {
        return createHeatMapByBackground(list, null, outPath, null, null,
                -180, 90);
    }

    /**
     * 使用默认范围和默认颜色、默认开始位置生成带背景地图的热力图
     *
     * @param list           元数据
     * @param backgroundPath 背景地图路径
     * @param outPath        输出路径
     * @return
     */
    public static boolean createHeatMapByBackground(List<HeatMapEntity> list, String backgroundPath,
                                                    String outPath) {
        return createHeatMapByBackground(list, backgroundPath, outPath,
                null, null, -180, 90);
    }

    /**
     * 使用默认颜色生成带背景地图的热力图
     *
     * @param list           元数据
     * @param backgroundPath 背景地图路径
     * @param outPath        输出路径
     * @param startLon       开始经度
     * @param startLat       开始纬度
     * @return
     */
    public static boolean createHeatMapByBackground(List<HeatMapEntity> list, String backgroundPath,
                                                    String outPath, int startLon, int startLat) {
        return createHeatMapByBackground(list, backgroundPath, outPath,
                null, null, startLon, startLat);
    }

    /**
     * 使用默认1x1像素生成带背景的热力图
     *
     * @param list           元数据
     * @param backgroundPath 背景地图路径
     * @param outPath        输出路径
     * @param values         数值范围
     * @param colors         颜色范围
     * @param startLon       开始经度
     * @param startLat       开始纬度
     * @return
     */
    public static boolean createHeatMapByBackground(List<HeatMapEntity> list, String backgroundPath, String outPath,
                                                    List<double[]> values, List<Color> colors,
                                                    int startLon, int startLat) {
        //初始化图片缓冲区(width:3900 height:1970)
        BufferedImage bi = new BufferedImage(3900, 1970, BufferedImage.TYPE_INT_ARGB);
        //初始化画板
        Graphics2D graphics = bi.createGraphics();

        //初始化数值范围
        List<double[]> initValue = (values == null ? defaultValue() : values);

        //初始化颜色范围
        List<Color> initColor = (colors == null ? defaultColor() : colors);
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
                    graphics.fillRect((lon * 10) + 145, (lat * 10) + 80, 10, 10);
                    break;
                }
            }
        }
        //加载地图
        if (backgroundPath != null && !"".equals(backgroundPath)) {
            ImageIcon ii = new ImageIcon(backgroundPath);
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
            return false;
        }
        bi.flush();
        return true;
    }

    /**
     * 生成默认色值，无背景，可变像素热力图
     *
     * @param list    数据
     * @param outPath 输出位置
     * @param width   经度步长
     * @param height  纬度步长
     * @return
     */
    public static boolean createHeatMap(List<HeatMapEntity> list, String outPath, Double width, Double height) {
        //初始化图片缓冲区
        BufferedImage bi = new BufferedImage(3600, 1800, BufferedImage.TYPE_INT_ARGB);
        //初始化画板
        Graphics2D graphics = bi.createGraphics();

        //初始化数值范围
        List<double[]> initValue = defaultValue();

        //初始化颜色范围
        List<Color> initColor = defaultColor();
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
                    graphics.fillRect((int) (lon * 10), (int) (lat * 10),
                            (int) (10 * width), (int) (10 * height));
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
            return false;
        }
        bi.flush();
        return true;
    }

    /**
     * 生成自定义数值和色值，无背景，可变像素热力图
     *
     * @param list    数据
     * @param outPath 输出位置
     * @param width   经度步长
     * @param height  纬度步长
     * @param values  数值范围
     * @return
     */
    public static boolean createHeatMap(List<HeatMapEntity> list, String outPath, Double width, Double height,
                                        List<double[]> values, List<Color> colors) {
        //初始化图片缓冲区
        BufferedImage bi = new BufferedImage(3600, 1800, BufferedImage.TYPE_INT_ARGB);
        //初始化画板
        Graphics2D graphics = bi.createGraphics();

        //初始化数值范围
        List<double[]> initValue = (values == null ? defaultValue() : values);

        //初始化颜色范围
        List<Color> initColor = (colors == null ? defaultColor() : colors);
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
                    graphics.fillRect((int) (lon * 10), (int) (lat * 10),
                            (int) (10 * width), (int) (10 * height));
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
            return false;
        }
        bi.flush();
        return true;
    }

    /**
     * 生成自定义位置和自定义颜色带背景的热力图
     *
     * @param list           元数据
     * @param backgroundPath 背景图
     * @param outPath        输出路径
     * @param startLon       经度开始值（带符号）
     * @param startLat       纬度开始值（带符号）
     * @return
     * @since v1.0.0
     */
    public static boolean createHeatMapByBackground(List<HeatMapEntity> list, String backgroundPath, String outPath,
                                       Double width, Double height,
                                       List<double[]> values, List<Color> colors, int startLon, int startLat) {
        //初始化图片缓冲区(width:3900 height:1970)
        BufferedImage bi = new BufferedImage(3900, 1970, BufferedImage.TYPE_INT_ARGB);
        //初始化画板
        Graphics2D graphics = bi.createGraphics();

        //初始化数值范围
        List<double[]> initValue = (values == null ? defaultValue() : values);

        //初始化颜色范围
        List<Color> initColor = (colors == null ? defaultColor() : colors);
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
                            (int) (10 * width), (int) (10 * height));
                    break;
                }
            }
        }
        //加载地图
        if (backgroundPath != null && !"".equals(backgroundPath)) {
            ImageIcon ii = new ImageIcon(backgroundPath);
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
            return false;
        }
        bi.flush();
        return true;
    }

    /**
     * 生成无背景，自定义像素、数值和色值、范围热力图
     * @param list 数据集合
     * @param outPath 输出图片地址
     * @param width 像素宽
     * @param height 像素高
     * @param values 数值
     * @param colors 色值
     * @param startLon 图片开始精度
     * @param startLat 图片开始纬度
     * @return
     */
    public static boolean createHeatMap(List<HeatMapEntity> list, String outPath, Double width, Double height,
                                       List<double[]> values, List<Color> colors,  int startLon, int startLat) {
        //初始化图片缓冲区
        BufferedImage bi = new BufferedImage(3600, 1800, BufferedImage.TYPE_INT_ARGB);
        //初始化画板
        Graphics2D graphics = bi.createGraphics();

        //初始化数值范围
        List<double[]> initValue = (values == null ? defaultValue() : values);

        //初始化颜色范围
        List<Color> initColor = (colors == null ? defaultColor() : colors);
        //为每一个点查找颜色值
        for (HeatMapEntity entity : list) {
            for (int i = 0; i < initValue.size(); i++) {
                double start = initValue.get(i)[0];
                double end = initValue.get(i)[1];
                int startBigDecimal = BigDecimal.valueOf(start).intValue();
                int endBigDecimal = BigDecimal.valueOf(end).intValue();
                int valueBigDecimal = BigDecimal.valueOf(entity.getValue()).intValue();
                if (valueBigDecimal - startBigDecimal >= 0 &&
                        valueBigDecimal - endBigDecimal < 0) {
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
                    graphics.fillRect((int) (lon * 10), (int) (lat * 10),
                            (int) (10 * width), (int) (10 * height));
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
            return false;
        }
        bi.flush();
        return true;
    }

    /**
     * 初始化数值范围
     *
     * @return
     * @since v2.0.0
     */
    private static List<double[]> defaultValue() {
        List<double[]> list = new ArrayList<>(9);
        list.add(new double[]{0d, 1d});
        list.add(new double[]{1d, 2d});
        list.add(new double[]{2d, 3d});
        list.add(new double[]{3d, 4d});
        list.add(new double[]{4d, 5d});
        list.add(new double[]{5d, 6d});
        list.add(new double[]{6d, 7d});
        list.add(new double[]{7d, 8d});
        list.add(new double[]{8d, 20d});
        return list;
    }

    /**
     * 初始化颜色范围
     *
     * @return
     * @since v2.0.0
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
}

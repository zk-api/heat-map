package com.github.zk.heatmap.util;

import com.github.zk.heatmap.entity.HeatMapEntity;
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
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author zk
 * @version v1.0.0
 * @date 2019/8/2 9:28
 * @since v1.0.0
 * @see com.github.zk.heatmap.draw.IDrawProcessor
 */
@Deprecated
public class CreateMap {

    /**
     * 使用读取文件方式示例
     *
     * @param args
     * @since v1.0.0
     */
    public static void main(String[] args) {
//        dop();
//        dop1();
        dop3();

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
//        List<double[]> list0 = new ArrayList<>(9);
//        list0.add(new double[]{0d, 2d});
//        list0.add(new double[]{2d, 4d});
//        list0.add(new double[]{4d, 6d});
//        list0.add(new double[]{6d, 8d});
//        list0.add(new double[]{8d, 10d});
//
//        List<Color> list1 = new ArrayList<>();
//        list1.add(new Color(0, 0, 207, 255));
//        list1.add(new Color(0, 153, 255, 255));
//        list1.add(new Color(104, 200, 245, 255));
//        list1.add(new Color(255, 207, 0, 255));
//        list1.add(new Color(255, 143, 0, 255));
        List<double[]> list0 = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            double[] val = new double[2];
            val[0] = i * 0.01;
            val[1] = (i + 1) * 0.01;
            list0.add(val);
        }

        List<Color> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int r = 0;
            for (int j = 0; j < 10; j++) {
                int g = 255 - j * 5;
                for (int k = 0; k < 10; k++) {
                    int b = 255 - k * 5;
                    Color color = new Color(r, g, b, 255);
                    list1.add(color);
                }
            }
        }

        List<HeatMapEntity> insertedList = interpolation(list, 4, 2);
        boolean b = createHeatMapByBackground(insertedList, "D:\\work\\workspace\\github\\heat-map\\data\\dt-release.png", "C:\\Users\\zhaokai\\Desktop\\rr_bds_pos_178_09-1.png",
                0.5d, 0.5d, list0, list1, -180, 90);

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
                hme.setValue("--".equals(lines[4]) ? 999.99d : Double.parseDouble(lines[4]));
                list.add(hme);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<double[]> list0 = new ArrayList<>(9);
        list0.add(new double[]{0d, 1d});
        list0.add(new double[]{1d, 2d});
        list0.add(new double[]{2d, 2.25d});
        list0.add(new double[]{2.25d, 2.5d});
        list0.add(new double[]{2.5d, 2.75d});
        list0.add(new double[]{2.75d, 3d});
        list0.add(new double[]{3d, 3.25d});
        list0.add(new double[]{3.25d, 3.5d});
        list0.add(new double[]{3.5d, 3.75d});
        list0.add(new double[]{3.75d, 4d});
        list0.add(new double[]{4d, 4.25d});
        list0.add(new double[]{4.25d, 4.5d});
        list0.add(new double[]{4.5d, 4.75d});
        list0.add(new double[]{4.75d, 5d});

        List<Color> list1 = new ArrayList<>();
        list1.add(new Color(227, 11, 11, 255));
        list1.add(new Color(227, 79, 11, 255));
        list1.add(new Color(219, 94, 57, 255));
        list1.add(new Color(213, 138, 104, 255));
        list1.add(new Color(199, 143, 105, 255));
        list1.add(new Color(222, 194, 181, 255));
        list1.add(new Color(227, 155, 11, 255));
        list1.add(new Color(222, 159, 83, 255));
        list1.add(new Color(222, 192, 139, 255));
        list1.add(new Color(227, 198, 11, 255));
        list1.add(new Color(217, 198, 106, 255));
        list1.add(new Color(226, 206, 148, 255));
        list1.add(new Color(135, 213, 21, 255));
        list1.add(new Color(168, 219, 111, 255));

//        List<HeatMapEntity> insertedList = interpolation(list, 1, 1);
        boolean b = createHeatMap(list, "C:\\Users\\zhaokai\\Desktop\\0.png",
                1d, 1d, null, null);
        if (b) {
            System.out.println("成功");
        }
    }

    public static void dop3() {
        List<HeatMapEntity> list = new ArrayList<>();
        Path path = Paths.get("D:\\WeChat\\WeChat Files\\Z506683059\\FileStorage\\File\\2022-09\\L31__20220820000000.RP-0(1).01");
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
                hme.setValue("--".equals(lines[10]) ? 999.99d : Double.parseDouble(lines[10]));
                list.add(hme);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<double[]> list0 = new ArrayList<>(9);
        list0.add(new double[]{-110d, -107d});
        list0.add(new double[]{-107d, -104d});
        list0.add(new double[]{-104d, -101d});
        list0.add(new double[]{-101d, -97d});

        List<Color> list1 = new ArrayList<>();
        list1.add(new Color(227, 11, 11, 255));
        list1.add(new Color(205, 129, 94, 255));
        list1.add(new Color(206, 168, 111, 255));
        list1.add(new Color(206, 193, 111, 255));

        boolean b = createHeatMapTwoDecimal(list, "C:\\Users\\zhaokai\\Desktop\\001.png",
                0.01d, 0.01d, list0, list1);
        if (b) {
            System.out.println("成功");
        }
    }

    public static void codg() {
        List<HeatMapEntity> list = new ArrayList<>();
        Path path = Paths.get("C:\\Users\\zhaokai\\Desktop\\t.txt");
        try {
            Iterator<String> iterator = Files.lines(path).iterator();
            while (iterator.hasNext()) {
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

    public static boolean createHeatMapTwoDecimal(List<HeatMapEntity> list, String outPath, Double width, Double height,
                                        List<double[]> values, List<Color> colors) {
        //初始化图片缓冲区
        BufferedImage bi = new BufferedImage(36000, 18000, BufferedImage.TYPE_INT_ARGB);
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
                    graphics.fillRect((int) (lon * 100), (int) (lat * 100),
                            (int) (100 * width), (int) (100 * height));
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
                    double lat = entity.getLat();
                    double lon = entity.getLon();
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
                    graphics.fillRect((int) (lon * 10) + 145, (int) (lat * 10) + 80,
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

    public static boolean createHeatMapByBackground1(List<HeatMapEntity> list, String backgroundPath, String outPath,
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
     *
     * @param list     数据集合
     * @param outPath  输出图片地址
     * @param width    像素宽
     * @param height   像素高
     * @param values   数值
     * @param colors   色值
     * @param startLon 图片开始精度
     * @param startLat 图片开始纬度
     * @return
     */
    public static boolean createHeatMap(List<HeatMapEntity> list, String outPath, Double width, Double height,
                                        List<double[]> values, List<Color> colors, int startLon, int startLat) {
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

    private static List<HeatMapEntity> interpolation(List<HeatMapEntity> list, int width, int height) {
        int startLon = -180;
        int startLat = 90;
        return insertedValue(list, width, height, startLon, startLat);
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
    private static List<HeatMapEntity> insertedValue(List<HeatMapEntity> list, int width, int height, int startLon, int startLat) {

        List<HeatMapEntity> allList = new ArrayList<>();

        for (int i = 0; i < 360 * 180; i++) {
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

    private static List<HeatMapEntity> insertedValue1(List<HeatMapEntity> list, int width, int height, int startLon, int startLat) {
        // 4 个点进行插值
        List<int[]> points = new ArrayList<>(4);
        // 下一次起始点
        int newStartLon = startLon + width;
        int newStartLat = startLat;
        // 右下点
        int rightLon = startLon + width;
        int rightLat = startLat - height;

        // 如果最后一个点经度为180，需要换行插值
        if (newStartLon >= 180) {
            newStartLon = -180;
            newStartLat = startLat - height;
        }

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

        List<HeatMapEntity> insertList = insertList(collect, points, width, height);
        // 四角点值插入
        insertList.addAll(collect);
        // 最后一个矩阵，不需要再查找下一个矩阵
        if (!(rightLon == 180 && rightLat == -90)) {
            List<HeatMapEntity> nextInsertList = insertedValue(list, width, height, newStartLon, newStartLat);
            insertList.removeAll(nextInsertList);
            insertList.addAll(nextInsertList);
        }
        return insertList;
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
    private static List<HeatMapEntity> insertList(List<HeatMapEntity> collect, List<int[]> points,
                                                  int width, int height) {
        double insertInterval = 0.5;
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

    /**
     * 对矩阵进行插值
     *
     * @param collect 待插值集合
     * @param points  四角点集合
     * @param width   像素宽
     * @param height  像素高
     * @return 插值后集合
     */
    private static List<HeatMapEntity> insertList1(List<HeatMapEntity> collect, List<int[]> points,
                                                   int width, int height) {
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
        double[] insertedLine1Value = new double[width + 1];
        insertedLine1Value[0] = line1Value[0];
        insertedLine1Value[insertedLine1Value.length - 1] = line1Value[1];
        double[] insertedLine2Value = new double[width + 1];
        insertedLine2Value[0] = line2Value[0];
        insertedLine2Value[insertedLine2Value.length - 1] = line2Value[1];
        //将数组放入集合，方便后续循环通用
        List<double[]> insertedLineValue = new ArrayList<>();
        insertedLineValue.add(insertedLine1Value);
        insertedLineValue.add(insertedLine2Value);

        for (int i = 0; i < lineValue.size(); i++) {
            double[] value = lineValue.get(i);
            // 计算应该递增的平均值
            double aveValue = (value[1] - value[0]) / width;
            aveValue = new BigDecimal(aveValue)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN)
                    .doubleValue();
            // 两行数据插值
            for (int j = 0; j < width - 1; j++) {

                HeatMapEntity heatMapEntity = new HeatMapEntity();
                // 基于基准点第一个点（左上）进行增加
                int lon = points.get(0)[0];
                int lat = points.get(0)[1];
                // 按行插值，根据行数计算经度
                lon += j + 1;
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
        // 高度小于2时， 证明不需要插值
        if (height < 2) {
            return insertedList;
        }

        /**
         * 开始插值列
         */
        for (int i = 0; i < width + 1; i++) {
            double aveValue = (insertedLine2Value[i] - insertedLine1Value[i]) / height;
            aveValue = new BigDecimal(aveValue)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN)
                    .doubleValue();
            for (int j = 0; j < height - 1; j++) {
                HeatMapEntity heatMapEntity = new HeatMapEntity();
                // 基于基准点第一个点进行增加
                int lon = points.get(0)[0];
                int lat = points.get(0)[1];
                // 按列插值，根据列数，计算经纬度
                lon += i;
                lat -= j + 1;
                double insertedValue = insertedLine1Value[i] + aveValue;

                heatMapEntity.setLon(lon);
                heatMapEntity.setLat(lat);
                heatMapEntity.setValue(insertedValue);
                insertedList.add(heatMapEntity);
            }
        }
        return insertedList;
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

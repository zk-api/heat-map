package com.github.zk.heatmap.util;

import com.github.zk.heatmap.entity.HeatMapEntity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zk
 * @date 2019/8/2 9:28
 * @since v1.0.0
 * @version v1.0.0
 */
public class CreateMap {

    /**
     * 初始化数值范围和颜色值对应关系
     */
    private static List<int[]> valueList = initValue();
    private static List<Color> colorList = initColor();

    /**
     * 使用读取文件方式示例
     *
     * @param args
     * @since v1.0.0
     */
    public static void main(String[] args) {
        List<HeatMapEntity> list = new ArrayList<>();
        Path path = Paths.get("E:\\githubWork\\heat-map\\data\\dop.DOP");
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
                if (Double.valueOf(lines[1]) % 1 == 0 && Double.valueOf(lines[2]) % 1 == 0) {
                    hme.setLat(Double.valueOf(lines[1]));
                    hme.setLon(Double.valueOf(lines[2]));
                    hme.setValue(lines[4].equals("--") ? 999999d : Double.valueOf(lines[4]));
                    list.add(hme);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean b = creatHeatMap(list, "E:\\githubWork\\heat-map\\data\\dt-release.png", "E:\\githubWork\\heat-map\\data\\outpic\\2.png", -150, 90);
        if (b) {
            System.out.println("成功");
        }
    }

    public static boolean creatHeatMap(List<HeatMapEntity> list, String outPath) {
        return creatHeatMap(list, null, outPath, -180, 90);
    }

    /**
     * 生成热力图
     *
     * @param list           元数据
     * @param backgroundPath 背景图
     * @param outPath        输出路径
     * @return
     * @since v1.0.0
     */
    public static boolean creatHeatMap(List<HeatMapEntity> list, String backgroundPath, String outPath) {
        return creatHeatMap(list, backgroundPath, outPath, -180, 90);
    }

    /**
     * 根据经纬度位置生成热力图
     *
     * @param list           元数据
     * @param backgroundPath 背景图
     * @param outPath        输出路径
     * @param startLon       经度开始值（带符号）
     * @param startLon       纬度开始值（带符号）
     * @return
     * @since v1.0.0
     */
    public static boolean creatHeatMap(List<HeatMapEntity> list, String backgroundPath, String outPath, int startLon, int startLat) {
        //初始化图片缓冲区(width:3900 height:1970)
        BufferedImage bi = new BufferedImage(3900, 1970, BufferedImage.TYPE_INT_ARGB);
        //初始化画板
        Graphics2D graphics = bi.createGraphics();

        //初始化数值范围
        List<int[]> initValue = initValue();
        //初始化颜色范围
        List<Color> initColor = initColor();
        //为每一个点查找颜色值
        for (HeatMapEntity entity : list) {
            for (int i = 0; i < initValue.size(); i++) {
                int start = initValue.get(i)[0];
                int end = initValue.get(i)[1];
                if (entity.getValue() >= start && entity.getValue() < end) {
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
     * 初始化数值范围
     * @return
     * @since v1.0.0
     */
    private static List<int[]> initValue() {
        List<int[]> list = new ArrayList<>(9);
        list.add(new int[]{0, 1});
        list.add(new int[]{1, 2});
        list.add(new int[]{2, 3});
        list.add(new int[]{3, 4});
        list.add(new int[]{4, 5});
        list.add(new int[]{5, 6});
        list.add(new int[]{6, 7});
        list.add(new int[]{7, 8});
        list.add(new int[]{8, 20});
        return list;
    }

    /**
     * 初始化颜色范围
     * @return
     * @since v1.0.0
     */
    private static List<Color> initColor() {
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

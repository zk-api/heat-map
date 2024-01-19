package com.github.zk.heatmap.example;

import com.github.zk.heatmap.draw.DrawProcessorImpl;
import com.github.zk.heatmap.draw.IDrawProcessor;
import com.github.zk.heatmap.entity.HeatMapEntity;
import com.github.zk.heatmap.entity.Legend;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 绘制示例
 *
 * @author zk
 * @since  2.0
 */
public class DrawExample {
    public static void main(String[] args) {
        System.out.println(defaultBackgroundDraw());
    }

    public static List<HeatMapEntity> buildHeatMapEntities(BigDecimal width, BigDecimal height) {
        if (width.compareTo(new BigDecimal("0")) <= 0 ||
                height.compareTo(new BigDecimal("0")) <= 0) {
            throw new RuntimeException("经纬度间隔必须为正数");
        }
        List<HeatMapEntity> list = new ArrayList<>();
        Path path = Paths.get("D:\\work\\workspace\\github\\heat-map\\data\\dop.DOP");
        try(Stream<String> lines = Files.lines(path)) {
            lines.skip(1).forEach(s -> {
                HeatMapEntity heatMapEntity = new HeatMapEntity();
                String[] colums = s.split("\\s+");
                if ("--".equals(colums[4])) {
                    return;
                }
                BigDecimal latBigDecimal = new BigDecimal(colums[1]).stripTrailingZeros();
                BigDecimal lonBigDecimal = new BigDecimal(colums[2]).stripTrailingZeros();
                if (Math.max(0, latBigDecimal.divide(width).stripTrailingZeros().scale()) > 0 ||
                        Math.max(0, lonBigDecimal.divide(height).stripTrailingZeros().scale()) > 0) {
                    return;
                }
                double lat = latBigDecimal.doubleValue();
                double lon = lonBigDecimal.doubleValue();
                double val = Double.parseDouble(colums[4]);
                heatMapEntity.setLat(lat);
                heatMapEntity.setLon(lon);
                heatMapEntity.setValue(val);
                list.add(heatMapEntity);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 默认设置绘制
     *
     * @return 图例列表
     */
    public static List<Legend> defaultDraw() {
        IDrawProcessor drawProcessor = new DrawProcessorImpl.DrawProcessorBuilder()
                .pixel(new BigDecimal("5"), new BigDecimal("5"))
                .build();
        List<HeatMapEntity> heatMapEntities = buildHeatMapEntities(new BigDecimal("5"),
                new BigDecimal("5"));
        String filePath = "D:\\work\\1.png";
        return drawProcessor.drawImg(heatMapEntities, filePath);
    }

    public static List<Legend> pixelDraw() {
        IDrawProcessor drawProcessor = new DrawProcessorImpl.DrawProcessorBuilder()
                .pixel(new BigDecimal("1"), new BigDecimal("1"))
                .build();
        List<HeatMapEntity> heatMapEntities = buildHeatMapEntities(new BigDecimal("5"),
                new BigDecimal("5"));
        List<HeatMapEntity> interpolation = drawProcessor.interpolation(heatMapEntities, 5, 5);
        String filePath = "D:\\work\\workspace\\github\\heat-map\\data\\outpic\\1-2.png";
        return drawProcessor.drawImg(interpolation, filePath);
    }

    public static List<Legend> defaultBackgroundDraw() {
        IDrawProcessor drawProcessor = new DrawProcessorImpl.DrawProcessorBuilder()
                .pixel(new BigDecimal("1"), new BigDecimal("1"))
                .build();
        List<HeatMapEntity> heatMapEntities = buildHeatMapEntities(new BigDecimal("1"),
                new BigDecimal("1"));
        String filePath = "D:\\work\\1-3.png";
        String backgroundPath = "D:\\work\\workspace\\github\\heat-map\\data\\dt-release.png";
        return drawProcessor.drawImgBackground(heatMapEntities, backgroundPath, filePath);
    }
}

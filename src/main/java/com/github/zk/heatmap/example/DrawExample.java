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

/**
 * 绘制示例
 *
 * @author zk
 * @date 2022/12/14 9:34
 */
public class DrawExample {
    public static void main(String[] args) {
//        defaultDraw();
        System.out.println(pixelDraw());
    }

    public static List<HeatMapEntity> buildHeatMapEntities() {
        List<HeatMapEntity> list = new ArrayList<>();
        Path path = Paths.get("D:\\work\\workspace\\github\\heat-map\\data\\dop.DOP");
        try {
            Files.lines(path).skip(1).forEach(s -> {
                HeatMapEntity heatMapEntity = new HeatMapEntity();
                String[] colums = s.split("\\s+");
                if ("--".equals(colums[4])) {
                    return;
                }
                double lat = Double.parseDouble(colums[1]);
                double lon = Double.parseDouble(colums[2]);
                double val = Double.parseDouble(colums[4]);
                heatMapEntity.setLat(lat);
                heatMapEntity.setLon(lon);
                heatMapEntity.setValue(val);
                list.add(heatMapEntity);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 默认设置绘制
     *
     * @return
     */
    public static List<Legend> defaultDraw() {
        IDrawProcessor drawProcessor = new DrawProcessorImpl.DrawProcessorBuilder().build();
        List<HeatMapEntity> heatMapEntities = buildHeatMapEntities();
        String filePath = "D:\\work\\workspace\\github\\heat-map\\data\\outpic\\1.png";
        return drawProcessor.drawImg(heatMapEntities, filePath);
    }

    public static List<Legend> pixelDraw() {
        IDrawProcessor drawProcessor = new DrawProcessorImpl.DrawProcessorBuilder()
                .pixel(new BigDecimal("5"), new BigDecimal("2.5"))
                .build();
        List<HeatMapEntity> heatMapEntities = buildHeatMapEntities();
        String filePath = "D:\\work\\workspace\\github\\heat-map\\data\\outpic\\1.png";
        return drawProcessor.drawImg(heatMapEntities, filePath);
    }
}

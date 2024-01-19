# heat-map 热力图生成

## 一、接口介绍
### 热力图生成有3个主要方法
#### 1. 绘制无背景热力图
`List<Legend> drawImg(List<HeatMapEntity> list, String outPath);`

参数说明:

| 序号 | 参数      | 描述       |
|----|---------|----------|
| 1  | list    | 坐标和值数据集合 |
| 2  | outPath | 图片输出路径   |

返回值: 图例集合

#### 2. 绘制带背景的热力图
`List<Legend> drawImgBackground(List<HeatMapEntity> list, String background, String outPath);`

参数说明:

| 序号 | 参数     | 描述       |
|----|--------|----------|
| 1  | list   | 坐标和值数据集合 |
| 2  | background | 背景图地址    |
| 3  | outPath | 图片输出路径   |

返回值: 图例集合

> 注意：由于背景经过分辨率调试，仅支持data/dt-release.png背景图
#### 3. 插值点
`List<HeatMapEntity> interpolation(List<HeatMapEntity> sourceList, int width, int height);`

参数说明:

| 序号 | 参数     | 描述       |
|----|--------|----------|
| 1  | sourceList   | 坐标和值数据集合 |
| 2  | width | 待插值经度步长  |
| 3  | height | 待插值纬度步长  |

返回值: 插值后的数据集合
> 注意：此功能需要根据数据调试色域，才能达到较好效果

## 二、快速开始
1. 项目添加依赖
```xml
<dependency>
    <groupId>io.github.zk-api</groupId>
    <artifactId>heat-map</artifactId>
    <version>1.0.3</version>
</dependency>
```
2. 通过build方式构建参数，代码如下：
```java
IDrawProcessor drawProcessor = new DrawProcessorImpl.DrawProcessorBuilder().build();
```
此方式采用默认配置生成热力图，如需自定义配置，在build构建时进行设置，如设置生成分辨率代码如下：
```java
IDrawProcessor drawProcessor = new DrawProcessorImpl.DrawProcessorBuilder()
                .pixel(new BigDecimal("1"), new BigDecimal("1"))
                .build();
```
> 注意： 分辨率需要和数据保持一致，如分辨率为1x1，数据也需要为1度x1度。

可自定义的配置分别有
```java
// 分辨率，默认 1 x 1
pixel(BigDecimal width, BigDecimal height);
// 绘制左上角初始位置，默认经度 -180 纬度 90
position(Integer startLon, Integer startLat);
// 图例的色域和值范围
legend(List<Color> colors, List<Double[]> values);
// 滤波半径，默认带背景的半径15 无背景的半径5，值越大平滑效果越好，计算时间越长
radius(Integer backgroudRadius, Integer noBackgroudRadius);
```

3. 组装数据并调用接口

将数据组装为`List<HeatMapEntity>`格式，调用相应接口生成对应热力图，使用示例如下
```java
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

public class DrawExample {
    /**
     * 组装数据
     */
    public static List<HeatMapEntity> buildHeatMapEntities() {
        List<HeatMapEntity> list = new ArrayList<>();
        Path path = Paths.get("D:\\work\\workspace\\github\\heat-map\\data\\dop.DOP");
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(s -> {
                HeatMapEntity heatMapEntity = new HeatMapEntity();
                String[] colums = s.split("\\s+");
                BigDecimal latBigDecimal = new BigDecimal(colums[1]);
                BigDecimal lonBigDecimal = new BigDecimal(colums[2]);

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
     * 绘制热力图
     */
    public static List<Legend> defaultDraw() {
        IDrawProcessor drawProcessor = new DrawProcessorImpl.DrawProcessorBuilder().build();
        List<HeatMapEntity> heatMapEntities = buildHeatMapEntities();
        String filePath = "D:\\work\\workspace\\github\\heat-map\\data\\outpic\\1.png";
        return drawProcessor.drawImg(heatMapEntities, filePath);
    }

    public static void main(String[] args) {
        System.out.println(defaultDraw());
    }
}
```

# heat-map
热力图生成 v1.0

## 使用说明
热力图生成有3个主要方法，分别为：
1. 绘制无背景热力图
   `List<Legend> drawImg(List<HeatMapEntity>, String);`
2. 绘制带背景的热力图（仅支持提供的背景）
   `List<Legend> drawImgBackground(List<HeatMapEntity>, String, String);`
3. 插值点
   `List<HeatMapEntity> interpolation(List<HeatMapEntity>, int, int);`

使用方式：
1. 通过build方式构建参数
2. 调用绘制方法
具体使用方式查看`com.github.zk.heatmap.example.DrawExample`类中的方法

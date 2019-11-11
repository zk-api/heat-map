# heat-map
热力图生成
### 一、文件说明
1. CreateMap.java 创建热力图的主要类，包含测试的main方法
2. HeatMapEntity.java 热力图生成所需实体类
3. dt-release.png 热力图底图（仅提供经度-180度，纬度90度为左上角）
### 二、使用说明
热力图生成有3个主要方法，分别为：
1. 生成无背景地图的热力图（经度-180度，纬度90度为左上角）；
2. 生成带背景地图的热力图（经度-180度，纬度90度为左上角）；
3. 生成自定义热力图。

具体使用方式：
1. 将数据组织成List\<HeatMapEntity\>格式；
2. 调用createMap静态方法，传入相应参数即可（此类中自带一个main方法，针对文件格式的元数据做的示例）。
生成效果如下图：

![image](https://github.com/zk-api/heat-map/blob/master/data/outpic/2.png)

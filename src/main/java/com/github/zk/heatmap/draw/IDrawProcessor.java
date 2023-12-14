package com.github.zk.heatmap.draw;

import com.github.zk.heatmap.entity.HeatMapEntity;
import com.github.zk.heatmap.entity.Legend;
import java.util.List;

/**
 * 绘制处理
 *
 * @author zk
 */
public interface IDrawProcessor {

    /**
     * 绘制热力图
     *
     * @param list 数据
     * @param outPath 输出路径
     * @return 图例
     */
    List<Legend> drawImg(List<HeatMapEntity> list, String outPath);

    /**
     * 绘制带背景图的热力图
     *
     * @param list 数据
     * @param background 背景图地址
     * @param outPath 输出路径
     * @return 是否生成成功
     */
    List<Legend> drawImgBackground(List<HeatMapEntity> list, String background, String outPath);

    /**
     * 数据插值
     *
     * @param sourceList 原始数据
     * @param width 要插值的像素宽度
     * @param height 要插值的像素高度
     * @return 插值后数据
     */
    List<HeatMapEntity> interpolation(List<HeatMapEntity> sourceList, int width, int height);

}

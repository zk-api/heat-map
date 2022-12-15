package com.github.zk.heatmap.draw;

import com.github.zk.heatmap.entity.HeatMapEntity;
import com.github.zk.heatmap.entity.Legend;
import java.util.List;

/**
 * 绘制处理
 *
 * @author zk
 * @date 2022/11/10 15:43
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
     * @param background 输出路径
     * @return 是否生成成功
     */
    boolean drawImgBackground(List<HeatMapEntity> list, String background, String outPath);

}

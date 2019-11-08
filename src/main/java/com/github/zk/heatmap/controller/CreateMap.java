package com.github.zk.heatmap.controller;

import com.github.zk.heatmap.common.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zk
 * @date 2019/8/1 14:07
 */
@RestController
public class CreateMap {
    @RequestMapping("/heatMap")
    public Response heatMap() {
        Response response = Response.getInstance();
        //创建图片缓冲区对象bufferedImage,包括外框及颜色类型
        BufferedImage bufferedImage = new BufferedImage(360,180,BufferedImage.TYPE_INT_ARGB);
        //初始化绘图画笔
        Graphics graphics = bufferedImage.getGraphics();
        //将指定图片作为背景图
        ImageIcon imageIcon = new ImageIcon("E:\\githubWork\\heat-map\\1.jpg");
        graphics.drawImage(imageIcon.getImage(),0,0,360,180,null);

        for (int i=0;i<360;i++) {
            for (int j=0;j<180;j++) {
                //0(a)~255(b)随机数，Math.random() * (b-a) + a
                double R = Math.random() * (255 - 0) + 0;
                double G = Math.random() * (255 - 0) + 0;
                double B = Math.random() * (255 - 0) + 0;
                //随机颜色
                graphics.setColor(new Color((int)R,(int)G,(int)B, 50));
                //绘制每个网格
                graphics.fillRect(i,j,1,1);
            }
        }
        try {
            //将绘图写入文件中
            ImageIO.write(bufferedImage,"png",new File("E:\\githubWork\\heat-map\\heatmap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setOk(null, 0, "success", null);
        return response;
    }
    @RequestMapping("/test")
    public Response test() {
        Response instance = Response.getInstance();
        List list = new ArrayList();
        Double[][] plane1 = new Double[][]{{114.1479828172934d,39.7412124543823d},{108.7427231256765d,35.4428930291832d},{111.709d,34.7777d},{114.1479828172934d,39.7412124543823d}};
        list.add(plane1);
        instance.setOk(null,0,"",list);
        return instance;
    }
}

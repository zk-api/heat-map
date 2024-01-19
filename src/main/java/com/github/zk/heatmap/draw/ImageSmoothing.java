package com.github.zk.heatmap.draw;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * 平滑处理
 *
 * @author zk
 */
public class ImageSmoothing {

    public static BufferedImage filter(BufferedImage image, int radius) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filtered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                List<Integer> redValues = new ArrayList<>();
                List<Integer> greenValues = new ArrayList<>();
                List<Integer> blueValues = new ArrayList<>();
                List<Integer> alphaValues = new ArrayList<>();

                for (int j = -radius / 2; j <= radius / 2; j++) {
                    for (int i = -radius / 2; i <= radius / 2; i++) {
                        int neighborX = x + i;
                        int neighborY = y + j;

                        if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                            int rgb = image.getRGB(neighborX, neighborY);
                            int alpha = (rgb >> 24) & 0xFF;
                            int red = (rgb >> 16) & 0xFF;
                            int green = (rgb >> 8) & 0xFF;
                            int blue = rgb & 0xFF;

                            redValues.add(red);
                            greenValues.add(green);
                            blueValues.add(blue);
                            alphaValues.add(alpha);
                        }
                    }
                }

                Collections.sort(redValues);
                Collections.sort(greenValues);
                Collections.sort(blueValues);
                Collections.sort(alphaValues);

                int medianRed = redValues.get((redValues.size() - 1) / 2);
                int medianGreen = greenValues.get((greenValues.size() - 1) / 2);
                int medianBlue = blueValues.get((blueValues.size() - 1) / 2);
                int medianAlpha = alphaValues.get((alphaValues.size() - 1) / 2);

                int filteredRGB = (medianAlpha << 24) | (medianRed << 16) | (medianGreen << 8) | medianBlue;

                filtered.setRGB(x, y, filteredRGB);
            }
        }

        return filtered;
    }

    public static void main(String[] args) {
        ImageSmoothing imageSmoothing = new ImageSmoothing();
        try {
            // 读取原始图像
            BufferedImage originalImage = ImageIO.read(new File("D:\\work\\workspace\\github\\heat-map\\data\\outpic\\1-1.png"));

            // 进行平滑处理
            BufferedImage smoothedImage = imageSmoothing.filter(originalImage, 5);

            // 保存处理后的图像
            ImageIO.write(smoothedImage, "png", new File("D:\\work\\smoothed_image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

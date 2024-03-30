package com.piggy.util;

//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 屏幕文字识别
 */
public class ScreenTextRecognitionUtil {

//    public static void main(String[] args) {
//        // 创建一个Tesseract实例
//        Tesseract tesseract = new Tesseract();
//        try {
//            // 设置Tesseract的训练数据路径
//            tesseract.setDatapath("D:\\Sundries\\Ai");
//
//            //设置训练语言
////            tesseract.setLanguage("eng");
//
//            long startTime = System.currentTimeMillis();
//            // 截取屏幕右下角区域
//            screenCapture();
//            // 读取截图
//            File file = new File("src/main/resources/screenshot.png");
////            File file = new File("src/main/resources/test.jpg");
//
//            // OCR识别
//            String result = tesseract.doOCR(file);
//
//            // 输出识别结果
//            long endTime = System.currentTimeMillis();
//            System.out.println("result:\n"+result);
//            System.out.println("用时:"+(endTime-startTime));
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }
//    }

    public static void screenCapture(){
        try {
            // 创建Robot对象
            Robot robot = new Robot();

            // 获取屏幕分辨率
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // 定义截取区域的大小（例如：500x250像素）
            int captureWidth = 500;
            int captureHeight = 250;

            // 计算截取区域的坐标
            // 从屏幕的宽度和高度中减去截取区域的宽度和高度，以获得右下角的起始坐标
            int x = screenSize.width - captureWidth;
            int y = screenSize.height - captureHeight;

            // 创建一个矩形来表示截取的区域
            Rectangle captureRect = new Rectangle(x, y, captureWidth, captureHeight);

            // 捕捉屏幕截图
            BufferedImage screenCapture = robot.createScreenCapture(captureRect);

            // 保存图像到文件
            ImageIO.write(screenCapture, "png", new File("src/main/resources/screenshot.png"));

            System.out.println("屏幕截图已保存为 screenshot.png");
        } catch (AWTException | IOException ex) {
            System.err.println("屏幕截图失败：" + ex);
        }
    }
}

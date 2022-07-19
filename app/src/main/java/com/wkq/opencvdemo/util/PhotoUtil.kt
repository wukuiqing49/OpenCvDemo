package com.wkq.opencvdemo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.widget.ImageView


import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.photo.Photo


/**
 * @author wkq
 *
 * @date 2022年07月15日 15:26
 *
 *@des  Application   加载System.loadLibrary("opencv_java4")
 *
 */

object PhotoUtil {


    /**
     * 彩色图像快速均值去噪函数的改进
     */
    fun fastNlMeansDenoising(bitmap: Bitmap, imageResult: ImageView,size:Float){
        var src =  Mat()
        var ret =  Mat()

        Utils.bitmapToMat(bitmap, src)
        Photo.fastNlMeansDenoisingColored(src,ret,size)
        Utils.matToBitmap(ret, bitmap)

        imageResult.setImageBitmap(bitmap);

        src.release();
        ret.release();
    }
    /**
     * 锐化效果
     * size:Floa
     * Range between 0 to 200.
     */
    fun detailEnhance(bitmap: Bitmap, imageResult: ImageView,size:Float){
        var src =  Mat()
        var ret =  Mat()
        Utils.bitmapToMat(bitmap, src)
        Photo.detailEnhance(src,ret,size)

        Utils.matToBitmap(ret, bitmap)
        imageResult.setImageBitmap(bitmap);

        src.release();
        ret.release();
    }
    /**
     * 褪色
     * size:Floa
     * Range between 0 to 200.
     */
    fun decolor(bitmap: Bitmap, imageResult: ImageView){
        var src =  Mat();
        var dst =  Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
        Utils.matToBitmap(dst, bitmap);
        imageResult.setImageBitmap(bitmap);
        src.release();
        dst.release();
    }

    /**
     * 风格化
     *
     * 风格化旨在产生各种效果的数字图像，而不是专注于

     *照片写实主义。边缘感知滤波器是样式化的理想选择，因为它们可以提取低分辨率的区域

     *对比度，同时保留或增强高对比度特征。
     *
     *  sigma_s  size between 0 to 200. 邻近Pixel的数量【0，200】
     * sigma_r 颜色平衡度，数值越大，同颜色的区域就会越大【0,1】
     */
    fun stylization(bitmap: Bitmap, imageResult: ImageView,size:Float){
        var src =  Mat()
        var ret =  Mat()
        Utils.bitmapToMat(bitmap, src)
        Photo.stylization(src,ret,200f,0.1f)

        Utils.matToBitmap(ret, bitmap)
        imageResult.setImageBitmap(bitmap);

        src.release();
        ret.release();
    }

    /**
     * 图像描绘，有两个输出分别是1通灰阶图与3通GBR图
     *
     * @param src Input 8-bit 3-channel image.
     * @param dst1 Output 8-bit 1-channel image. 1通灰阶图
     * @param dst2 Output image with the same size and type as src. 3通GBR图
     * @param sigma_s %Range between 0 to 200. 0 to 200. 邻近Pixel的数量【0，200】
     * @param sigma_r %Range between 0 to 1. 颜色平衡度，数值越大，同颜色的区域就会越大【0,1】
     * @param shade_factor %Range between 0 to 0.1  值越高图像越亮【0，0.1】
     */
    fun pencilSketch(bitmap: Bitmap, imageResult: ImageView){
        var src =  Mat()
        var retOne =  Mat()
        var ret =  Mat()
        Utils.bitmapToMat(bitmap, src)
        Photo.pencilSketch(src,retOne,ret,200f,0.1f,0.02f)

        Utils.matToBitmap(ret, bitmap)
        imageResult.setImageBitmap(bitmap);

        src.release();
        ret.release();
    }

    //将彩色图像转换为灰度图像  脱色
    //Photo.decolor(src,ret,size)

    //改变颜色
    //Photo.colorChange(src,ret,size)

    //局部修改图像的视照明
    //Photo.illuminationChange(src,ret,size)

    //纹理发泡
    //Photo.textureFlattening(src,ret,size)


    //素描  铅笔状非真实感线条绘制
    //Photo.pencilSketch(src,ret,size)

    //风格化
    //Photo.stylization(src,ret,size)
    // 铅笔状非真实感线条绘制
    //Photo.pencilSketch(src,ret,size)




}
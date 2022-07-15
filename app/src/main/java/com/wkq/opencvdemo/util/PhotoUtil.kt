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
import org.opencv.photo.Photo


/**
 * @author wkq
 *
 * @date 2022年07月15日 15:26
 *
 *@des
 *
 */

object PhotoUtil {


    //加载库
    init {
        System.loadLibrary("opencv_java4")
    }

    /**
     * 快速降噪
     */
    fun fastNlMeansDenoising(bitmap: Bitmap, imageResult: ImageView,size:Float){
        //灰度处理
        Log.e("效果:",size.toString())
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
     * 细节增强
     * size:Floa
     * Range between 0 to 200.
     */
    fun detailEnhance(bitmap: Bitmap, imageResult: ImageView,size:Float){
        //灰度处理
        Log.e("效果:",size.toString())
        var src =  Mat()
        var ret =  Mat()
        Utils.bitmapToMat(bitmap, src)
        Photo.detailEnhance(src,ret,size)

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

    //边缘保持滤波器

    //素描
    //Photo.pencilSketch(src,ret,size)

    //风格化
    //Photo.stylization(src,ret,size)




}
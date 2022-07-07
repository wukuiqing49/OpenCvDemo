package com.wkq.opencvdemo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.MORPH_RECT


object ImageUtil {

    init {
        System.loadLibrary("opencv_java4")
    }
    /**
     * 高斯差分
     */
    fun differenceOfGaussian(imageBitmap: Bitmap, imageResult: ImageView) {
        val grayMat = Mat()
        val blur1 = Mat()
        val blur2 = Mat()
        // 将图像转换成灰度图像
        val originalMat = Mat(imageBitmap.height, imageBitmap.width, CvType.CV_8UC4)
        Utils.bitmapToMat(imageBitmap, originalMat)
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGRA2GRAY)
        // 进行高斯模糊
        Imgproc.GaussianBlur(originalMat, blur1, Size(15.0, 15.0), 5.0)
        Imgproc.GaussianBlur(originalMat, blur2, Size(21.0, 21.0), 5.0)
        // 将两幅模糊后的图像相减
        val doG = Mat()
        Core.absdiff(blur1, blur2, doG)
        // 反转二值阈值化
        Core.multiply(doG, Scalar(100.0), doG)
        Imgproc.threshold(doG, doG, 50.0, 255.0, Imgproc.THRESH_BINARY_INV)
        val resultBitmap = Bitmap.createBitmap(doG.cols(), doG.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(doG, resultBitmap)
        imageResult.setImageBitmap(resultBitmap)

    }

    /**
     * 灰度处理
     */
    fun Togray(bitmap: Bitmap, imageResult: ImageView){
        //灰度处理
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
     * 腐蚀操作
     */
    fun Tocorrosion(bitmap: Bitmap, imageResult: ImageView){
        //灰度处理
        var src =  Mat();
        var dst =  Mat();
        val element = Imgproc.getStructuringElement(MORPH_RECT, Size(10.0, 10.0))

        Utils.bitmapToMat(bitmap, src);
        Imgproc.erode(src, dst, element);
        Utils.matToBitmap(dst, bitmap);
        imageResult.setImageBitmap(bitmap);
        src.release();
        dst.release();
    }

    /**
     * 边缘监测
     */
    fun CannyScan(bitmap: Bitmap, imageResult: ImageView){
        //灰度处理

        val src = Mat()
        Utils.bitmapToMat(bitmap, src)

        val gray = Mat()
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY) //灰度处理

        val ret = src.clone()

        Imgproc.Canny(src, ret, 75.0, 200.0);
        Utils.matToBitmap(ret, bitmap);

        imageResult.setImageBitmap(bitmap);
        src.release();
        gray.release();
    }

    /**
     * 高斯模糊
     */
    fun ToGaussian(bitmap: Bitmap, imageResult: ImageView){
        //灰度处理
        var src =  Mat();
        var dst =  Mat();


        Utils.bitmapToMat(bitmap, src);
        Imgproc.GaussianBlur(src, dst, Size(77.0, 77.0), 5.0, 5.0)
        Utils.matToBitmap(dst, bitmap);
        imageResult.setImageBitmap(bitmap);
        src.release();
        dst.release();
    }

    /**
     * 膨胀操作
     */
    fun Todilate(bitmap: Bitmap, imageResult: ImageView){
        //灰度处理
        var src =  Mat();
        var ret =  Mat();
        val element = Imgproc.getStructuringElement(MORPH_RECT, Size(10.0, 10.0))
        Utils.bitmapToMat(bitmap, src);
        Imgproc.dilate(src, ret, element);
        Utils.matToBitmap(ret, bitmap);
        imageResult.setImageBitmap(bitmap);
        src.release();
        ret.release();
    }


    /**
     * 中值滤波
     */
    fun TomedianBlur(bitmap: Bitmap, imageResult: ImageView){
        //灰度处理
        var src =  Mat();
        var ret =  Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.medianBlur(src, ret, 77);
        Utils.matToBitmap(ret, bitmap);
        imageResult.setImageBitmap(bitmap);
        src.release();
        ret.release();
    }


    fun getBitmap(context: Context?, vectorDrawableId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable: Drawable? = context!!.getDrawable(vectorDrawableId)
            bitmap = Bitmap.createBitmap(
                vectorDrawable!!.intrinsicWidth,
                vectorDrawable!!.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
            vectorDrawable.draw(canvas)
        } else {
            bitmap = BitmapFactory.decodeResource(context!!.getResources(), vectorDrawableId)
        }
        return bitmap
    }


    /**
     * 设置图片亮度
     */
    fun toLight(bitmap: Bitmap, imageResult: ImageView, lightNum: Double){
        //灰度处理
        var src =  Mat();
        var ret =  Mat();
        Utils.bitmapToMat(bitmap, src);
        var mat=changeMatLuminance(src, lightNum);
        val bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mat, bitmap)
        imageResult.setImageBitmap(bitmap);
        src.release();
        ret.release();
        mat.release();
    }


    /**
     * 改变图像对比度
     */
    fun toMatLuminance(bitmap: Bitmap, imageResult: ImageView, lightNum: Double){
        //灰度处理
        var src =  Mat();
        Utils.bitmapToMat(bitmap, src);
        var mat=changeMatContrast(src, lightNum);
        val bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mat, bitmap)
        imageResult.setImageBitmap(bitmap);
        src.release();
        mat.release();
    }



    /**
     * 改变图像亮度
     *
     * @param srcMat Mat数据源
     * @param luminanceParams 亮度参数，小于0降低亮度，大于0增加亮度
     * @return 调整对比度后的图像
     */
    fun changeMatLuminance(srcMat: Mat, luminanceParams: Double):Mat{
        val res = Mat()
        Core.add(srcMat, Scalar(luminanceParams, luminanceParams, luminanceParams), res)
        return res
    }

    /**
     * 改变图像对比度
     *
     * @param srcMat Mat数据源
     * @param contrastParams 对比度参数，小于1降低对比度，大于1增加对比度
     * @return 调整对比度后的图像
     */
    fun changeMatContrast(srcMat: Mat, contrastParams: Double):Mat{
        val res = Mat()
        Core.multiply(srcMat, Scalar(contrastParams, contrastParams, contrastParams), res)
        return res
    }


    fun yuv(bitmap: Bitmap, imageResult: ImageView, lightNum: Double){
        var srcMat =  Mat();
        Utils.bitmapToMat(bitmap, srcMat);
        var dstMat = Mat(srcMat.rows(), srcMat.cols(), srcMat.type())
        var bgrList: ArrayList<Mat> = ArrayList(3)
        Core.split(srcMat, bgrList) // 将 BGR 3个通道拆分
        for (i in 0..2) {
            Imgproc.equalizeHist(bgrList[i], bgrList[i]) // 对每个通道直方图均衡化
        }
        var bgrMat = Mat(srcMat.rows(), srcMat.cols(), srcMat.type())
        Core.merge(bgrList, bgrMat) // 合并通道
        Core.addWeighted(srcMat, lightNum/10, bgrMat, lightNum/10, (lightNum*3-10), dstMat) // 添加权重
        Utils.matToBitmap(dstMat, bitmap)
        imageResult.setImageBitmap(bitmap);

        srcMat.release();
        dstMat.release();
        bgrMat.release()
        bgrList.clear()

    }

    /**
     * 图片反相
     *
     * @param srcMat
     * @return
     */
    fun invert(srcMat: Mat): Mat? {
        val rows = srcMat.rows()
        val cols = srcMat.cols()
        val type = srcMat.type()
        val mat = Mat(rows, cols, type, Scalar(255.0, 255.0, 255.0)) // 算子。全白(255, 255, 255)
        val dst = Mat(rows, cols, type)
        // Core.bitwise_xor(srcMat, mat, dst); // 布尔运算。
        // Core.subtract(mat, srcMat, dst); // 相减
        Core.bitwise_not(srcMat, dst) // 取反
        return dst
    }


}
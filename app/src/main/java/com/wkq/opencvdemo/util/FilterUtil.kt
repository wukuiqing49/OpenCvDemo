package com.wkq.opencvdemo.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.FONT_HERSHEY_COMPLEX_SMALL
import java.nio.charset.Charset


/**
 * @author wkq
 *
 * @date 2022年07月06日 16:53
 *
 *@des  滤镜工具类
 *
 */

object FilterUtil {


    /**
     * 灰度处理
     */
    fun togray(bitmap: Bitmap, imageResult: ImageView) {
        //灰度处理
        var src = Mat();
        var dst = Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
        Utils.matToBitmap(dst, bitmap);
        imageResult.setImageBitmap(bitmap);
        src.release();
        dst.release();
    }

    /**
     * 绘制文字  不支持中文
     */
    fun drawText(bitmap: Bitmap, imageResult: ImageView) {
        var dst = Mat();
        Imgproc.putText(
            dst, "buzhichi??Chinese",
            Point(bitmap.getWidth().toDouble() / 2, bitmap.getHeight().toDouble() / 2),
            Imgproc.FONT_HERSHEY_PLAIN, 2.5,
            Scalar(0.0, 0.0, 0.0), 1, Imgproc.LINE_AA, false
        )
        Utils.matToBitmap(dst, bitmap);
        imageResult.setImageBitmap(bitmap);
        dst.release();
    }



    /**
     * 轮廓()即使边缘监测
     */
    fun cannyScan(photo: Bitmap, imageResult: ImageView) {
        var mat = Mat()
        var Cmat = Mat()
        var cartton = Bitmap.createBitmap(
            photo.getWidth(),
            photo.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        Utils.bitmapToMat(photo, mat)
        Imgproc.Canny(mat, Cmat, 50.0, 100.0)
        Core.bitwise_not(Cmat, Cmat)
        Utils.matToBitmap(Cmat, cartton)
        imageResult.setImageBitmap(cartton);
        Cmat.release()
        mat.release()


    }


    /**
     * 二值化
     */
    fun threshold(photo: Bitmap, imageResult: ImageView) {
        var mat = Mat()
        var thes = Bitmap.createBitmap(photo.width, photo.height, Bitmap.Config.ARGB_8888)
        Utils.bitmapToMat(photo, mat)
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY)
        Core.bitwise_not(mat, mat)
        Imgproc.threshold(mat, mat, 160.0, 255.0, Imgproc.THRESH_BINARY_INV)
        Utils.matToBitmap(mat, thes)
        imageResult.setImageBitmap(thes);
        mat.release()

    }

    /**
     * 素描
     */
    fun sm(photo: Bitmap, imageResult: ImageView) {
        var SM = Mat()
        var SM1 = Mat()
        var sumiaoMap = Bitmap.createBitmap(photo.width, photo.height, Bitmap.Config.ARGB_8888)
        var SMB = Bitmap.createBitmap(photo.width, photo.height, Bitmap.Config.ARGB_8888)
        var SMB1 = Bitmap.createBitmap(photo.width, photo.height, Bitmap.Config.ARGB_8888)
        Utils.bitmapToMat(photo, SM)
        //灰度化
        Imgproc.cvtColor(SM, SM, Imgproc.COLOR_RGB2GRAY)
        //颜色取反
        Core.bitwise_not(SM, SM1)
        //高斯模糊
        Imgproc.GaussianBlur(SM1, SM1, Size(13.0, 13.0), 0.0, 0.0)
        Utils.matToBitmap(SM, SMB)
        Utils.matToBitmap(SM1, SMB1)
        for (i in 0 until SMB.width) {
            for (j in 0 until SMB.height) {
                var A = SMB.getPixel(i, j)
                var B = SMB1.getPixel(i, j)
                var CR: Int = colordodge(Color.red(A), Color.red(B))
                var CG: Int = colordodge(Color.green(A), Color.red(B))
                var CB: Int = colordodge(Color.blue(A), Color.blue(B))
                sumiaoMap.setPixel(i, j, Color.rgb(CR, CG, CB))
            }
        }
        imageResult.setImageBitmap(sumiaoMap);
        SM1.release()
        SM.release()
    }

    /**
     * 怀旧
     */
    fun hj(photo: Bitmap, imageResult: ImageView) {
        var huaijiu = Bitmap.createBitmap(photo.width, photo.height, Bitmap.Config.ARGB_8888)
        for (i in 0 until photo.width) {
            for (j in 0 until photo.height) {
                var A = photo.getPixel(i, j)
                var AR =
                    (0.393 * Color.red(A) + 0.769 * Color.green(A) + 0.189 * Color.blue(A)).toInt()
                var AG =
                    (0.349 * Color.red(A) + 0.686 * Color.green(A) + 0.168 * Color.blue(A)).toInt()
                var AB =
                    (0.272 * Color.red(A) + 0.534 * Color.green(A) + 0.131 * Color.blue(A)).toInt()
                AR = if (AR > 255) 255 else AR
                AG = if (AG > 255) 255 else AG
                AB = if (AB > 255) 255 else AB
                huaijiu.setPixel(i, j, Color.rgb(AR, AG, AB))
            }
        }
        imageResult.setImageBitmap(huaijiu);
    }

    /**
     *连环画
     */
    fun lhh(photo: Bitmap, imageResult: ImageView) {
        var lianhuanhua = Bitmap.createBitmap(photo.width, photo.height, Bitmap.Config.ARGB_8888)
        for (i in 0 until photo.width) {
            for (j in 0 until photo.height) {
                var A = photo.getPixel(i, j)
                var AR =
                    Math.abs(Color.red(A) - Color.blue(A) + Color.green(A) + Color.green(A)) * Color.red(
                        A
                    ) / 256
                var AG =
                    Math.abs(Color.red(A) - Color.green(A) + Color.blue(A) + Color.blue(A)) * Color.red(
                        A
                    ) / 256
                var AB =
                    Math.abs(Color.red(A) - Color.blue(A) + Color.blue(A) + Color.blue(A)) * Color.green(
                        A
                    ) / 256
                AR = if (AR > 255) 255 else AR
                AG = if (AG > 255) 255 else AG
                AB = if (AB > 255) 255 else AB
                lianhuanhua.setPixel(i, j, Color.rgb(AR, AG, AB))
            }
        }
        imageResult.setImageBitmap(lianhuanhua);
    }

    /**
     * 膨胀
     * 使用特定的结构元素放大图像。
     */
    fun pz(bitmap: Bitmap, imageResult: ImageView) {

        var src = Mat()
        var ret = Mat()

        val element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, Size(10.0, 10.0))
        Utils.bitmapToMat(bitmap, src);
        Imgproc.dilate(src, ret, element);
        Utils.matToBitmap(ret, bitmap);
        imageResult.setImageBitmap(bitmap);
        src.release();
        ret.release();
    }

    /**
     * 熔铸滤镜
     */
    fun rz(photo: Bitmap, imageResult: ImageView) {
        var rongzhu = Bitmap.createBitmap(photo.width, photo.height, Bitmap.Config.ARGB_8888)
        for (i in 0 until photo.width) {
            for (j in 0 until photo.height) {
                var A = photo.getPixel(i, j)
                var AR = Color.red(A) * 128 / (Color.blue(A) + Color.green(A) + 1)
                var AG = Color.green(A) * 128 / (Color.blue(A) + Color.red(A) + 1)
                var AB = Color.blue(A) * 128 / (Color.red(A) + Color.green(A) + 1)
                AR = if (AR > 255) 255 else AR
                AG = if (AG > 255) 255 else AG
                AB = if (AB > 255) 255 else AB
                rongzhu.setPixel(i, j, Color.rgb(AR, AG, AB))
            }
        }
        imageResult.setImageBitmap(rongzhu);
    }

    /**
     * 冰冻
     */
    fun bd(photo: Bitmap, imageResult: ImageView) {
        var bingdong = Bitmap.createBitmap(photo.width, photo.height, Bitmap.Config.ARGB_8888)
        for (i in 0 until photo.width) {
            for (j in 0 until photo.height) {
                var A = photo.getPixel(i, j)
                var AR = (Color.red(A) - Color.blue(A) - Color.green(A)) * 3 / 2
                var AG = (Color.green(A) - Color.blue(A) - Color.red(A)) * 3 / 2
                var AB = (Color.blue(A) - Color.red(A) - Color.green(A)) * 3 / 2
                AR = if (AR > 255) 255 else AR
                AG = if (AG > 255) 255 else AG
                AB = if (AB > 255) 255 else AB
                bingdong.setPixel(i, j, Color.rgb(AR, AG, AB))
            }
        }
        imageResult.setImageBitmap(bingdong);
    }

    /**
     * 浮雕滤镜
     */
    fun fd(photo: Bitmap, imageResult: ImageView) {

        var bingdong = Bitmap.createBitmap(photo.width, photo.height, Bitmap.Config.ARGB_8888)
        for (i in 1 until photo.width - 1) {
            for (j in 1 until photo.height - 1) {
                var A = photo.getPixel(i - 1, j - 1)
                var B = photo.getPixel(i + 1, j + 1)
                var AR = Color.red(B) - Color.red(A) + 128
                var AG = Color.green(B) - Color.green(A) + 128
                var AB = Color.blue(B) - Color.blue(A) + 128
                AR = if (AR > 255) 255 else AR
                AG = if (AG > 255) 255 else AG
                AB = if (AB > 255) 255 else AB
                bingdong.setPixel(i, j, Color.rgb(AR, AG, AB))
            }
        }
        imageResult.setImageBitmap(bingdong);
    }

    private fun colordodge(A: Int, B: Int): Int {
        return Math.min(A + A * B / (255 - B + 1), 255)
    }


}
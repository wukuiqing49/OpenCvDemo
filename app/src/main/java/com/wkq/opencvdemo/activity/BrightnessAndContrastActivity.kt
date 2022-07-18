package com.wkq.opencvdemo.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.wkq.opencvdemo.R
import com.wkq.opencvdemo.databinding.ActivityBrightnessBinding
import com.wkq.opencvdemo.util.ImageUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc


/**
 * @author wkq
 *
 * @date 2022年07月07日 14:15
 *
 *@des
 *
 */

class BrightnessAndContrastActivity : AppCompatActivity() {


    companion object {
        fun newInstance(mContext: Context) {
            var intent = Intent(mContext, BrightnessAndContrastActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    val path = "https://img2.baidu.com/it/u=686238839,986827545&fm=253&fmt=auto&app=138&f=JPEG"

    private var originBrightness: Double = 0.0

    private var brightness: Double = 0.0
        set(value) {
            field = value
            adjustBrightnessContrast()
        }

    private var contrast: Double = 100.0
        set(value) {
            field = value
            adjustBrightnessContrast()
        }

    private fun adjustBrightnessContrast() {
        val pre = Mat()
        Core.add(
                source,
                Scalar(
                        brightness - originBrightness,
                        brightness - originBrightness,
                        brightness - originBrightness
                ),
                pre
        )
        val dst = Mat()
        Core.multiply(
                pre,
                Scalar(
                        contrast / 100,
                        contrast / 100,
                        contrast / 100,
                        contrast / 100
                ),
                dst
        )
        val bitmap = Bitmap.createBitmap(dst.cols(), dst.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(dst, bitmap)
        binding!!.ivContent.setImageBitmap(bitmap)
        pre.release()
        dst.release()
    }
    var binding: ActivityBrightnessBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView<ActivityBrightnessBinding>(
            this,
            R.layout.activity_brightness
        )
        initImageView()
        initView()
    }
    private lateinit var source: Mat
    private fun initImageView() {

        Observable.create<Bitmap> {
            var bm = Glide.with(this).asBitmap().load(path).submit().get()
            it.onNext(bm)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Bitmap> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(bitmap: Bitmap) {


                    Glide.with(this@BrightnessAndContrastActivity).load(bitmap).into(binding!!.ivContent)
                        val bgr = Mat()
                        Utils.bitmapToMat(bitmap,bgr)

                        source = Mat()
                        Imgproc.cvtColor(bgr, source, Imgproc.COLOR_BGR2RGB)
                        Utils.matToBitmap(source, bitmap)

                        originBrightness = Core.mean(source).`val`[0]
                        binding!!.sbLd.progress = originBrightness.toInt()
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }
                })

    }

    private fun initView() {
        Glide.with(this).load(path).into(binding!!.ivContent)
        binding!!.sbDbd.progress = 100
        binding!!.sbDbd.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                contrast = progress.toDouble()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding!!.sbLd.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                brightness = progress.toDouble()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }

    private fun processLight(progress: Int) {
        Observable.create<Bitmap> {
            var bm = Glide.with(this).asBitmap().load(path).submit().get()
            it.onNext(bm)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Bitmap> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Bitmap) {
                    ImageUtil.toLight(t, binding!!.ivContent, progress.toDouble())
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

    }
    private fun processDB(progress: Int) {
        Observable.create<Bitmap> {
            var bm = Glide.with(this).asBitmap().load(path).submit().get()
            it.onNext(bm)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Bitmap> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Bitmap) {
                    ImageUtil.yuv(t, binding!!.ivContent, progress.toDouble()/10)
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

    }


}
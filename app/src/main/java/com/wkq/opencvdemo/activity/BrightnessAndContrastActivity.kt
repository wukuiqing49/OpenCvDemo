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

    var binding: ActivityBrightnessBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView<ActivityBrightnessBinding>(
            this,
            R.layout.activity_brightness
        )
        initView()
    }

    private fun initView() {
        Glide.with(this).load(path).into(binding!!.ivContent)
        binding!!.sbDbd.max = 10
        binding!!.sbDbd.min = 0
        binding!!.sbDbd.progress = 5
        binding!!.sbDbd.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                processDB(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
//        对比度参数，小于1降低对比度，大于1增加对比度

        binding!!.sbBgd.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        binding!!.sbLd.max = 100
        binding!!.sbLd.min = -100
        binding!!.sbLd.progress = 0
        binding!!.sbLd.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                processLight(progress)
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
                    ImageUtil.yuv(t, binding!!.ivContent, progress.toDouble())
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

    }


}
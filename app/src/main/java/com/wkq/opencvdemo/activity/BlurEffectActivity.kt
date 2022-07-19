package com.wkq.opencvdemo.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.wkq.opencvdemo.R
import com.wkq.opencvdemo.databinding.ActivityBlurEffectBinding
import com.wkq.opencvdemo.databinding.ActivityDemoSaicingBinding
import com.wkq.opencvdemo.util.ImageUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * @author wkq
 *
 * @date 2022年07月19日 9:04
 *
 *@des
 *
 */

class BlurEffectActivity : AppCompatActivity(), View.OnClickListener {
    val path = "https://img2.baidu.com/it/u=686238839,986827545&fm=253&fmt=auto&app=138&f=JPEG"

    companion object {
        fun newInstance(mContext: Context) {
            var intent = Intent(mContext, BlurEffectActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    var binding: ActivityBlurEffectBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityBlurEffectBinding>(this, R.layout.activity_blur_effect)
        binding!!.onCLick = this
        initIcon()

    }

    private fun initIcon() {
        Glide.with(this).load(path).into(binding!!.ivInitial)
        Glide.with(this).load(path).into(binding!!.ivContent)
    }


    fun downImag(type: Int) {
        Observable.create<Bitmap> {
            var bm = Glide.with(this).asBitmap().load(path).submit().get()
            it.onNext(bm)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Bitmap> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Bitmap) {
                        mohu(type, t)
                    }


                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }
                })
    }

    fun mohu(type: Int, t: Bitmap) {
        when (type) {
            0 -> {
                ImageUtil.pyrUp(t, binding!!.ivContent)
            }
            1 -> {
                ImageUtil.ToGaussian(t, binding!!.ivContent)
            }
            2 -> {
                ImageUtil.medianBlur(t, binding!!.ivContent, size)
            }
            3 -> {
                ImageUtil.blur(t, binding!!.ivContent, sizeBlur)
            }
            4 -> {
                ImageUtil.boxFilter(t, binding!!.ivContent, depth, sizeBlur)
            }
        }
    }

    var size = 1
    var sizeBlur = 1.0
    var depth = -1
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.bt_reset -> {
                initIcon()
            }
            R.id.bt_py -> {
                downImag(0)
            }

            R.id.bt_gs -> {
                downImag(1)
            }


            R.id.bt_filter -> {
                size += 2
                downImag(2)
            }

            R.id.bt_blur -> {
                sizeBlur += 2
                downImag(3)
            }
            R.id.bt_boxFilter -> {
                sizeBlur += 2
                downImag(4)
            }

        }
    }

}
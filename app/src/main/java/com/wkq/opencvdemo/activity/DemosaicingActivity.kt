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
import com.wkq.opencvdemo.databinding.ActivityDemoSaicingBinding
import com.wkq.opencvdemo.databinding.ActivityFilterBinding
import com.wkq.opencvdemo.util.FilterUtil
import com.wkq.opencvdemo.util.ImageUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * @author wkq
 *
 * @date 2022年07月06日 16:40
 *
 *@des 去除马赛克
 *
 *
 */

class DemosaicingActivity : AppCompatActivity(), View.OnClickListener {
    val path = "https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D450%2C600/sign=2f85fd16d3c8a786be7f420a5239e50b/0df431adcbef760941acc4732cdda3cc7dd99eb2.jpg"

    companion object {
        fun newInstance(mContext: Context) {
            var intent = Intent(mContext, DemosaicingActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    var binding: ActivityDemoSaicingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDemoSaicingBinding>(this, R.layout.activity_demo_saicing)
        binding!!.onCLick = this
        initIcon()

    }

    private fun initIcon() {
        Glide.with(this).load(path).into(binding!!.ivInitial)
        Glide.with(this).load(path).into(binding!!.ivContent)
    }


    fun showStyle() {
        Observable.create<Bitmap> {
            var bm = Glide.with(this).asBitmap().load(path).submit().get()
            it.onNext(bm)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Bitmap> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Bitmap) {

                        showPhoto(t)
                    }


                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }
                })
    }

    private fun showPhoto(t: Bitmap) {

        ImageUtil.demosaicing(t,binding!!.ivContent)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.bt_reset -> {
                initIcon()
            }

            R.id.bt_mo -> {
                showStyle()
            }

        }
    }


}
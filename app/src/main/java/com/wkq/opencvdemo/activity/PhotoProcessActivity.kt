package com.wkq.opencvdemo.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.wkq.opencvdemo.R
import com.wkq.opencvdemo.databinding.ActivityPhotoBinding
import com.wkq.opencvdemo.util.ImageUtil
import com.wkq.opencvdemo.util.PhotoUtil
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

class PhotoProcessActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        fun newInstance(mContext: Context) {
            var intent = Intent(mContext, PhotoProcessActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    val path = "https://img2.baidu.com/it/u=686238839,986827545&fm=253&fmt=auto&app=138&f=JPEG"

    var binding: ActivityPhotoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView<ActivityPhotoBinding>(
                this,
                R.layout.activity_photo
        )
        initView()
    }

    private fun initView() {
        Glide.with(this).load(R.mipmap.ic_launcher).into(binding!!.iv)
        Glide.with(this).load(R.mipmap.ic_launcher).into(binding!!.ivContent)
        binding!!.onCLick = this
    }

    private fun processPhotoJz(progress: Float) {
        Observable.create<Bitmap> {


            var bm = Glide.with(this).asBitmap().load(R.mipmap.ic_launcher).submit().get()
            if (bm != null) it.onNext(bm)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Bitmap> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(bitmap: Bitmap) {
                        if (bitmap != null && bitmap.byteCount > 10) {
                            PhotoUtil.fastNlMeansDenoising(bitmap, binding!!.ivContent, progress)
                        } else {
                            Log.e("图片异常", bitmap.byteCount.toString())
                        }

                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }
                })

    }
    private fun processPhotosjzq(progress: Float) {
        Observable.create<Bitmap> {


            var bm = Glide.with(this).asBitmap().load(R.mipmap.ic_launcher).submit().get()
            if (bm != null) it.onNext(bm)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Bitmap> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(bitmap: Bitmap) {
                        if (bitmap != null && bitmap.byteCount > 10) {
                            PhotoUtil.fastNlMeansDenoising(bitmap, binding!!.ivContent, progress)
                        } else {
                            Log.e("图片异常", bitmap.byteCount.toString())
                        }

                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }
                })

    }

    var size = 1f
    var Range=1f
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.bt_jz -> {
                processPhotoJz(size)
                size += 3f
            }
            R.id.bt_sjzq -> {
                processPhotosjzq(Range)
                Range += 3f
            }
        }
    }


}
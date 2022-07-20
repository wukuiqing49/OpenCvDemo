package com.wkq.opencvdemo.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

    val path = "https://img-blog.csdnimg.cn/20210721193446915.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2VsbGlzcHk=,size_16,color_FFFFFF,t_70"

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
        Glide.with(this).load(path).into(binding!!.iv)
        Glide.with(this).load(path).into(binding!!.ivContent)
        binding!!.onCLick = this
    }


    private fun downImg(type: Int) {
        Observable.create<Bitmap> {
            var bm = Glide.with(this).asBitmap().load(path).submit().get()
            if (bm != null) it.onNext(bm)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Bitmap> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(bitmap: Bitmap) {
                        if (bitmap != null && bitmap.byteCount > 10) {
                            processImg(type, bitmap)
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

    private fun processImg(type: Int, bitmap: Bitmap) {
        when (type) {

            0 -> {
                PhotoUtil.fastNlMeansDenoising(bitmap, binding!!.ivContent, size)
            }
            1 -> {
                PhotoUtil.detailEnhance(bitmap, binding!!.ivContent, Range)
            }
            2 -> {
                PhotoUtil.decolor(bitmap, binding!!.ivContent)
            }

            3 -> {
                PhotoUtil.stylization(bitmap, binding!!.ivContent, style)
            }
            4-> {
                PhotoUtil.pencilSketch(bitmap, binding!!.ivContent)
            }
        }
    }

    var size = 1f
    var Range = 50f
    var style = 0f
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.bt_jz -> {

                Toast.makeText(this@PhotoProcessActivity,"131243213123",Toast.LENGTH_LONG).show()
                downImg(0)
                size += 1f
            }
            R.id.bt_sjzq -> {
                downImg(1)
                Range += 10f
                Toast.makeText(this@PhotoProcessActivity,"131243213123",Toast.LENGTH_SHORT).show()
            }
            R.id.bt_ts -> {
                downImg(2)
                Toast.makeText(this@PhotoProcessActivity,"131243213123",Toast.LENGTH_LONG).show()
            }
            R.id.bt_style -> {
                style += 10
                downImg(3)
                Toast.makeText(this@PhotoProcessActivity,"131243213123",Toast.LENGTH_SHORT).show()
            }
            R.id.bt_pencilSketch -> {
                downImg(4)
                Toast.makeText(this@PhotoProcessActivity,"131243213123",Toast.LENGTH_SHORT).show()
            }
        }
    }


}
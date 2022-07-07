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
import com.wkq.opencvdemo.databinding.ActivityFilterBinding
import com.wkq.opencvdemo.util.FilterUtil
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
 *@des
 *
 */

class FilterActivity : AppCompatActivity() ,View.OnClickListener{
    val path ="https://img2.baidu.com/it/u=686238839,986827545&fm=253&fmt=auto&app=138&f=JPEG"

    companion object {
        fun newInstance(mContext: Context) {
            var intent = Intent(mContext, FilterActivity::class.java)
            mContext.startActivity(intent)
        }
    }
    var binding :ActivityFilterBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding =DataBindingUtil.setContentView<ActivityFilterBinding>(this, R.layout.activity_filter)
        binding!!.onCLick=this
        initIcon()

    }

    private fun initIcon() {
        Glide.with(this).load(path).into(binding!!.ivInitial)
        Glide.with(this).load(path).into(binding!!.ivContent)
    }




    fun showStyle(type:Int){
        Observable.create<Bitmap> {
            var bm = Glide.with(this).asBitmap().load(path).submit().get()
            it.onNext(bm)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Bitmap> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Bitmap) {

                    showPhoto(type,t)
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    fun showPhoto(type:Int,bitmap: Bitmap){
        when(type){
            1 ->{
                FilterUtil.togray(bitmap,binding!!.ivContent)
            }
            2 ->{
                FilterUtil.cannyScan(bitmap,binding!!.ivContent)
            }
            3 ->{
                FilterUtil.threshold(bitmap,binding!!.ivContent)
            }
            4 ->{
                FilterUtil.sm(bitmap,binding!!.ivContent)
            }

            5 ->{
                FilterUtil.lhh(bitmap,binding!!.ivContent)
            }
            6 ->{
                FilterUtil.pz(bitmap,binding!!.ivContent)
            }
            7 ->{
                FilterUtil.rz(bitmap,binding!!.ivContent)
            }
            8 ->{
                FilterUtil.bd(bitmap,binding!!.ivContent)
            }
            9 ->{
                FilterUtil.fd(bitmap,binding!!.ivContent)
            }
            10->{
                FilterUtil.hj(bitmap,binding!!.ivContent)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.bt_reset ->{
                initIcon()
            }
            R.id.bt_hd ->{
                showStyle(1)
            }
            R.id.bt_lk ->{
                showStyle(2)
            }
            R.id.bt_ez ->{
                showStyle(3)
            }
            R.id.bt_sm ->{
                showStyle(4)
            }
            R.id.bt_lhh ->{
                showStyle(5)
            }
            R.id.bt_pz ->{
                showStyle(6)
            }
            R.id.bt_rz ->{
                showStyle(7)
            }
            R.id.bt_bd ->{
                showStyle(8)
            }
            R.id.bt_fd ->{
                showStyle(9)
            }
            R.id.bt_hj ->{
                showStyle(10)
            }
        }
    }


}
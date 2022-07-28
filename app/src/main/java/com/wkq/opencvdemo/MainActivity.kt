package com.wkq.opencvdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wkq.opencvdemo.activity.*
import com.wkq.opencvdemo.databinding.ActivityMainBinding
import com.wkq.opencvdemo.util.ImageUtil
import com.wkq.opencvdemo.util.PhotoUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.btHy.setOnClickListener {
            binding.ivContent.setImageResource(R.mipmap.ic_launcher)
        }

//       var bitmap= ImageUtil.getBitmap(this,R.mipmap.ic_launcher)
        binding.btGf.setOnClickListener {
            ImageUtil.differenceOfGaussian(
                ImageUtil.getBitmap(
                    this@MainActivity,
                    R.mipmap.ic_launcher
                )!!, binding.ivContent
            );
        }
        binding.btHd.setOnClickListener {
            ImageUtil.Togray(
                ImageUtil.getBitmap(this@MainActivity, R.mipmap.ic_launcher)!!,
                binding.ivContent
            );
        }
        binding.btFs.setOnClickListener {
            ImageUtil.Tocorrosion(
                ImageUtil.getBitmap(this@MainActivity, R.mipmap.ic_launcher)!!,
                binding.ivContent
            );
        }
        binding.btPz.setOnClickListener {
            ImageUtil.Todilate(
                ImageUtil.getBitmap(this@MainActivity, R.mipmap.ic_launcher)!!,
                binding.ivContent
            );
        }
        binding.btLb.setOnClickListener {
            ImageUtil.TomedianBlur(
                ImageUtil.getBitmap(this@MainActivity, R.mipmap.ic_launcher)!!,
                binding.ivContent
            );
        }
        binding.btGsmh.setOnClickListener {
            ImageUtil.ToGaussian(
                ImageUtil.getBitmap(this@MainActivity, R.mipmap.ic_launcher)!!,
                binding.ivContent
            );
        }
        binding.btByjc.setOnClickListener {
            ImageUtil.CannyScan(
                ImageUtil.getBitmap(this@MainActivity, R.mipmap.ic_launcher)!!,
                binding.ivContent
            );
        }
        binding.btLj.setOnClickListener {
            FilterActivity.newInstance(this)
        }
        binding.btLdb.setOnClickListener {
            BrightnessAndContrastActivity.newInstance(this)
        }
        var size=5f

        binding.btWz.setOnClickListener {
            PhotoProcessActivity.newInstance(this)
        }
        binding.btMo.setOnClickListener {
            BlurEffectActivity.newInstance(this)
        }
        binding.btText.setOnClickListener {
            ImageUtil.addChineseText(
                    ImageUtil.getBitmap(this@MainActivity, R.mipmap.ic_launcher)!!,
                    binding.ivContent
            );
        }

    }

}
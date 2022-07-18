package com.wkq.opencvdemo

import android.app.Application


/**
 * @author wkq
 *
 * @date 2022年07月18日 13:56
 *
 *@des
 *
 */

class OpennCvApplication:Application() {
    init {
        System.loadLibrary("opencv_java4")
    }


}
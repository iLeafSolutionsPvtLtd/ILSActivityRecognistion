package com.ileaf.activity_recoganition

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


class TestActivityRecoganition{

    companion object{


        fun startActivityService( context: Activity){
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION)==PackageManager.PERMISSION_GRANTED){
                Intent(context, DetectedActivityService::class.java).apply {
                   context.startService(this)
                }
            }else{
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),100)
            }

        }
    }
}
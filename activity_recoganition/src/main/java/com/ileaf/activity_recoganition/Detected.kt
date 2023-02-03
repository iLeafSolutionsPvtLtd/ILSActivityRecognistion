package com.ileaf.activity_recoganition

import TRANSITIONS_RECEIVER_ACTION
import TransitionsReceiver
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.google.android.gms.location.ActivityRecognition
import com.tmcuk.mobilityiq.tracking.requestActivityTransitionUpdates


const val ACTIVITY_UPDATES_INTERVAL = 500L
private const val MINIMAL_DISPLACEMENT = 2.0 // in
class DetectedActivityService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    private val transitionBroadcastReceiver: TransitionsReceiver = TransitionsReceiver().apply {
        action = {

//            setDetectedActivity(it)

        }
    }

    override fun onCreate() {
        super.onCreate()

        requestActivityUpdates()
        requestActivityTransitionUpdates()
        registerReceiver(transitionBroadcastReceiver, IntentFilter(TRANSITIONS_RECEIVER_ACTION))
    }

    @SuppressLint("MissingPermission")
    private fun requestActivityUpdates() {
        val task = ActivityRecognition.getClient(this).requestActivityUpdates(
            ACTIVITY_UPDATES_INTERVAL, DetectedActivityReceiver.getPendingIntent(this)
        )


        task.run {
            addOnSuccessListener {
                Log.d("ActivityUpdate", getString(R.string.activity_update_request_success))
            }
            addOnFailureListener {
                Log.d("ActivityUpdate", getString(R.string.activity_update_request_failed))
            }
        }
    }
}
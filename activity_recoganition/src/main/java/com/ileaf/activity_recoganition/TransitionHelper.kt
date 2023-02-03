import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.*
import com.ileaf.activity_recoganition.R


@SuppressLint("MissingPermission")
fun Service.requestActivityTransitionUpdates() {
  val request = ActivityTransitionRequest(getActivitiesToTrack())

    val intent = Intent(this, TransitionsReceiver::class.java)
    intent.action = TRANSITIONS_RECEIVER_ACTION

    val  pendingIntent =PendingIntent.getBroadcast(this,TRANSITION_PENDING_INTENT_REQUEST_CODE,intent,PendingIntent.FLAG_MUTABLE)
    val  task=ActivityRecognition.getClient(this).requestActivityTransitionUpdates(request,pendingIntent)

  task.run {
    addOnSuccessListener {
      Log.d("TransitionUpdate", getString(R.string.transition_update_request_success))
    }
    addOnFailureListener {
      Log.d("TransitionUpdate", getString(R.string.transition_update_request_failed))
    }
  }
}

@SuppressLint("MissingPermission")
fun Activity.removeActivityTransitionUpdates() {
    val task = ActivityRecognition.getClient(this).removeActivityTransitionUpdates(
        TransitionsReceiver.getPendingIntent(this))

    task.run {
        addOnSuccessListener {
            Log.d("TransitionUpdate", getString(R.string.transition_update_remove_success))
        }
        addOnFailureListener {
            Log.d("TransitionUpdate", getString(R.string.transition_update_remove_failed))
        }
    }
}
@SuppressLint("MissingPermission")
fun Service.removeActivityTransitionUpdates() {
    val task = ActivityRecognition.getClient(this).removeActivityTransitionUpdates(
        TransitionsReceiver.getPendingIntent(this))

    task.run {
        addOnSuccessListener {
            Log.d("TransitionUpdate", getString(R.string.transition_update_remove_success))
        }
        addOnFailureListener {
            Log.d("TransitionUpdate", getString(R.string.transition_update_remove_failed))
        }
    }
}


private fun getActivitiesToTrack(): List<ActivityTransition> =
    mutableListOf<ActivityTransition>()
        .apply {
          add(ActivityTransition.Builder()
              .setActivityType(DetectedActivity.STILL)
              .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
              .build())
          add(ActivityTransition.Builder()
              .setActivityType(DetectedActivity.STILL)
              .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
              .build())
          add(ActivityTransition.Builder()
              .setActivityType(DetectedActivity.WALKING)
              .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
              .build())
          add(ActivityTransition.Builder()
              .setActivityType(DetectedActivity.WALKING)
              .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
              .build())
            add(ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build())
            add(ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build())
            add(ActivityTransition.Builder()
                .setActivityType(DetectedActivity.ON_BICYCLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build())
            add(ActivityTransition.Builder()
                .setActivityType(DetectedActivity.ON_BICYCLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build())
        }
package com.ieeevit.enigma8.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.notification.NotificationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class NotificationViewModel:ViewModel() {
    private val _notifiactionStatus  = MutableLiveData<NotificationResponse>()
    val notificationStatus: LiveData<NotificationResponse>
        get() = _notifiactionStatus




    fun getNotificationDetails(authToken:String) {
        Api.retrofitService.getNotificationDetails(authToken).enqueue(object : Callback<NotificationResponse> {
            override fun onResponse(
                    call: Call<NotificationResponse>,
                    response: Response<NotificationResponse>
            ){
                if (response.body() != null) {
                    _notifiactionStatus.value = response.body()
                }
                Log.e("Room","Pass")
                Log.e("Response","$response")


            }


            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.e("com.ieeevit.enigma8.model.room.Room","Fail")
            }
        })
    }

}

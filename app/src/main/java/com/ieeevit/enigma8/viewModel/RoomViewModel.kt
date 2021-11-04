package com.ieeevit.enigma8.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.RoomResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class RoomViewModel:ViewModel() {
    private val _roomStatus  = MutableLiveData<RoomResponse>()
    val roomStatus: LiveData<RoomResponse>
        get() = _roomStatus


    fun getRoomDetails(authToken:String) {
        Api.retrofitService.getRoomDetails(authToken).enqueue(object : Callback<RoomResponse> {
            override fun onResponse(
                call: Call<RoomResponse>,
                response: Response<RoomResponse>
            ) {
                if (response.body() != null) {
                    _roomStatus.value = response.body()
                }
                Log.e("Room","Pass")
                Log.e("Response","$response")
                if(!response.isSuccessful) {
                    val gson =
                            Gson()
                    val type = object : TypeToken<RoomResponse>() {}.type
                    var errorResponse: RoomResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    Log.e("Response Error","$errorResponse")

                }

            }


            override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                Log.e("com.ieeevit.enigma8.model.Room","Fail")
            }
        })
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RoomViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

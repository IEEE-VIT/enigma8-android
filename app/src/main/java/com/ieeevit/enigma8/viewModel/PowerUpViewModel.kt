package com.ieeevit.enigma8.viewModel

import com.ieeevit.enigma8.model.PowerUpResponse
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

class PowerUpViewModel:ViewModel() {
    private val _powerupStatus  = MutableLiveData<com.ieeevit.enigma8.model.PowerUpResponse>()
    val powerupStatus: LiveData<com.ieeevit.enigma8.model.PowerUpResponse>
        get() = _powerupStatus


    fun getPowerupDetails(authToken:String) {
        Api.retrofitService.getPowerUpDeatils(authToken).enqueue(object : Callback<com.ieeevit.enigma8.model.PowerUpResponse> {
            override fun onResponse(
                call: Call<com.ieeevit.enigma8.model.PowerUpResponse>,
                response: Response<com.ieeevit.enigma8.model.PowerUpResponse>
            ) {
                if (response.body() != null) {
                    _powerupStatus.value = response.body()
                }
                Log.e("Powerup","Pass")
                Log.e("Response","$response")
                if(!response.isSuccessful) {
                    val gson =
                        Gson()
                    val type = object : TypeToken<RoomResponse>() {}.type
                    var errorResponse: RoomResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    Log.e("Response Error","$errorResponse")

                }

            }


            override fun onFailure(call: Call<com.ieeevit.enigma8.model.PowerUpResponse>, t: Throwable) {
                Log.e("com.ieeevit.enigma8.model.Room","Fail")
            }
        })
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PowerUpViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PowerUpViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

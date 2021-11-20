package com.ieeevit.enigma8.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.powerup.PowerUpResponse
import com.ieeevit.enigma8.model.powerup.PowerupRequest
import com.ieeevit.enigma8.model.powerup.SendPowerupResponse
import com.ieeevit.enigma8.model.room.RoomResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class PowerUpViewModel:ViewModel() {
    private val _powerupStatus  = MutableLiveData<PowerUpResponse>()
    val powerupStatus: LiveData<PowerUpResponse>
        get() = _powerupStatus
    private val _sPowerupResponse = MutableLiveData<SendPowerupResponse>()
    val sPowerupResponse: LiveData<SendPowerupResponse>
        get() = _sPowerupResponse





    fun sendPowerupDetails(authToken:String,powerupRequest: PowerupRequest) {
        Api.retrofitService.sendPowerupDetails(authToken,powerupRequest)
                .enqueue(object : Callback<SendPowerupResponse> {
                    override fun onResponse(
                            call: Call<SendPowerupResponse>,
                            response: Response<SendPowerupResponse>
                    ) {
                        if (response.body() != null) {
                            _sPowerupResponse.value = response.body()


                        }
                        if(!response.isSuccessful) {
                            val gson = Gson()
                            val type = object : TypeToken<SendPowerupResponse>() {}.type
                            val errorResponse: SendPowerupResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            val new = errorResponse?.data?.message
                            Log.e("Response Error","$errorResponse")
                        }
                        Log.e("Response",response.body().toString())
                        Log.e("Send","Success")
                        Log.e("Response","$response")

                    }

                    override fun onFailure(call: Call<SendPowerupResponse>, t: Throwable) {
                        Log.e("SendFail","Fail")
                    }

                })
    }


    fun getPowerupDetails(authToken:String) {
        Api.retrofitService.getPowerUpDeatils(authToken).enqueue(object : Callback<PowerUpResponse> {
            override fun onResponse(
                    call: Call<PowerUpResponse>,
                    response: Response<PowerUpResponse>
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


            override fun onFailure(call: Call<PowerUpResponse>, t: Throwable) {
                Log.e("com.ieeevit.enigma8.model.room.Room","Fail")
            }
        })
    }


}

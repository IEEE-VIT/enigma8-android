package com.ieeevit.enigma8.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.PowerupRequest
import com.ieeevit.enigma8.model.SendPowerupResponse
import com.ieeevit.enigma8.model.User
import com.ieeevit.enigma8.model.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendPowerupViewModel:ViewModel() {
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
                        Log.e("fa",_sPowerupResponse.value!!.data!!.message!!)

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

}
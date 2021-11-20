package com.ieeevit.enigma8.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.fcm.FcmRequest
import com.ieeevit.enigma8.model.fcm.FcmResponse
import com.ieeevit.enigma8.model.profile.User
import com.ieeevit.enigma8.model.profile.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileSetupViewModel(application: Application) :AndroidViewModel(application) {
    private val _userResponse = MutableLiveData<User>()
    val userResponse: LiveData<User>
        get() = _userResponse
    private val _fuserResponse = MutableLiveData<String>()
    val fuserResponse: LiveData<String>
        get() = _fuserResponse



    private val _fcmCode = MutableLiveData<FcmResponse>()
    val fcmCode: LiveData<FcmResponse>
        get() = _fcmCode

    fun sendFCMToken(authCode: String,fcmrequest: FcmRequest) {
        Api.retrofitService.sendFCMToken(authCode, fcmrequest)
                .enqueue(object : Callback<FcmResponse> {
                    override fun onResponse(
                            call: Call<FcmResponse>,
                            response: Response<FcmResponse>
                    ) {
                        if (response.body() != null) {
                            Log.e("AUthKEY", response.body().toString())
                            _fcmCode.value = response.body()
                            Log.e("new",_fcmCode.value.toString())

                        }
                        Log.e("Response","$response")
                    }

                    override fun onFailure(call: Call<FcmResponse>, t: Throwable) {
                        Log.e("Fail","${t.message}")
                    }
                })
    }





    fun sendUserDetails(authToken:String,userRequest: UserRequest) {
        Api.retrofitService.sendUserDetails(authToken,userRequest)
                .enqueue(object : Callback<User> {
                    override fun onResponse(
                            call: Call<User>,
                            response: Response<User>
                    ) {
                             if(response.body()!=null) {
                                 _userResponse.value = response.body()
                                 Log.e("UserResponse","${response.body()}")
                             }


                        if(!response.isSuccessful) {
                            val gson = Gson()
                            val type = object : TypeToken<User>() {}.type
                            val errorResponse: User? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            val new = errorResponse?.message
                            _fuserResponse.value = new
                            Log.e("new","$new")
                            Log.e("Response Error","$errorResponse")
                            Log.e("Response","$response")
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("SendFail","Fail")
                    }

                })
    }

}
package com.ieeevit.enigma8.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.User
import com.ieeevit.enigma8.model.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileSetupViewModel(application: Application) :AndroidViewModel(application) {
    private val _userResponse = MutableLiveData<User>()
    val userResponse: LiveData<User>
        get() = _userResponse



    fun sendUserDetails(authToken:String,userRequest: UserRequest) {
        Api.retrofitService.sendUserDetails(authToken,userRequest)
                .enqueue(object : Callback<User> {
                    override fun onResponse(
                            call: Call<User>,
                            response: Response<User>
                    ) {
                        if (response.body() != null) {
                            _userResponse.value = response.body()
                            Log.e("fa",_userResponse.value!!.data!!.message!!)

                        }
                        if(!response.isSuccessful) {
                            val gson = Gson()
                            val type = object : TypeToken<User>() {}.type
                            val errorResponse: User? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            val new = errorResponse?.data?.message
                            Log.e("Response Error","$errorResponse")
                        }
                        Log.e("Response",response.body().toString())
                        Log.e("Send","Success")
                        Log.e("Response","$response")

                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("SendFail","Fail")
                    }

                })
    }

}
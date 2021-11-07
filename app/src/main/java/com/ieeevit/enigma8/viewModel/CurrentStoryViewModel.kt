package com.ieeevit.enigma8.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.CurrentStory
import com.ieeevit.enigma8.model.User
import com.ieeevit.enigma8.model.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentStoryViewModel:ViewModel() {
    private val _storyResponse = MutableLiveData<CurrentStory>()
    val storyResponse: LiveData<CurrentStory>
        get() = _storyResponse



    fun getCurrentStoryDetails(roomid:String?,authToken:String) {
        Api.retrofitService.getCurrentStoryDeatils(roomid,authToken)
                .enqueue(object : Callback<CurrentStory> {
                    override fun onResponse(
                            call: Call<CurrentStory>,
                            response: Response<CurrentStory>
                    ) {
                        if (response.body() != null) {
                            _storyResponse.value = response.body()
                            Log.e("fa",_storyResponse.toString())

                        }
                        if(!response.isSuccessful) {
                            val gson = Gson()
                            val type = object : TypeToken<CurrentStory>() {}.type
                            val errorResponse: CurrentStory? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            val new = errorResponse?.data
                            Log.e("Response Error","$errorResponse")
                        }
                        Log.e("Response",response.body().toString())
                        Log.e("Send","Success")
                        Log.e("Response","$response")

                    }

                    override fun onFailure(call: Call<CurrentStory>, t: Throwable) {
                        Log.e("SendFail","Fail")
                    }

                })
    }

}
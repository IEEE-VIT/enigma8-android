package com.ieeevit.enigma8.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.FullStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullStoryViewModel:ViewModel() {
    private val _fullstoryResponse = MutableLiveData<FullStory>()
    val fullstoryResponse: LiveData<FullStory>
        get() = _fullstoryResponse



    fun getFullStoryDetails(roomid:String?,authToken:String) {
        Api.retrofitService.getFullStoryDetails(roomid,authToken)
            .enqueue(object : Callback<FullStory> {
                override fun onResponse(
                    call: Call<FullStory>,
                    response: Response<FullStory>
                ) {
                    if (response.body() != null) {
                        _fullstoryResponse.value = response.body()
                        Log.e("Fullstory",_fullstoryResponse.toString())

                    }
                    if(!response.isSuccessful) {
                        val gson = Gson()
                        val type = object : TypeToken<FullStory>() {}.type
                        val errorResponse: FullStory? = gson.fromJson(response.errorBody()!!.charStream(), type)
                        val new = errorResponse?.data
                        Log.e("Response Error","$errorResponse")
                    }
                    Log.e("Response",response.body().toString())
                    Log.e("Send","Success")
                    Log.e("Response","$response")

                }

                override fun onFailure(call: Call<FullStory>, t: Throwable) {
                    Log.e("SendFail","Fail")
                }

            })
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FullStoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FullStoryViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
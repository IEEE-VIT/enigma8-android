package com.ieeevit.enigma8.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.story.CurrentStory
import com.ieeevit.enigma8.model.room.RoomResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentStoryViewModel:ViewModel() {
    private val _storyResponse = MutableLiveData<CurrentStory>()
    val storyResponse: LiveData<CurrentStory>
        get() = _storyResponse
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
                Log.e("com.ieeevit.enigma8.model.room.Room","Fail")
            }
        })
    }


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
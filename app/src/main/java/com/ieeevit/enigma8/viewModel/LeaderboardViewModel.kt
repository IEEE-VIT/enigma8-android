package com.ieeevit.enigma8.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.countdown.StatusResponse
import com.ieeevit.enigma8.model.leaderboard.LeaderboardResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LeaderboardViewModel: ViewModel() {
    private val _leaderboardResponse = MutableLiveData<LeaderboardResponse>()
    val leaderboardResponse: LiveData<LeaderboardResponse>
        get() = _leaderboardResponse


    private val _eleaderboardResponse = MutableLiveData<String>()
    val eleaderboardResponse: LiveData<String>
        get() = _eleaderboardResponse

    private val _enigmaStatus = MutableLiveData<StatusResponse>()
    val enigmaStatus: LiveData<StatusResponse>
        get() = _enigmaStatus
    private val error = MutableLiveData<Int>()
    private val callStatus = MutableLiveData<Int>()

    init {
        error.value = 0
    }

    fun getEnigmaStatus(authToken:String) {
        Api.retrofitService.getEnigmaStatus(authToken).enqueue(object : Callback<StatusResponse> {
            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                if (response.body() != null) {
                    _enigmaStatus.value = response.body()
                    callStatus.value = response.code()
                } else {
                    error.value = 1
                }
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                error.value = 1
            }
        })
    }

    fun getLeaderboardDetails(page:Int?, perpage:Int?, authToken:String, quer: String?){
            Api.retrofitService.getLeaderboardDetails(page,perpage,authToken,quer)
                .enqueue(object : Callback<LeaderboardResponse>{
                    override fun onResponse(
                        call: Call<LeaderboardResponse>,
                        response: Response<LeaderboardResponse>
                    ) {
                        if (response.body() != null) {
                            _leaderboardResponse.value = response.body()
                            Log.e("fa",_leaderboardResponse.toString())

                        }
                        if(!response.isSuccessful) {
                            val gson = Gson()
                            val type = object : TypeToken<LeaderboardResponse>() {}.type
                            val errorResponse: LeaderboardResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)

                            val new = errorResponse?.message
                            _eleaderboardResponse.value = new

                            Log.e("Response Error","$errorResponse")
                        }
                        Log.e("Response",response.body().toString())
                        Log.e("Send","Success")
                        Log.e("Response","$response")

                    }

                    override fun onFailure(call: Call<LeaderboardResponse>, t: Throwable) {
                        Log.e("SendFail","Response Failed to parse")
                    }

                })
        }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LeaderboardViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}
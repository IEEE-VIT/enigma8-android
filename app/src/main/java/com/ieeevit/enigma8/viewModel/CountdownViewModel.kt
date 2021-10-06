package com.ieeevit.enigma8.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.StatusResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountdownViewModel : ViewModel() {
    private val _enigmaStatus = MutableLiveData<StatusResponse>()
    val enigmaStatus: LiveData<StatusResponse>
        get() = _enigmaStatus
    private val error = MutableLiveData<Int>()
    private val callStatus = MutableLiveData<Int>()

    init {
        error.value = 0
    }

    fun getEnigmaStatus(authCode: String) {
        Api.retrofitService.getEnigmaStatus(authCode).enqueue(object : Callback<StatusResponse> {
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

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CountdownViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CountdownViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
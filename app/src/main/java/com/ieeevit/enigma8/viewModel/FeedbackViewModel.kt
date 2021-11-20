package com.ieeevit.enigma8.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.feedback.Feedback
import com.ieeevit.enigma8.model.feedback.FeedbackRequest
import com.ieeevit.enigma8.model.profile.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackViewModel:ViewModel()  {
    private val _FeedbackResponse = MutableLiveData<Feedback>()
    val FeedbackResponse: LiveData<Feedback>
        get() = _FeedbackResponse



    fun sendFeedbackDetails(authToken:String,feedbackRequest: FeedbackRequest) {
        Api.retrofitService.sendFeedbackDetails(authToken,feedbackRequest)
                .enqueue(object : Callback<Feedback> {
                    override fun onResponse(
                            call: Call<Feedback>,
                            response: Response<Feedback>
                    ) {
                        if (response.body() != null) {
                            _FeedbackResponse.value = response.body()
                            Log.e("fa",_FeedbackResponse.value!!.data!!.message!!)

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

                    override fun onFailure(call: Call<Feedback>, t: Throwable) {
                        Log.e("SendFail","Fail")
                    }

                })
    }

}
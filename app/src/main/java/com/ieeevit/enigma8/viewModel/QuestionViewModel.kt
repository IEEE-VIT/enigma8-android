package com.ieeevit.enigma8.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.question.*
import com.ieeevit.enigma8.model.room.CheckResponse
import com.ieeevit.enigma8.model.room.RoomResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionViewModel:ViewModel() {
    private val _questionResponse = MutableLiveData<QuestionResponse>()
    val questionResponse: LiveData<QuestionResponse>
        get() = _questionResponse
    private val _submitResponse = MutableLiveData<SubmitResponse>()
    val submitResponse: LiveData<SubmitResponse>
        get() = _submitResponse
    private val _hintResponse = MutableLiveData<HintResponse>()
    val hintResponse: LiveData<HintResponse>
        get() = _hintResponse
    private val _checkroomResponse = MutableLiveData<CheckResponse>()
    val checkResponse: LiveData<CheckResponse>
        get() = _checkroomResponse
    private val _questionNo = MutableLiveData<Int>()
    val questionNo: LiveData<Int>
        get() = _questionNo
    private val _roomStatus  = MutableLiveData<RoomResponse>()
    val roomStatus: LiveData<RoomResponse>
        get() = _roomStatus
    private val _uPowerupResponse = MutableLiveData<UsePowerupResponse>()
    val upowerupResponse: LiveData<UsePowerupResponse>
        get() = _uPowerupResponse



    fun getUsePowerupDeatils(roomid:String?,authToken:String) {
        Api.retrofitService.getUsePowerupDetails(roomid,authToken).enqueue(object : Callback<UsePowerupResponse> {
            override fun onResponse(
                    call: Call<UsePowerupResponse>,
                    response: Response<UsePowerupResponse>
            ) {
                if (response.body() != null) {
                    _uPowerupResponse.value = response.body()
                }
                Log.e("Powerup","Pass")
                Log.e("Response","$response")


            }


            override fun onFailure(call: Call<UsePowerupResponse>, t: Throwable) {
                Log.e("com.ieeevit.enigma8.model.room.Room","Fail")
            }
        })
    }







    fun getQuestionNo(questionNo:Int) {
        _questionNo.value=questionNo
    }



    fun sendQuestionDetails(authToken:String,submitRequest: SubmitRequest) {
        Api.retrofitService.sendQuestionDetails(authToken,submitRequest)
                .enqueue(object : Callback<SubmitResponse> {
                    override fun onResponse(
                            call: Call<SubmitResponse>,
                            response: Response<SubmitResponse>
                    ){
                        if (response.body() != null) {
                            _submitResponse.value = response.body()
                            Log.e("fa","$_submitResponse")

                        }

                    }

                    override fun onFailure(call: Call<SubmitResponse>, t: Throwable) {
                        Log.e("SendFail","Fail")
                    }

                })
    }
    fun getHintDetails(roomid:String?,authToken:String) {
        Api.retrofitService.getHintDeatils(roomid,authToken)
                .enqueue(object : Callback<HintResponse> {
                    override fun onResponse(
                            call: Call<HintResponse>,
                            response: Response<HintResponse>
                    ) {
                        if (response.body() != null) {
                            _hintResponse.value = response.body()
                            Log.e("fa",_hintResponse.toString())

                        }
                        if(!response.isSuccessful) {
                            val gson = Gson()
                            val type = object : TypeToken<HintResponse>() {}.type
                            val errorResponse: HintResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            val new = errorResponse?.data
                            Log.e("Response Error","$errorResponse")
                        }
                        Log.e("Response",response.body().toString())
                        Log.e("Send","Success")
                        Log.e("Response","$response")

                    }

                    override fun onFailure(call: Call<HintResponse>, t: Throwable) {
                        Log.e("SendFail","Fail")
                    }

                })
    }


    fun getQuestionDetails(roomid:String?,authToken:String) {
        Api.retrofitService.getQuestionDeatils(roomid,authToken)
                .enqueue(object : Callback<QuestionResponse> {
                    override fun onResponse(
                            call: Call<QuestionResponse>,
                            response: Response<QuestionResponse>
                    ) {
                        if (response.body() != null) {
                            _questionResponse.value = response.body()
                            Log.e("fa",_questionResponse.toString())

                        }
                        if(!response.isSuccessful) {
                            val gson = Gson()
                            val type = object : TypeToken<QuestionResponse>() {}.type
                            val errorResponse: QuestionResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            val new = errorResponse?.data
                            Log.e("Response Error","$errorResponse")
                        }
                        Log.e("Response",response.body().toString())
                        Log.e("Send","Success")
                        Log.e("Response","$response")

                    }

                    override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {
                        Log.e("SendFail","Fail")
                    }

                })
    }

}
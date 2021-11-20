package com.ieeevit.enigma8.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.*
import com.ieeevit.enigma8.model.room.RoomResponse
import com.ieeevit.enigma8.model.story.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel:ViewModel() {
    private val _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse>
        get() = _profileResponse

    private val _roomStatus  = MutableLiveData<RoomResponse>()
    val roomStatus: LiveData<RoomResponse>
        get() = _roomStatus

    private val clientId: String =
        "485828193401-acfdftfgnbsvpk6du4m4ogqjs62dbvc5.apps.googleusercontent.com"

    val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestScopes(Scope(Scopes.EMAIL))
        .requestIdToken(clientId)
        .requestEmail()
        .build()

    fun getProfileDetails(authToken:String) {
        Api.retrofitService.getProfileDetails(authToken)
            .enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.body() != null) {
                        _profileResponse.value = response.body()
                        Log.e("profileresponse :",_profileResponse.toString())

                    }
                    if(!response.isSuccessful) {
                        val gson = Gson()
                        val type = object : TypeToken<ProfileResponse>() {}.type
                        val errorResponse: ProfileResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                        val new = errorResponse?.data
                        Log.e("Response Error","$errorResponse")
                    }
                    Log.e("Response",response.body().toString())
                    Log.e("Send","Success")
                    Log.e("Response","$response")

                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Log.e("SendFail","Fail")
                }

            })
    }

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
                    val gson = Gson()
                    val type = object : TypeToken<RoomResponse>() {}.type
                    var errorResponse: RoomResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    Log.e("Response Error","$errorResponse")

                }

            }


            override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                Log.e("com.ieeevit.enigma8.model.Room","Fail")
            }
        })
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
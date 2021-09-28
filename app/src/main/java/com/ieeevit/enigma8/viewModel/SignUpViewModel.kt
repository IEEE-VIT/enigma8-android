package com.ieeevit.enigma8.viewModel
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.AccessTokenResponse
import com.ieeevit.enigma8.model.AuthRequest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class SignUpViewModel : ViewModel() {
    private val _authStatus = MutableLiveData<Int>()
    val authStatus: LiveData<Int>
        get() = _authStatus
    private val _authCode = MutableLiveData<String>()
    val authCode: LiveData<String>
        get() = _authCode
    private val _userStatus = MutableLiveData<Boolean>()
    val userStatus: LiveData<Boolean>
        get() = _userStatus
    private val clientId: String =
        "485828193401-acfdftfgnbsvpk6du4m4ogqjs62dbvc5.apps.googleusercontent.com"

    init {
        _authStatus.value = 3   // 0:fail 1:success
        _authCode.value = null
        _userStatus.value = null
    }

    val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestScopes(Scope(Scopes.EMAIL))
        .requestIdToken(clientId)
        .requestEmail()
        .build()





    fun getAuthCode(code: String) {

        Api.retrofitService.getAccessToken(AuthRequest(code))
            .enqueue(object : Callback<AccessTokenResponse> {
                override fun onResponse(
                    call: Call<AccessTokenResponse>,
                    response: Response<AccessTokenResponse>
                ) {
                    if (response.body() != null) {
                        Log.i("AUthKEY", response.body().toString())
                        val result: AccessTokenResponse? = response.body()
                        _authCode.value = result?.success.toString()
//                        _userStatus.value = result?.usernameExist
                        _authStatus.value = 1
                        Log.e("success",result!!.data.JWT.jwt.toString())
                    }
                }

                override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                    _authStatus.value = 0
                    Log.e("Fail","${t.message}")
                }
            })
    }



    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SignUpViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
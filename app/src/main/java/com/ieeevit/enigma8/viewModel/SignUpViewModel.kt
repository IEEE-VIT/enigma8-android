package com.ieeevit.enigma8.viewModel
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.work.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.ieeevit.enigma8.MainActivity
import com.ieeevit.enigma8.api_service.Api
import com.ieeevit.enigma8.model.AccessTokenResponse
import com.ieeevit.enigma8.model.AuthRequest
import com.ieeevit.enigma8.utils.PrefManager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class SignUpViewModel(application: Application) : AndroidViewModel(application){

    val sharedPreference = PrefManager(getApplication())


    private val _authCode = MutableLiveData<AccessTokenResponse>()
    val authCode: LiveData<AccessTokenResponse>
        get() = _authCode

    private val clientId: String =
        "485828193401-acfdftfgnbsvpk6du4m4ogqjs62dbvc5.apps.googleusercontent.com"

    init {
        _authCode.value = null
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
                        Log.e("AUthKEY", response.body().toString())
                        _authCode.value = response.body()
                        Log.e("new",_authCode.value.toString())
                        sharedPreference.setAuthCode(_authCode.value!!.data.JWT)



                    }
                    Log.e("Response","$response")
                }

                override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                    Log.e("Fail","${t.message}")
                }
            })
    }


}
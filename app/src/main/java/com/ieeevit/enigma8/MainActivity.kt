package com.ieeevit.enigma8


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.ieeevit.enigma8.viewModel.SignUpViewModel
import org.jetbrains.annotations.Nullable


@Suppress("DEPRECATION")
class MainActivity :AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 0
    private lateinit var g_button: Button
    private lateinit var viewModel: SignUpViewModel



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        g_button = findViewById(R.id.google)




        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        mGoogleSignInClient = GoogleSignIn.getClient(this, viewModel.gso)


//                viewModel.getUser().observe(this, object : Observer<User?>() {
//                    fun onChanged(@Nullable data: User?) {
//                        // update ui.
//                    }
//                })
        g_button.setOnClickListener {
            signIn()

        }


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->

            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
//            val msg = getString(R.string., token)
            Log.d(TAG,token.toString())
//            Toast.makeText(baseContext, token.toString(), Toast.LENGTH_SHORT).show()
        })


    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount?>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val authToken = account?.idToken

            viewModel.getAuthCode(authToken.toString())
//            Toast.makeText(this,"Sign in",Toast.LENGTH_LONG).show()
            Log.e("AuthCode",authToken!!)
            Toast.makeText(this,"Sign in sucessful",Toast.LENGTH_LONG).show()

//            viewModel.authCode.observe(this,{
//
////                Log.e("result",it)
//
//            })


//            val intent = Intent(this,CountdownActivity::class.java)
//            startActivity(intent)
//            finish()

        } catch (e: ApiException) {
           Toast.makeText(this,"Sign In Failed",Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)

        }
    }



}

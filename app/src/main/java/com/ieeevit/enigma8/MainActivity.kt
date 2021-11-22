package com.ieeevit.enigma8


import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.onboarding.OnboardingActivity
import com.ieeevit.enigma8.view.profile.ProfileActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.view.story.CharacterActivity
import com.ieeevit.enigma8.view.timer.CountdownActivity
import com.ieeevit.enigma8.viewModel.SignUpViewModel


@Suppress("DEPRECATION")
class MainActivity :AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 0
    private lateinit var sharedPreference: PrefManager
    private lateinit var g_button: Button
    private lateinit var viewModel: SignUpViewModel




    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        g_button = findViewById(R.id.google)
        sharedPreference = PrefManager(this)
        FirebaseApp.initializeApp(applicationContext)

        val text1 : TextView = findViewById(R.id.heading)


        val paint1 = text1.paint
        val shader1 : Shader = LinearGradient(0f, 0f,0f,text1.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f), Shader.TileMode.REPEAT)
        paint1.shader = shader1



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

            viewModel.authCode.observe(this,{
                if(it!=null) {
                    sharedPreference.setAuthCode(it.data.JWT)

                    if(it.data.isNew) {
                        sharedPreference.setCount(1)
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    else {
                        val intent = Intent(this,RoomsActvity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            })

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->

            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            // Log and toast
//            val msg = getString(R.string., token)
            Log.e("Fcmtoken","$token")
            sharedPreference.setFcm(token.toString())


//            Toast.makeText(baseContext, token.toString(), Toast.LENGTH_SHORT).show()
        })


    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

//        sharedPreference.setisNew(1)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.addOnSuccessListener {
                Log.e("token","${it.idToken}")
                viewModel.getAuthCode(it.idToken.toString())

            }.addOnFailureListener{
                Toast.makeText(this,"Sign In Failed",Toast.LENGTH_LONG).show()
                Log.e("fail","Failed")

            }
//            handleSignInResult(task)

        }
    }


//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount?>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            val authToken = account?.idToken
//
//
//
//            Toast.makeText(this,"Sign in",Toast.LENGTH_LONG).show()
////            Log.e("AuthCode",sharedPreference.getAuthCode().toString())
//
//        } catch (e: ApiException) {
//
//        }
//    }



}

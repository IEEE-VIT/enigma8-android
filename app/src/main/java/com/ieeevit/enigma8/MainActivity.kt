package com.ieeevit.enigma8


import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.profile.ProfileActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.view.timer.CountdownActivity
import com.ieeevit.enigma8.viewModel.CountdownViewModel
import com.ieeevit.enigma8.viewModel.ProfileViewModel
import com.ieeevit.enigma8.viewModel.SignUpViewModel


@Suppress("DEPRECATION")
class MainActivity :AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 0
    private lateinit var sharedPreference: PrefManager
    private lateinit var g_button: ConstraintLayout
    private lateinit var s_button: Button
    private lateinit var viewModel: SignUpViewModel
    private lateinit var text_change:TextView

    private lateinit var pViewModel:ProfileViewModel
    private lateinit var cViewModel: CountdownViewModel
    private lateinit var icon:ImageView




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        cViewModel = ViewModelProvider(this).get(CountdownViewModel::class.java)
        if(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable){
            val view = View.inflate(this, R.layout.connection_error, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.0f
            dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.window!!.attributes = lp
            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            dialog.show()
            view.findViewById<Button>(R.id.try_again).setOnClickListener(View.OnClickListener {
                recreate()

        })
        }
        g_button = findViewById(R.id.google)
        icon =findViewById(R.id.icon_google)
        sharedPreference = PrefManager(this)

        FirebaseApp.initializeApp(applicationContext)

        val text1 : TextView = findViewById(R.id.heading)


        val paint1 = text1.paint
        val shader1 : Shader = LinearGradient(0f, 0f, 0f, text1.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f, 0.7f), Shader.TileMode.REPEAT)
        paint1.shader = shader1
        text_change = findViewById(R.id.text_change)



        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        pViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        mGoogleSignInClient = GoogleSignIn.getClient(this, viewModel.gso)
        if(sharedPreference.getCount() == 1) {
            text_change.text = "Start The Cryptic Hunt"
            icon.visibility = View.GONE
        }


        g_button.setOnClickListener {
            signIn()
        }

            viewModel.authCode.observe(this, {
                if (it != null) {
                    sharedPreference.setAuthCode(it.data.JWT)
                    cViewModel.getEnigmaStatus("Bearer ${it.data.JWT}")
                    if (it.data.isNew) {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        sharedPreference.setCount(1)
                        cViewModel.enigmaStatus.observe(this,{
                            if(it.data.enigmaStarted == true) {
                                val intent = Intent(this,RoomsActvity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else if(it.data.enigmaStarted == false) {
                                val intent = Intent(this,CountdownActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })

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
            Log.e("Fcmtoken", "$token")
            sharedPreference.setFcm(token.toString())


//            Toast.makeText(baseContext, token.toString(), Toast.LENGTH_SHORT).show()
        })


    }



    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.addOnSuccessListener {

                Log.e("token", "${it.idToken}")
                viewModel.getAuthCode(it.idToken.toString())

            }.addOnFailureListener{
//                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_LONG).show()
                Log.e("fail", "Failed")

            }

        }


    }






}

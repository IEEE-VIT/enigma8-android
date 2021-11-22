package com.ieeevit.enigma8.view.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.fcm.FcmRequest
import com.ieeevit.enigma8.model.profile.UserRequest
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.timer.CountdownActivity
import com.ieeevit.enigma8.viewModel.ProfileSetupViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern


class ProfileActivity:AppCompatActivity() {
    private lateinit var sharedPreference: PrefManager

    lateinit var uname: EditText
    lateinit var nextButton: Button
    lateinit var autoCompletePlatform: AutoCompleteTextView
    lateinit var tabHeading : TextView
    lateinit var viewModel: ProfileSetupViewModel
    var isCollegeStudent: Boolean = false
    var outreach: String = ""
    lateinit var enterusername: TextView
    lateinit var option:TextView
    private lateinit var relative:RelativeLayout
    lateinit var end:ImageView
    var message = ""


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        if(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable){
            val view = View.inflate(this, R.layout.connection_error, null)
            val builder = android.app.AlertDialog.Builder(this)
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

        sharedPreference = PrefManager(this)
        enterusername = findViewById(R.id.enter_username)

        val heading : TextView = findViewById(R.id.setup)
        tabHeading = findViewById(R.id.tabHeading)
        val text2 : TextView = findViewById(R.id.how_did_you)
        val shader1 : Shader = LinearGradient(0f, 0f, 0f, heading.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
        heading.paint.shader = shader1
        val shader3 : Shader = LinearGradient(0f, 0f, 0f, text2.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
        text2.paint.shader = shader3
        val shader2 : Shader = LinearGradient(0f, 0f, 0f, tabHeading.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
        tabHeading.paint.shader = shader2

        uname = findViewById(R.id.username)

//        end = findViewById(R.id.end_icon)
        autoCompletePlatform = findViewById(R.id.option)
        autoCompletePlatform.setDropDownBackgroundResource(R.color.black)
        nextButton = findViewById(R.id.submit)
        viewModel = ViewModelProvider(this).get(ProfileSetupViewModel::class.java)
        val nooutreach = findViewById<TextView>(R.id.nooutreach)
        val minimum = findViewById<TextView>(R.id.minimum_characters)
        val authCode: String? = sharedPreference.getAuthCode()
        val notAvailable  = findViewById<TextView>(R.id.notAvailable)
        val token = sharedPreference.getFcm()
        relative = findViewById(R.id.relative)
        Log.e("Fcmtoken", "$token")
        val fcmRequest = FcmRequest(token.toString(), "android")


        uname.doOnTextChanged { text, start, before, count ->
            minimum.visibility = View.GONE
            notAvailable.visibility = View.GONE
        }


        val items = resources.getStringArray(R.array.platform)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, items)
        autoCompletePlatform.setAdapter(arrayAdapter)
        autoCompletePlatform.setOnItemClickListener { _, _, position, _ ->
            outreach = arrayAdapter.getItem(position) ?: ""
            nooutreach.visibility = View.INVISIBLE

        }






        uname.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)

            }
        })
        val regex = "^[a-zA-Z0-9]+$"
        val special: Pattern = Pattern.compile(regex)


        nextButton.setOnClickListener() {
            val username = uname.text.toString().trim()
            val hasSpecial: Matcher = special.matcher(username)
            when {
                TextUtils.isEmpty(username) -> {
                    minimum.text = "This field cannot be empty"
                    minimum.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                TextUtils.isEmpty(outreach)->{
                    nooutreach.visibility = View.VISIBLE
                    minimum.visibility = View.INVISIBLE
                    return@setOnClickListener
                }
                !hasSpecial.find() -> {
                    minimum.text = "*No special character"
                    minimum.visibility = View.VISIBLE

                }

                username.length < 8 -> {
                    minimum.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                else-> {
                    val userRequest = UserRequest(
                            username, outreach
                    )
                    viewModel.sendUserDetails("Bearer ${authCode.toString()}", userRequest)
                    viewModel.sendFCMToken("Bearer ${authCode.toString()}", fcmRequest)
                    viewModel.fcmCode.observe(this, {
                        if (it != null) {
                            Log.e("Fcmresponse", "$it")

                        }

                    })



                }
            }
            Log.e("U", username)
            Log.e("I", isCollegeStudent.toString())
            Log.e("O", outreach)


        }
        viewModel.fuserResponse.observe(this, {
            if (it == "username not unique") {
                notAvailable.visibility = View.VISIBLE
                Log.e("Hello", "Hello")

            }

        })
        viewModel.userResponse.observe(this, {
            Log.e("UserResponse", "$it")
            Log.e("messag", "${it.message}")
            if (it.message == "username not unique") {
                notAvailable.visibility = View.VISIBLE
                Log.e("Hello", "Hello")

            } else if (it.success == true) {
                sharedPreference.setCount(2)
                sharedPreference.setisNew(2)
                val intent = Intent(this, CountdownActivity::class.java)
                startActivity(intent)
                finish()
            }
        })





    }
    fun TextFieldClicked(view: View) {
        if (view.id == R.id.username);
        uname.setText("")
    }


    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}


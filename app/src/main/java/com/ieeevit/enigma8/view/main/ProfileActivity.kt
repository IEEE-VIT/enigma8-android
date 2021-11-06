package com.ieeevit.enigma8.view.main

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.UserRequest
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.viewModel.ProfileSetupViewModel

class ProfileActivity:AppCompatActivity() {
    private lateinit var sharedPreference: PrefManager
    lateinit var check1: CheckBox
    lateinit var check2: CheckBox
    lateinit var uname: EditText
    lateinit var nextButton: Button
    var usernameExist:Boolean = false
    lateinit var autoCompletePlatform: AutoCompleteTextView
    lateinit var viewModel: ProfileSetupViewModel
    var isCollegeStudent: Boolean = false
    var outreach: String = ""
    var platformPos: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedPreference = PrefManager(this)

        check1 = findViewById(R.id.checkBoxY)
        check2 = findViewById(R.id.checkBoxN)
        uname = findViewById(R.id.username)
        autoCompletePlatform = findViewById(R.id.option)
        nextButton = findViewById(R.id.nxt_bt)
        viewModel = ViewModelProvider(this).get(ProfileSetupViewModel::class.java)


        val items = resources.getStringArray(R.array.platform)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, items)
        autoCompletePlatform.setAdapter(arrayAdapter)




        check1.setOnClickListener() {
            check1.isChecked = true
            check2.isChecked = false
            isCollegeStudent = true


        }
        check2.setOnClickListener() {
            check2.isChecked = true
            check1.isChecked = false
            isCollegeStudent = false
        }
        autoCompletePlatform.setOnItemClickListener { _, _, position, _ ->
            outreach = arrayAdapter.getItem(position) ?: ""

        }

        nextButton.setOnClickListener() {
            val username = uname.text.toString().trim()


            when {
                TextUtils.isEmpty(username) -> {
                    uname.error = "Enter User Name"
                    return@setOnClickListener
                }

                username.length < 8 -> {
                    uname.error = "Username should contain minimum 8 characters"
                    return@setOnClickListener
                }
                else -> {
                    val userRequest = UserRequest(
                            username,outreach
                    )
                    val authCode: String? = sharedPreference.getAuthCode()

                    viewModel.sendUserDetails( "Bearer ${authCode.toString()}", userRequest)
                    viewModel.userResponse.observe(this,{
                        Log.e("Ma",it.data!!.message!!)
                if(it.data.message=="username not unique") {
                    Log.e("Wohoo","This username is already taken.Try another")
                }
                else {
                    Log.e("hello","yOU failed")
                }
                    })

                }
            }
            Log.e("U",username)
            Log.e("I",isCollegeStudent.toString())
            Log.e("O",outreach)



        }

    }

    }


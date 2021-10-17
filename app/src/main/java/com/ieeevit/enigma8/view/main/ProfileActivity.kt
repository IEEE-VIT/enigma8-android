package com.ieeevit.enigma8.view.main

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.ieeevit.enigma8.R

class ProfileActivity:AppCompatActivity() {

    lateinit var check1:CheckBox
    lateinit var check2:CheckBox
    lateinit var uname:EditText
    lateinit var autoCompletePlatform:AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        check1=findViewById(R.id.checkBoxY)
        check2=findViewById(R.id.checkBoxN)
        uname=findViewById(R.id.username)
        autoCompletePlatform=findViewById(R.id.option)
        val items = resources.getStringArray(R.array.platform)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item,items)
        autoCompletePlatform.setAdapter(arrayAdapter)



        check1.setOnClickListener() {
            check1.isChecked=true
            check2.isChecked=false
        }
        check2.setOnClickListener() {
            check2.isChecked=true
            check1.isChecked=false
        }
        if(uname.length() <8){
            uname.error="*minimum 8 characters"

        }






    }
}
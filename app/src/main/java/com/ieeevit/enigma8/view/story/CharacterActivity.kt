package com.ieeevit.enigma8.view.story

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.Full_Story
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.viewModel.FullStoryViewModel





class CharacterActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc_character)
        sharedPreferences = PrefManager(this)

        var count = 0

        var viewModel: FullStoryViewModel = ViewModelProvider(this).get(FullStoryViewModel::class.java)
        var dataList: MutableList<Full_Story> = mutableListOf()
        val conti = findViewById<Button>(R.id.contin)

        var name = findViewById<TextView>(R.id.name_char)
        val authToken = sharedPreferences.getAuthCode()
        var desc = findViewById<TextView>(R.id.charac_card)

        val sharedPreferences: PrefManager = PrefManager(this)

        viewModel.getFullStoryDetails(
            sharedPreferences.getRoomid().toString(),
            "Bearer $authToken"
        )
        viewModel.fullstoryResponse.observe(this, {
            dataList.clear()
            if (it != null) {
                for (item in it.data.story) {
                    if (item.roomNo == 0 ) {

                        dataList.add(Full_Story(item.roomNo,item.sender, item.message))
                    }
                }

                Log.e("dataList","$dataList")


            }

            name.text = dataList[0].sender
            desc.text = dataList[0].message
            Log.e("mssg","$dataList")

            conti.setOnClickListener {
                if (count == dataList.size-1) {
                    val intent = Intent(this, StoryActivity::class.java)
                    startActivity(intent)
                }
                count++


                name.text = dataList[count].sender
                desc.text = dataList[count].message


            }

                Log.e("StoryResponse", "$it")

        })


    }


}
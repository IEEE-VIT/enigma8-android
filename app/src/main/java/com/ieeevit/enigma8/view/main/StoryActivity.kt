package com.ieeevit.enigma8.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.PowerupAdapter
import com.ieeevit.enigma8.adapter.RoomsAdapter
import com.ieeevit.enigma8.adapter.StoryAdapter
import com.ieeevit.enigma8.model.RoomsOuter
import com.ieeevit.enigma8.model.Story
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.viewModel.CurrentStoryViewModel
import com.ieeevit.enigma8.viewModel.ProfileSetupViewModel

class StoryActivity:AppCompatActivity() {
    private lateinit var sharedPreferences: PrefManager
    private lateinit var adapter: StoryAdapter
    private lateinit var storyView: RecyclerView
    lateinit var viewModel: CurrentStoryViewModel
    lateinit var nxt_btn: Button
    lateinit var continue_btn: Button
    var count:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        sharedPreferences = PrefManager(this)
        viewModel = ViewModelProvider(this).get(CurrentStoryViewModel::class.java)
        storyView = findViewById(R.id.recycle)
        nxt_btn = findViewById(R.id.next)
        continue_btn = findViewById(R.id.conti)
        var dataList : MutableList<Story> = mutableListOf()
        var DataList : MutableList<Story> = mutableListOf()


        viewModel.getCurrentStoryDetails(sharedPreferences.getRoomid().toString(),"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFuczI5aHVsQGdtYWlsLmNvbSIsImlhdCI6MTYzNDQ4NDU1MX0.GS_wtFw-bMjAM_50VhfmJAb-aq2ReHMXrALAPcTOxYQ")
        viewModel.storyResponse.observe(this,{
            if(it!=null) {
                for(item in it.data.story) {
                    dataList.add(Story(item.roomNo,item.sender,item.message))
                }
                DataList.add(Story(dataList[0].roomNo,dataList[0].sender,dataList[0].message))
                adapter = StoryAdapter(this, DataList)
                storyView.layoutManager = LinearLayoutManager(this)
                storyView.adapter = adapter
                adapter.notifyDataSetChanged()
                nxt_btn.setOnClickListener {

                        DataList.add(Story(dataList[count].roomNo,dataList[count].sender,dataList[count].message))
                        adapter = StoryAdapter(this, DataList)
                        storyView.layoutManager = LinearLayoutManager(this)
                        storyView.adapter = adapter
                        adapter.notifyDataSetChanged()
                        count++
                        if(count==dataList.size) {
                            nxt_btn.setVisibility(View.INVISIBLE)
                            continue_btn.setVisibility(View.VISIBLE)
                        }
                }
                continue_btn.setOnClickListener {
                    val intent = Intent(this,ProfileActivity::class.java)
                    startActivity(intent)
                }









            }
            Log.e("StoryResponse","$it")

        })

    }
}
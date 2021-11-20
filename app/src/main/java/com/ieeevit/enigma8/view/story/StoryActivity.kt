package com.ieeevit.enigma8.view.story

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.StoryAdapter
import com.ieeevit.enigma8.model.question.QuestionList
import com.ieeevit.enigma8.model.story.Story
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.question.QuestionActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.viewModel.CurrentStoryViewModel

class StoryActivity:AppCompatActivity() {
    private lateinit var sharedPreferences: PrefManager
    private lateinit var adapter: StoryAdapter
    private lateinit var storyView: RecyclerView
    private lateinit var back: ImageView
    private lateinit var instruction: ImageView
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
        val authToken = sharedPreferences.getAuthCode()

        val actionbartxt = findViewById<TextView>(R.id.tabHeading)
        val jones = findViewById<TextView>(R.id.character1_name)
        val ali = findViewById<TextView>(R.id.character2_name)
        val painthead = actionbartxt.paint
        val shader1 : Shader = LinearGradient(0f, 0f,0f,actionbartxt.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        painthead.shader = shader1

        val shader2 : Shader = LinearGradient(0f, 0f,0f,jones.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        jones.paint.shader = shader2
        ali.paint.shader = shader2
        back = findViewById(R.id.back_btn)
        instruction = findViewById(R.id.instruction)
        back.setOnClickListener {
            val intent = Intent(this, RoomsActvity::class.java)
            startActivity(intent)
        }
        instruction.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
        }

        Log.e("tag","${sharedPreferences.getRoomid()}")
        Log.e("auth","${sharedPreferences.getAuthCode()}")



        viewModel.getCurrentStoryDetails(sharedPreferences.getRoomid().toString(),"Bearer $authToken")
        viewModel.storyResponse.observe(this,{
            if(it!=null) {
                for(item in it.data.story) {
                    dataList.add(Story(item.roomNo,item.sender,item.message))
                }
                DataList.add(Story(dataList[0].roomNo,dataList[0].sender,dataList[0].message))
                adapter = StoryAdapter(this, DataList)
                storyView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true)
                storyView.adapter = adapter
                adapter.notifyDataSetChanged()
                nxt_btn.setOnClickListener {

                        DataList.add(Story(dataList[count].roomNo,dataList[count].sender,dataList[count].message))
                        adapter = StoryAdapter(this, DataList)
                        storyView.layoutManager = LinearLayoutManager(this)
                        storyView.adapter = adapter
                        adapter.notifyDataSetChanged()
                        count++
                         storyView.smoothScrollToPosition(storyView.getAdapter()!!.getItemCount()-1)

                        if(count==dataList.size) {
                            nxt_btn.setVisibility(View.INVISIBLE)
                            continue_btn.setVisibility(View.VISIBLE)

                        }
                }


            }
            Log.e("StoryResponse","$it")

        })
        continue_btn.setOnClickListener{
            val intent = Intent(this,QuestionActivity::class.java )
            startActivity(intent)

        }
    }

}
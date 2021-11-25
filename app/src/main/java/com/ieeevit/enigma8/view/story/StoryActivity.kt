package com.ieeevit.enigma8.view.story

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
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.ProgressBarAnimation
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
    private lateinit var progress: ProgressBar
    private lateinit var blackScreen:ImageView
    var count:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
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
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            view.findViewById<Button>(R.id.try_again).setOnClickListener(View.OnClickListener {
                recreate()

            })
        }
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
        blackScreen  = findViewById(R.id.overlay)
        progress = findViewById(R.id.progressBar)
        blackScreen.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progress, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progress.startAnimation(anim)

        val shader2 : Shader = LinearGradient(0f, 0f,0f,jones.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        jones.paint.shader = shader2
        ali.paint.shader = shader2
//        back = findViewById(R.id.back_btn)
//        instruction = findViewById(R.id.instruction)
//        back.setOnClickListener {
//            val intent = Intent(this, RoomsActvity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        instruction.setOnClickListener {
//            val intent = Intent(this, InstructionActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        Log.e("tag","${sharedPreferences.getRoomid()}")
        Log.e("auth","${sharedPreferences.getAuthCode()}")



        viewModel.getCurrentStoryDetails(sharedPreferences.getRoomid().toString(),"Bearer $authToken")
        viewModel.storyResponse.observe(this,{
            progress.visibility = View.GONE
            blackScreen.visibility = View.GONE
            if(it!=null) {
                for(item in it.data.story) {
                    if(item.roomNo !=0 ) {
                        dataList.add(Story(item.roomNo,item.sender,item.message))
                    }
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
            finish()

        }
    }

}
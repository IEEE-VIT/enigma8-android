package com.ieeevit.enigma8.view.story

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ieeevit.enigma8.ProgressBarAnimation
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.Full_Story
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity

import com.ieeevit.enigma8.viewModel.FullStoryViewModel
import org.w3c.dom.Text


class CharacterActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: PrefManager
    private lateinit var back: ImageView
    private lateinit var instruction: ImageView
    private lateinit var progress: ProgressBar
    private lateinit var blackScreen:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc_character)
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

        var count = 0


        var viewModel: FullStoryViewModel = ViewModelProvider(this).get(FullStoryViewModel::class.java)
        var dataList: MutableList<Full_Story> = mutableListOf()
        val conti = findViewById<Button>(R.id.contin)

        var name = findViewById<TextView>(R.id.name_char)
        val authToken = sharedPreferences.getAuthCode()
        var desc = findViewById<TextView>(R.id.charac_card)

        val sharedPreferences: PrefManager = PrefManager(this)

//        back = findViewById(R.id.back_btn)
//        instruction = findViewById(R.id.instruction)
        blackScreen = findViewById(R.id.overlay)
        progress = findViewById(R.id.progressBar)
        blackScreen.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progress, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progress.startAnimation(anim)
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

        viewModel.getFullStoryDetails(
            sharedPreferences.getRoomid().toString(),
            "Bearer $authToken"
        )
        viewModel.fullstoryResponse.observe(this, {

            progress.visibility = View.GONE
            blackScreen.visibility = View.GONE

            dataList.clear()
            if (it != null) {
                for (item in it.data.story) {
                    if (item.roomNo == 0) {

                        dataList.add(Full_Story(item.roomNo, item.sender, item.message))
                    }
                }

                Log.e("dataList", "$dataList")


            }

            name.text = dataList[0].sender
            desc.text = dataList[0].message
            Log.e("mssg", "$dataList")

            conti.setOnClickListener {
                if (count == dataList.size - 1) {
                    val intent = Intent(this, StoryActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                count++


                name.text = dataList[count].sender
                desc.text = dataList[count].message


            }

            Log.e("StoryResponse", "$it")

        })

    }
}
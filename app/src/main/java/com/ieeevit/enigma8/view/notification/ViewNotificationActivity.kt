package com.ieeevit.enigma8.view.notification

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity

class ViewNotificationActivity: AppCompatActivity() {
    private lateinit var notification:TextView
    private lateinit var time:TextView
    private lateinit var tabHeading:TextView
    private lateinit var back: ImageView
    private lateinit var instruction: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notifications_view)
        back = findViewById(R.id.back_btn)
        instruction = findViewById(R.id.instruction)
        back.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        instruction.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
        }
        notification = findViewById(R.id.notification)
        tabHeading = findViewById(R.id.tabHeading)
        val shader1 : Shader = LinearGradient(0f, 0f, 0f,notification.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
//        text1.paint.shader = shader1
        notification.paint.shader = shader1
        val shader2 : Shader = LinearGradient(0f, 0f, 0f,tabHeading.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f, 0.6f), Shader.TileMode.REPEAT)
//        text1.paint.shader = shader1
        tabHeading.paint.shader = shader2
        time = findViewById(R.id.notif_text)
        val extras = intent.getStringExtra("metadata")
        Log.e("Extras","$extras")
        notification.text = "hello"
        val gotTime  = intent.getStringExtra("time")
        time.text = "As a bonus , we have extended the time by $gotTime"

    }

}
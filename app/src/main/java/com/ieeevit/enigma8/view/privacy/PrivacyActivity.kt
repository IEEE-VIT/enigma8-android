package com.ieeevit.enigma8.view.privacy

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.notification.NotificationActivity
import com.ieeevit.enigma8.view.profile.Profdashboard
import com.ieeevit.enigma8.view.rooms.RoomsActvity

class PrivacyActivity:AppCompatActivity() {
    private lateinit var back: ImageView
    private lateinit var instruction: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privacy_policy)
        val heading : TextView = findViewById<EditText>(R.id.privacy_policy)
        val shader1 : Shader= LinearGradient(0f, 0f,0f,heading.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),Shader.TileMode.REPEAT)
        heading.paint.shader = shader1
        back = findViewById(R.id.back_btn)
        instruction = findViewById(R.id.instruction)
        back.setOnClickListener {
            val intent = Intent(this, Profdashboard::class.java)
            startActivity(intent)
        }
        instruction.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
        }

    }
}
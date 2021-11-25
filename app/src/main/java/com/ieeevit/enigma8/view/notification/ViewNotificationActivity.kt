package com.ieeevit.enigma8.view.notification

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
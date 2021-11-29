package com.ieeevit.enigma8.view.notification

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.NotificationAdapter
import com.ieeevit.enigma8.model.notification.Notification
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.viewModel.NotificationViewModel
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.*

@Suppress("DEPRECATION")
class NotificationActivity:AppCompatActivity() {
    lateinit var viewModel: NotificationViewModel
    private lateinit var sharedPreference: PrefManager
    var dataList:MutableList<Notification> = mutableListOf()
    private lateinit var notificationView: RecyclerView
    private lateinit var adapter: NotificationAdapter
    private lateinit var come: ConstraintLayout
    private lateinit var zero : ConstraintLayout
    private lateinit var tabHeading:TextView
    private lateinit var back: ImageView
    private lateinit var instruction: ImageView


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        if(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable){
            val view = View.inflate(this, R.layout.connection_error, null)
            val builder = android.app.AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.0f
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.window!!.attributes = lp
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            view.findViewById<Button>(R.id.try_again).setOnClickListener(View.OnClickListener {
                recreate()

            })
        }
        val heading : TextView = findViewById(R.id.heading_notification)
//        text1 = findViewById(R.id.nothing_text)
        val shader1 : Shader= LinearGradient(0f, 0f,0f,heading.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.4f,0.6f), Shader.TileMode.REPEAT)
        heading.paint.shader = shader1
        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        sharedPreference = PrefManager(this)
        come = findViewById(R.id.notificationCome)
        zero = findViewById(R.id.zeroNotification)
        notificationView = findViewById(R.id.notificationRecyler)
        val authCode: String? = sharedPreference.getAuthCode()
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

        viewModel.getNotificationDetails("Bearer ${authCode.toString()}")
        viewModel.notificationStatus.observe(this,{
            dataList.clear()
            if(it!=null) {
                Log.e("NotificationRespobsne","$it")
                for(item in it.data.notifs) {
                    val dateNow = Calendar.getInstance().time
                    val hours = dateNow.hours
                    val minutes = dateNow.minutes
                    val timeOfDay: LocalTime = Instant.ofEpochSecond(item.timestamp.toLong()/1000)
                            .atOffset(ZoneOffset.UTC)
                            .toLocalTime()
                    val notificationDate = Date(item.timestamp.toLong()).date
                    Log.e("notificationdate","$notificationDate")
                    val date  = dateNow.date
                    if(date == notificationDate) {
                        val hourOfDay: Int = timeOfDay.hour
                        val MinuteOfDay :Int = timeOfDay.minute
                        val minutesDifference = ((hours*60+minutes)-(hourOfDay*60+MinuteOfDay))
                        if(minutesDifference<60) {
                            dataList.add(Notification(item._id,item.text,item.type,item.metadata, "$minutesDifference min(s) ago"))
                        }
                        else  {
                            dataList.add(Notification(item._id,item.text,item.type,item.metadata,(minutesDifference/60).toString()+" hour(s) ago"))
                        }

                    }
                    else {
                        dataList.add(Notification(item._id,item.text,item.type,item.metadata,(date-notificationDate).toString()+" day(s) ago"))

                    }


                }
                if(dataList.size==0) {
                    zero.visibility = View.VISIBLE
                    come.visibility = View.INVISIBLE
                }
                else {
                    adapter = NotificationAdapter(this, dataList,viewModel,authCode)
                    notificationView.layoutManager = LinearLayoutManager(this)
                    notificationView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    zero.visibility = View.INVISIBLE
                    come.visibility = View.VISIBLE

                }

            }

        })

    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this,RoomsActvity::class.java)
            startActivity(intent)
            finish()
        }
        return true
    }

}
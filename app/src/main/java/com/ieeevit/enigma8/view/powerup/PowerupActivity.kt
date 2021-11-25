package com.ieeevit.enigma8.view.powerup

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.PowerupAdapter
import com.ieeevit.enigma8.model.powerup.Powerups
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.instruction.InstructionActivity
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import com.ieeevit.enigma8.view.story.CharacterActivity
import com.ieeevit.enigma8.view.story.StoryActivity
import com.ieeevit.enigma8.viewModel.PowerUpViewModel

class PowerupActivity : AppCompatActivity(){
    private lateinit var sharedPreferences: PrefManager
    private lateinit var adapter: PowerupAdapter
    private lateinit var powerupView: RecyclerView
    private lateinit var viewModel: PowerUpViewModel
    private lateinit var back:ImageView
    private lateinit var instruction:ImageView
    private var unused : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_powerup)
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
        back = findViewById(R.id.back_btn)
        instruction = findViewById(R.id.instruction)
        back.setOnClickListener {
            val intent = Intent(this,RoomsActvity::class.java)
            startActivity(intent)
        }
        instruction.setOnClickListener {
            val intent = Intent(this,InstructionActivity::class.java)
            startActivity(intent)
        }


        val powruptxt = findViewById<TextView>(R.id.powerup_txt)
        val painthead = powruptxt.paint
        val shader1: Shader = LinearGradient(0f, 0f, 0f, powruptxt.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f, 0.7f), Shader.TileMode.REPEAT)
        painthead.shader = shader1

        viewModel = ViewModelProvider(this).get(PowerUpViewModel::class.java)
        sharedPreferences = PrefManager(this)
        val authToken = sharedPreferences.getAuthCode()
        powerupView = findViewById(R.id.powerups)
        Log.e("Token", "$authToken")
        viewModel.getPowerupDetails("Bearer ${authToken.toString()}")

        var dataList: MutableList<Powerups> = mutableListOf()
        viewModel.powerupStatus.observe(this, {
            dataList.clear()
            if (it != null) {
                for (item in it.data.powerups) {

                    if (item.available_to_use == false) {
                        if (dataList.size == 0) {
                            dataList.add(Powerups(item._id, item.name, item.detail, item.icon, item.available_to_use))
                        } else {
                            dataList.add(dataList.size - 1, Powerups(item._id, item.name, item.detail, item.icon, item.available_to_use))

                        }


                    } else {
                        dataList.add(unused, Powerups(item._id, item.name, item.detail, item.icon, item.available_to_use))
                        unused++
                    }

                }
                adapter = PowerupAdapter(this, dataList, viewModel, authToken.toString())
                powerupView.layoutManager = LinearLayoutManager(this)
                powerupView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            Log.e("Response", "$it")
        })
        viewModel.sPowerupResponse.observe(this,{
            if (sharedPreferences.getRoomid() == sharedPreferences.getRoomOneid()) {

                val intent = Intent(this, CharacterActivity::class.java)
                startActivity(intent)
                finish()
            } else if(sharedPreferences.getRoomid() != sharedPreferences.getRoomOneid()) {

                val intent = Intent(this, StoryActivity::class.java)
                startActivity(intent)
                finish()

            }

        })



    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return true
    }


}
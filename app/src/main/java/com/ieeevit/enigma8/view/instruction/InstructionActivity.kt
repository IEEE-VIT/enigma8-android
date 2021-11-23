package com.ieeevit.enigma8.view.instruction

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.InstructionSliderAdapter
import com.ieeevit.enigma8.view.IndicatorLayout
import com.ieeevit.enigma8.view.rooms.RoomsActvity


@Suppress("DEPRECATION")
class InstructionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        var fragmentList:MutableList<Fragment> = mutableListOf()
        val vpIntroSlider = findViewById<ViewPager2>(R.id.vpIntroSlider)
        val nxtBtn = findViewById<Button>(R.id.next)



        val rightArrow = findViewById<ImageView>(R.id.arrow_right)
        val leftArrow = findViewById<ImageView>(R.id.arrow_left)


        val headtext = findViewById<TextView>(R.id.heading_instructions)
        val insname = findViewById<TextView>(R.id.instruction_name)
        val painthead = headtext.paint
        val shader1 : Shader = LinearGradient(0f, 0f,0f,headtext.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        painthead.shader = shader1
        val shader2 : Shader = LinearGradient(0f, 0f,0f,insname.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_blue), this.getColor(R.color.dark_blue)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        insname.paint.shader = shader2

        val tabhead = findViewById<TextView>(R.id.tabHeading)
        val shader3 : Shader = LinearGradient(0f, 0f,0f,tabhead.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        tabhead.paint.shader = shader3

        // making the status bar transparent
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val indicatorLayout = findViewById<IndicatorLayout>(R.id.indicatorLayout)
        val adapter = InstructionSliderAdapter(this)
        vpIntroSlider.adapter = adapter
        fragmentList.add(GameMechanics())
        fragmentList.add(ScoringSystem())
        fragmentList.add(Poweups())
        fragmentList.add(RoomStates())
        fragmentList.add(LetsPlay())

        Log.e("this","${fragmentList}")
        adapter.setFragmentList(fragmentList)
        indicatorLayout.setIndicatorCount(adapter.itemCount)
        indicatorLayout.selectCurrentPosition(0)
        registerListeners(fragmentList)
    }
    private fun registerListeners(fragmentList:MutableList<Fragment>) {
        val vpIntroSlider = findViewById<ViewPager2>(R.id.vpIntroSlider)
        val indicatorLayout = findViewById<IndicatorLayout>(R.id.indicatorLayout)
        val rightArrow = findViewById<ImageView>(R.id.arrow_right)
        val leftArrow = findViewById<ImageView>(R.id.arrow_left)
        val nxtBtn = findViewById<Button>(R.id.next)
        val instructionname = findViewById<TextView>(R.id.instruction_name)
        val icon_instruction = findViewById<ImageView>(R.id.icon_instruction)

        vpIntroSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicatorLayout.selectCurrentPosition(position)
                if (position < fragmentList.lastIndex) {
                    if (position==0) {
                        icon_instruction.setImageResource(R.drawable.game_mechanics)
                        instructionname.text = "GAME MECHANICS"
                        leftArrow.visibility = View.INVISIBLE
                        rightArrow.visibility = View.VISIBLE
                    }
                    else {
                        leftArrow.visibility = View.VISIBLE
                        rightArrow.visibility = View.VISIBLE
                        if(position==1)
                        {
                            icon_instruction.setImageResource(R.drawable.scoring_system)
                            instructionname.text = "SCORING SYSTEM"
                        }
                        else if(position==2)
                        {
                            icon_instruction.setImageResource(R.drawable.powerups_icon)
                            instructionname.text = "POWERUPS"
                        }
                        else if(position==3)
                        {
                            icon_instruction.setImageResource(R.drawable.ic_room_state)
                            instructionname.text = "ROOM STATES"
                        }
                    }

                }
                else {
                    instructionname.text = "LET'S PLAY!"
                    icon_instruction.setImageResource(R.drawable.lets_play)
                    rightArrow.visibility = View.INVISIBLE
                    leftArrow.visibility = View.VISIBLE
                    nxtBtn.visibility = View.VISIBLE


                }
            }
        })

        nxtBtn.setOnClickListener{
            startActivity(Intent(this, RoomsActvity::class.java))
            finish()
        }
        rightArrow.setOnClickListener {
            val position = vpIntroSlider.currentItem
            if (position < fragmentList.lastIndex) {
                vpIntroSlider.currentItem = position + 1
            }
        }
        leftArrow.setOnClickListener{
            val position = vpIntroSlider.currentItem
            if (position <= fragmentList.lastIndex) {
                vpIntroSlider.currentItem = position - 1
                Log.e("lol","$position")
            }
        }


    }
}
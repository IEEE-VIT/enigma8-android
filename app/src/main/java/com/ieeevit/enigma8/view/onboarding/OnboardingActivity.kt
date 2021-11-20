package com.ieeevit.enigma8.view.onboarding

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ieeevit.enigma8.MainActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.IntroSliderAdapter
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.IndicatorLayout
import com.ieeevit.enigma8.view.rooms.RoomsActvity


@Suppress("DEPRECATION")
class OnboardingActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        var fragmentList:MutableList<Fragment> = mutableListOf()
        sharedPreferences = PrefManager(this)

        val arrow = findViewById<ImageView>(R.id.tvArrow)
        val get = findViewById<TextView>(R.id.get)

        val headtext = findViewById<TextView>(R.id.enigma)
        val painthead = headtext.paint
        val shader1 : Shader = LinearGradient(0f, 0f,0f,headtext.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        painthead.shader = shader1
        val shader2 : Shader = LinearGradient(0f, 0f,0f,get.lineHeight.toFloat(), intArrayOf(this.getColor(R.color.light_yellow), this.getColor(R.color.dark_yellow)), floatArrayOf(0.3f,0.7f),
            Shader.TileMode.REPEAT)
        get.paint.shader = shader2

        // making the status bar transparent
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val vpIntroSlider = findViewById<ViewPager2>(R.id.vpIntroSlider)
        val indicatorLayout = findViewById<IndicatorLayout>(R.id.indicatorLayout)
        val adapter = IntroSliderAdapter(this)
        vpIntroSlider.adapter = adapter
        fragmentList.add(OnboardingFragmentOne())
        fragmentList.add(OnboardingFragmentTwo())
        fragmentList.add(OnboardingFragmentThree())
        Log.e("this","${fragmentList}")
        adapter.setFragmentList(fragmentList)
        indicatorLayout.setIndicatorCount(adapter.itemCount)
        indicatorLayout.selectCurrentPosition(0)
        registerListeners(fragmentList)
    }
    private fun registerListeners(fragmentList:MutableList<Fragment>) {
        val vpIntroSlider = findViewById<ViewPager2>(R.id.vpIntroSlider)
        val indicatorLayout = findViewById<IndicatorLayout>(R.id.indicatorLayout)
        val tvArrow = findViewById<ImageView>(R.id.tvArrow)
        val get = findViewById<TextView>(R.id.get)
        vpIntroSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicatorLayout.selectCurrentPosition(position)
                if (position < fragmentList.lastIndex) {
                    tvArrow.setImageResource(R.drawable.ic_arrow)
                }
                else {
                    tvArrow.visibility = View.INVISIBLE
                    get.visibility = View.VISIBLE

                }
            }
        })



        tvArrow.setOnClickListener {
            val position = vpIntroSlider.currentItem
            if (position < fragmentList.lastIndex) {
                vpIntroSlider.currentItem = position + 1
            }
        }
        get.setOnClickListener {
            sharedPreferences.setIndicator(1)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
        }
    }
}
package com.ieeevit.enigma8.view.storyM

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ieeevit.enigma8.R

class Storyfirstactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storyactivity)

        val storyone = Storyfragmentone()
        val storytwo = Storyfragmenttwo()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.StoryfragmentFL, storyone)
            commit()
        }

        val btnNext = findViewById<Button>(R.id.btnstory_Next)
        btnNext.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.StoryfragmentFL, storytwo)
                addToBackStack(null)
                commit()
            }
        }
        val btnPrev = findViewById<Button>(R.id.btnstory_previous)
        btnPrev.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.StoryfragmentFL, storyone)
                addToBackStack(null)
                commit()
            }
        }
    }
}
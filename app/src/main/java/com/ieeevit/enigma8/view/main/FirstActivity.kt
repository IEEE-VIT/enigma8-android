package com.ieeevit.enigma8.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ieeevit.enigma8.R


class FirstActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val myFragment = RoomFragment()
        val fragment : Fragment? =
            supportFragmentManager.findFragmentByTag(RoomFragment::class.java.simpleName)
        if (fragment !is RoomFragment) {
            supportFragmentManager.beginTransaction().add(
                R.id.container,myFragment,
                RoomFragment::class.java.simpleName)
                .commit()




        }

    }
}
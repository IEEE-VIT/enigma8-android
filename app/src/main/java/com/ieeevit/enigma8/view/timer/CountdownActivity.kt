package com.ieeevit.enigma8.view.timer

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ieeevit.enigma8.R

class   CountdownActivity : AppCompatActivity() {

    private lateinit var bt:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)
//            if (savedInstanceState == null) {
//                val fragment = CountdownFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
//
//            }
        val myFragment = CountdownFragment()
        val fragment : Fragment? =
                    supportFragmentManager.findFragmentByTag(CountdownFragment::class.java.simpleName)
            if (fragment !is CountdownFragment) {
                supportFragmentManager.beginTransaction().add(R.id.container,myFragment,CountdownFragment::class.java.simpleName)
                        .commit()




        }

    }


//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        actionBar?.hide()
//    }
}
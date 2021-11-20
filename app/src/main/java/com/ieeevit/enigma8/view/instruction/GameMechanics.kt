package com.ieeevit.enigma8.view.instruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.databinding.FragmentGameMechanicsBinding

class GameMechanics : Fragment(){
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_mechanics, container, false)
    }
}
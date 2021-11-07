package com.ieeevit.enigma8.view.main

import android.content.Intent
import android.os.Bundle
import android.system.Os.close
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.adapter.PowerupAdapter
import com.ieeevit.enigma8.adapter.RoomsAdapter
import com.ieeevit.enigma8.model.PowerupRequest
import com.ieeevit.enigma8.model.Powerups
import com.ieeevit.enigma8.model.RoomsOuter
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.viewModel.PowerUpViewModel
import com.ieeevit.enigma8.viewModel.RoomViewModel
import com.ieeevit.enigma8.viewModel.SendPowerupViewModel

class PowerupDialog : DialogFragment(){
    private lateinit var sharedPreferences: PrefManager
    private lateinit var adapter: PowerupAdapter
    private lateinit var powerupView: RecyclerView
    private var unused : Int = 0

    private val viewModel: PowerUpViewModel by lazy {
        ViewModelProvider(this, PowerUpViewModel.Factory())
                .get(PowerUpViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_powerup, container, false)

        rootView.findViewById<ImageButton>(R.id.close).setOnClickListener {
            dismiss()
        }
        sharedPreferences = PrefManager(requireContext())
        val authToken = sharedPreferences.getAuthCode()
        powerupView = rootView.findViewById(R.id.powerups)
        Log.e("Token","$authToken")
        viewModel.getPowerupDetails("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFuczI5aHVsQGdtYWlsLmNvbSIsImlhdCI6MTYzNDQ4NDU1MX0.GS_wtFw-bMjAM_50VhfmJAb-aq2ReHMXrALAPcTOxYQ")

        var dataList : MutableList<Powerups> = mutableListOf()
        viewModel.powerupStatus.observe(viewLifecycleOwner, {

            if (it != null) {
                for (item in it.data.powerups) {

                    if(item.available_to_use==false) {
                        if(dataList.size==0) {
                            dataList.add(Powerups(item._id,item.name,item.detail,item.icon,item.available_to_use))
                        }
                        else {
                            dataList.add(dataList.size-1,Powerups(item._id,item.name,item.detail,item.icon,item.available_to_use))

                        }


                    }
                    else {
                        dataList.add(unused,Powerups(item._id,item.name,item.detail,item.icon,item.available_to_use))
                        unused++
                    }

                }
                adapter = PowerupAdapter(requireContext(), dataList)
                powerupView.layoutManager = LinearLayoutManager(context)
                powerupView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            Log.e("Response","$it")
        })

        rootView.findViewById<Button>(R.id.continue_btn).setOnClickListener {
            val intent = Intent(context,StoryActivity::class.java)
            startActivity(intent)
        }
        return rootView
    }

}
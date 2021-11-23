package com.ieeevit.enigma8.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.powerup.PowerupRequest
import com.ieeevit.enigma8.model.powerup.Powerups
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.story.CharacterActivity
import com.ieeevit.enigma8.view.story.StoryActivity
import com.ieeevit.enigma8.viewModel.PowerUpViewModel
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class PowerupAdapter(var context: Context, var dataList: List<Powerups>, val viewModel: PowerUpViewModel, val authToken:String):RecyclerView.Adapter<PowerupAdapter.ViewHolder>() {
    private lateinit var sharedPreferences: PrefManager
    lateinit var  lifecycleOwner:LifecycleOwner
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var detail: TextView
        var icon : ImageView
        var used : ImageView

        init {
            name = itemView.findViewById(R.id.name)
            icon = itemView.findViewById(R.id.icon)
            detail = itemView.findViewById(R.id.detail)
            used = itemView.findViewById(R.id.powerupused)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PowerupAdapter.ViewHolder {
        sharedPreferences = PrefManager(context)
        lifecycleOwner = parent.context as LifecycleOwner


        var view = LayoutInflater.from(parent.context).inflate(R.layout.powerup, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PowerupAdapter.ViewHolder, position: Int) {
        var data = dataList[position]


        holder.name.text = data.name
        holder.detail.text = data.detail
        Picasso.get().load(data.icon).into(holder.icon)
        Picasso.get().load(Uri.parse(data.icon)).into(holder.icon)

        if(!data.available_to_use)
        {

            holder.used.visibility = View.VISIBLE
            holder.itemView.isClickable = false

            holder.itemView.setBackgroundColor(Color.parseColor("#FF5349"))

        }
        else if(data.available_to_use == true) {
            holder.itemView.setOnClickListener {

                Log.e("Powerupid", "${dataList[holder.adapterPosition]._id}")

                val Dialogview = View.inflate(context, R.layout.confirm_powerup, null)
                val builder = AlertDialog.Builder(context)
                builder.setView(Dialogview)
                val dialog = builder.create()
                val lp = dialog.window!!.attributes
                lp.dimAmount = 0.0f
                dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                dialog.window!!.attributes = lp
                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.show()
                sharedPreferences.setPowerupName(dataList[position].name)
                sharedPreferences.setRoomid(sharedPreferences.getRoomid().toString())
                val icon = Dialogview.findViewById<ImageView>(R.id.powerup_icon)
                Picasso.get().load(Uri.parse(data.icon)).into(icon)



                Dialogview.findViewById<Button>(R.id.confirm_btn).setOnClickListener {
                    val sendPowerupRequest = PowerupRequest(
                            sharedPreferences.getRoomid().toString(),
                            dataList[holder.adapterPosition]._id
                    )
                    viewModel.sendPowerupDetails("Bearer ${authToken}", sendPowerupRequest)
                    viewModel.sPowerupResponse.observe(lifecycleOwner,{
                        if (sharedPreferences.getRoomid() == sharedPreferences.getRoomOneid()) {

                            val intent = Intent(context, CharacterActivity::class.java)
                            context.startActivity(intent)
                        } else if(sharedPreferences.getRoomid() != sharedPreferences.getRoomOneid()) {

                            val intent = Intent(context, StoryActivity::class.java)
                            context.startActivity(intent)

                        }

                    })






                }
                Dialogview.findViewById<ImageView>(R.id.close).setOnClickListener {
                    dialog.dismiss()
                }

            }


        }
    }


    override fun getItemCount() = dataList.size

}
package com.ieeevit.enigma8.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.Powerups
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.main.ProfileActivity
import com.ieeevit.enigma8.view.main.StoryActivity
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class PowerupAdapter(var context: Context, var dataList: List<Powerups>):RecyclerView.Adapter<PowerupAdapter.ViewHolder>() {
    private lateinit var sharedPreferences: PrefManager
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var detail: TextView
        var icon : ImageView

        init {
            name = itemView.findViewById(R.id.name)
            icon = itemView.findViewById(R.id.icon)
            detail = itemView.findViewById(R.id.detail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PowerupAdapter.ViewHolder {
        sharedPreferences = PrefManager(context)


        var view = LayoutInflater.from(parent.context).inflate(R.layout.powerup, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PowerupAdapter.ViewHolder, position: Int) {
        var data = dataList[position]

        holder.name.text = data.name
        holder.detail.text = data.detail

        if(!data.available_to_use)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF5349"))
        }
        holder.itemView.setOnClickListener {
            sharedPreferences.setPowerupid(dataList[holder.adapterPosition]._id)
            val Dialogview = LayoutInflater.from(context).inflate(R.layout.confirm_powerup, null)
            val mBuilder = AlertDialog.Builder(context).setView(Dialogview)

            val mAlertDialog = mBuilder.show()

            Dialogview.findViewById<Button>(R.id.confirm_button).setOnClickListener {
                val intent = Intent(context,StoryActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)

            }
            Dialogview.findViewById<Button>(R.id.cancel_button).setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        Picasso.get().load("https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png").into(holder.icon)

    }


    override fun getItemCount() = dataList.size

}
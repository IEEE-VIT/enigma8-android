package com.ieeevit.enigma8.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.RoomsOuter
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.main.PowerupDialog

import com.squareup.picasso.Picasso
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLConnection


@Suppress("DEPRECATION")
class RoomsAdapter(var context: Context, var dataList: List<RoomsOuter>):RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {
    private lateinit var sharedPreferences: PrefManager
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var roomName: TextView
        var roomImage: ImageView
        var leftLamp: ImageView
        var rightLamp: ImageView
        var centerLamp: ImageView
        var left: ImageView
        var right: ImageView
        var center: ImageView

        init {
            roomName = itemView.findViewById(R.id.roomName)
            roomImage = itemView.findViewById(R.id.roomImage)
            leftLamp = itemView.findViewById(R.id.leftLamp)
            rightLamp = itemView.findViewById(R.id.rightLamp)
            centerLamp = itemView.findViewById(R.id.centerLamp)
            left  = itemView.findViewById(R.id.left)
            right = itemView.findViewById(R.id.right)
            center = itemView.findViewById(R.id.center)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsAdapter.ViewHolder {
        sharedPreferences = PrefManager(context)

        var view = LayoutInflater.from(parent.context).inflate(R.layout.room_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomsAdapter.ViewHolder, position: Int) {
        var data = dataList[position]

        holder.roomName.text = data.name
//        holder.roomImage.setImageURI(data.media.toUri())




//        holder.roomImage.setImageURI(data.media.toUri())
        Picasso.get().load("https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png").into(holder.roomImage)
        holder.leftLamp.setImageResource(data.leftLamp)
        holder.rightLamp.setImageResource(data.rightLamp)
        holder.centerLamp.setImageResource(data.centerLamp)
        holder.right.setImageResource(data.right)
        holder.left.setImageResource(data.left)
        holder.center.setImageResource(data.center)
        holder.itemView.setOnClickListener {
                sharedPreferences.setRoomid(dataList[holder.adapterPosition].roomid)
                var dialog = PowerupDialog()
                val activity : AppCompatActivity = holder.itemView.context as AppCompatActivity
                dialog.show(activity.supportFragmentManager, null)
            }




    }

    override fun getItemCount() = dataList.size

}
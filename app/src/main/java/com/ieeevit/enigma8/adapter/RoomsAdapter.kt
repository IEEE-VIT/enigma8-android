package com.ieeevit.enigma8.adapter

import android.annotation.SuppressLint
import android.app.Activity
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
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.model.room.RoomsOuter
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.powerup.PowerupActivity
import com.ieeevit.enigma8.view.question.QuestionActivity
import com.ieeevit.enigma8.viewModel.RoomViewModel
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class RoomsAdapter(var context: Context, var dataList: List<RoomsOuter>, val authToken: String, val lifecycleOwner: LifecycleOwner, val listner: OnRoomClickListner):RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {
    private lateinit var sharedPreferences: PrefManager

    var flag : Boolean = false
    var current = 0
    lateinit var view:View

    interface OnRoomClickListner {
        fun onRoomClick( id: String){}
    }

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RoomsAdapter.ViewHolder, position: Int) {
        var data = dataList[position]
        holder.roomName.text = "Room "+"${data.roomNo}"
        Picasso.get().load(Uri.parse(data.media)).into(holder.roomImage)
        holder.leftLamp.setImageResource(data.leftLamp)
        holder.rightLamp.setImageResource(data.rightLamp)
        holder.centerLamp.setImageResource(data.centerLamp)
        holder.right.setImageResource(data.right)
        holder.left.setImageResource(data.left)
        holder.center.setImageResource(data.center)
        Log.e("flag", "${data}")
        val authCode: String? = sharedPreferences.getAuthCode()
        sharedPreferences.getPowerupSetStatus()



        holder.itemView.setOnClickListener {
            listner.onRoomClick(dataList[position].roomid)
            sharedPreferences.setRoomNo(dataList[position].roomNo)
            sharedPreferences.setRoomid(dataList[position].roomid)
            sharedPreferences.setPowerup(dataList[position].powerupUsed)
            Log.e("ID", "${sharedPreferences.getRoomid()}")

            }


    }







    override fun getItemCount() = dataList.size
}
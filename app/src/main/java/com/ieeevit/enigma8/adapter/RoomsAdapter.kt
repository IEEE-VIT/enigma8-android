package com.ieeevit.enigma8.adapter

import android.annotation.SuppressLint
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
class RoomsAdapter(var context: Context, var dataList: List<RoomsOuter>, val viewModel: RoomViewModel, val authToken: String, val lifecycleOwner: LifecycleOwner):RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {
    private lateinit var sharedPreferences: PrefManager

    var flag : Boolean = false
    var current = 0
    lateinit var view:View

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

        holder.roomName.text = data.name

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
            viewModel.getRoomUnlockDetails(dataList[position].roomid, authToken)
            sharedPreferences.setRoomNo(dataList[position].roomNo)
            sharedPreferences.setRoomid(dataList[position].roomid)
            sharedPreferences.setPowerup(dataList[position].powerupUsed)
            Log.e("ID", "${sharedPreferences.getRoomid()}")
        }

        viewModel.checkResponse.observe(lifecycleOwner, {
            if (it.data.status == "complete") {
                val view = View.inflate(context, R.layout.room_done_dialog, null)
                val builder = AlertDialog.Builder(context)
                builder.setView(view)
                val dialog = builder.create()
                val lp = dialog.window!!.attributes
                lp.dimAmount = 0.0f
                dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                dialog.window!!.attributes = lp
                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.show()
                view.findViewById<ImageView>(R.id.close).setOnClickListener {
                    dialog.dismiss()
                }

            } else if (it.data.status == "canUnlock") {
                val intent = Intent(holder.itemView.context, PowerupActivity::class.java)
                var context = holder.itemView.context
                context.startActivity(intent)
//                (context as Activity).finish()

            } else if (it.data.status == "unlocked") {
                val intent = Intent(holder.itemView.context, QuestionActivity::class.java)
                var context = holder.itemView.context
                context.startActivity(intent)

            } else if (it.data.status == "locked") {
                val view = View.inflate(context, R.layout.room_unlock_dialog, null)
                val builder = AlertDialog.Builder(context)
                builder.setView(view)
                val dialog = builder.create()
                val lp = dialog.window!!.attributes
                lp.dimAmount = 0.0f
                dialog.window!!.attributes = lp
                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                dialog.show()
                view.findViewById<TextView>(R.id.room_unlock).text = "You need ${it.data.starsNeeded} more"
                val close = view.findViewById<ImageView>(R.id.close)

                close.setOnClickListener(View.OnClickListener { dialog.dismiss() })

            } else if (it.data.status == "complete") {

                val view = View.inflate(context, R.layout.room_done_dialog, null)
                val builder = AlertDialog.Builder(context)
                builder.setView(view)
                val dialog = builder.create()
                val lp = dialog.window!!.attributes
                lp.dimAmount = 0.0f
                dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                dialog.window!!.attributes = lp
                dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.show()

                val close = view.findViewById<ImageView>(R.id.close)
                close.setOnClickListener {
                    dialog.dismiss()
                }


            }
        })
    }







    override fun getItemCount() = dataList.size
}
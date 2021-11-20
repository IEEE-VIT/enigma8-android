package com.ieeevit.enigma8.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.webactivity.WebActivity
import com.ieeevit.enigma8.model.notification.Notification
import com.ieeevit.enigma8.utils.PrefManager
import com.ieeevit.enigma8.view.feedback.FeedbackActivity
import com.ieeevit.enigma8.view.notification.ViewNotificationActivity
import java.time.*


@Suppress("DEPRECATION")
class NotificationAdapter(var context: Context, var dataList: List<Notification>):RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    lateinit var sharedPreferences: PrefManager



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var message: TextView
        var time: TextView
        var view_btn:TextView


        init {
            message = itemView.findViewById(R.id.notif_title)
            time = itemView.findViewById(R.id.notif_time)
            view_btn = itemView.findViewById(R.id.view_button)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        sharedPreferences = PrefManager(context)

        var view = LayoutInflater.from(parent.context).inflate(R.layout.notication_card, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {

        var data = dataList[position]
        holder.message.text = data.text

        holder.view_btn.text = "View"
        holder.time.text = data.timestamp
        if(dataList[position].metadata == "") {
            holder.view_btn.visibility = View.INVISIBLE
        }

        holder.view_btn.setOnClickListener {
            if(dataList[position].type == "feedback" && dataList[position].metadata == "") {
                val intent = Intent(holder.itemView.context, FeedbackActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
            else if(dataList[position].type == "social" && dataList[position].metadata == "") {
                val intent = Intent(holder.itemView.context, WebActivity::class.java)
                intent.putExtra("URI",dataList[position].metadata)
                holder.itemView.context.startActivity(intent)

            }
            else if(dataList[position].type=="text" && dataList[position].metadata == ""){
                val intent = Intent(holder.itemView.context, ViewNotificationActivity::class.java)
                intent.putExtra("metadata", dataList[position].metadata)
                intent.putExtra("time",dataList[position].timestamp)
                holder.itemView.context.startActivity(intent)
            }


        }


    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertEpochToOffsetDateTime(epochValue: Long): OffsetDateTime {
        return OffsetDateTime.of(LocalDateTime.ofEpochSecond(epochValue, 0, ZoneOffset.UTC), ZoneOffset.UTC)
    }



    override fun getItemCount() = dataList.size
}
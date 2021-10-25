package com.example.chaty.utils

import android.content.Context
import android.util.Log
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.model.Message
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        fun getMessageType(message: Message): Int {
            val uid = mAuth.currentUser?.uid.toString()
            return if (message.sender == uid) 1 else 2
        }

        fun getMessageTime(timeInMillis: String): String {
            val timeFormat = SimpleDateFormat("hh:mm a")
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMillis.toLong()
            return clearLeadingZeros(timeFormat.format(calendar.time))
        }

        fun getChatID(senderID: String, receiverID: String) =
            if (senderID < receiverID) senderID + receiverID else receiverID + senderID

        fun getChatTime(time: String): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val timeFormat = SimpleDateFormat("hh:mm a")
            val calendar1 = Calendar.getInstance()
            val calendar2 = Calendar.getInstance()

            calendar1.timeInMillis = System.currentTimeMillis()
            calendar2.timeInMillis = time.toLong()

            if (calendar1.get(Calendar.YEAR) != calendar2.get(Calendar.YEAR))    // in different years
                return dateFormat.format(calendar2.time)

            if (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH))  // in same month
            {
                val diff =
                    calendar1.get(Calendar.DAY_OF_MONTH) - calendar2.get(Calendar.DAY_OF_MONTH)
                if (diff < 7) {
                    if (diff == 0) return clearLeadingZeros(timeFormat.format(calendar2.time)     )   // in the same day
                    if (diff == 1) return "Yesterday"
                    return when (calendar2.get(Calendar.DAY_OF_WEEK)) {
                        Calendar.SATURDAY -> "Sat"
                        Calendar.SUNDAY -> "Sun"
                        Calendar.MONDAY -> "Mon"
                        Calendar.TUESDAY -> "Tue"
                        Calendar.WEDNESDAY -> "Wed"
                        Calendar.THURSDAY -> "Thu"
                        else -> "Fri"
                    }
                }
            }
            return dateFormat.format(calendar2.time)
        }
        fun clearLeadingZeros(time:String):String{
            return if(time[0]=='0') time.drop(1) else time
        }
        fun convertPxToDp(context: Context, px: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (px * scale + 0.5f).toInt()
        }

        fun checkConversationDate(messages: List<Message>, position: Int): Boolean {
            if (position > 0) {
                val calendar1 = Calendar.getInstance()
                val calendar2 = Calendar.getInstance()
                calendar1.timeInMillis = messages[position].time.toLong()
                calendar2.timeInMillis = messages[position - 1].time.toLong()
                val diff = calendar1.timeInMillis - calendar2.timeInMillis
                val days = diff / (1000 * 60 * 60 * 24)
                if(messages[position].body=="hi shosho hi hi"){
                    Log.d("mostafa", "$days")
                }
                return if (days == 0L) {
                    calendar1[Calendar.DAY_OF_MONTH] == calendar2[Calendar.DAY_OF_MONTH]
                } else false
            }
            return false
        }
    }
}
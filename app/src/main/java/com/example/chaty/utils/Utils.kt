package com.example.chaty.utils

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
        fun getChatID(senderID:String,receiverID:String)=
            if(senderID<receiverID)senderID+receiverID else receiverID+senderID

        fun getChatTime(time:String): String {
            val dateFormat=SimpleDateFormat("dd/MM/yyyy")
            val timeFormat=SimpleDateFormat("hh:mm a")
            val calendar1=Calendar.getInstance()
            val calendar2=Calendar.getInstance()

            calendar1.timeInMillis=System.currentTimeMillis()
            calendar2.timeInMillis=time.toLong()

            if(calendar1.get(Calendar.YEAR)!=calendar2.get(Calendar.YEAR))    // in different years
                return dateFormat.format(calendar2.time)

            if(calendar1.get(Calendar.MONTH)==calendar2.get(Calendar.MONTH))  // in same month
            {
                val diff=calendar1.get(Calendar.DAY_OF_MONTH)-calendar2.get(Calendar.DAY_OF_MONTH)
                if(diff<7)
                {
                    if(diff==0) return timeFormat.format(calendar2.time)        // in the same day
                    if(diff==1) return "Yesterday"
                    return when(calendar2.get(Calendar.DAY_OF_WEEK)){
                        Calendar.SATURDAY->"Sat"
                        Calendar.SUNDAY->"Sun"
                        Calendar.MONDAY->"Mon"
                        Calendar.TUESDAY->"Tue"
                        Calendar.WEDNESDAY->"Wed"
                        Calendar.THURSDAY->"Thu"
                        else -> "Fri"
                    }
                }
            }
            return dateFormat.format(calendar2.time)
        }
    }
}
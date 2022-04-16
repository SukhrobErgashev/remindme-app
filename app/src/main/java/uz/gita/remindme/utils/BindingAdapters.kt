package uz.gita.remindme.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

object BindingAdapters {
    @BindingAdapter("android:setDateF")
    @JvmStatic
    fun setDataF(textView: TextView, l: Long) {
        val c = Calendar.getInstance()
        c.timeInMillis = l
        c.let {
            val year = it.get(Calendar.YEAR).toString()
            var month = it.get(Calendar.MONTH).toString()
            if (month.length == 1)
                month = "0$month"
            var day = it.get(Calendar.DAY_OF_MONTH).toString()
            if (day.length == 1)
                day = "0$day"
            textView.text = "$day/$month/$year"
        }
    }

    @BindingAdapter("android:setTimeF")
    @JvmStatic
    fun setTimeF(textView: TextView, l: Long) {
        val c = Calendar.getInstance()
        c.timeInMillis = l
        c.let {
            var hour = it.get(Calendar.HOUR_OF_DAY).toString()
            if (hour.length == 1)
                hour = "0$hour"
            var minute = it.get(Calendar.MINUTE).toString()
            if (minute.length == 1)
                minute = "0$minute"
            textView.text = "$hour:$minute"
        }
    }

    //    @BindingAdapter("android:setDateF")
//    @JvmStatic
//    fun setDataF(textView: TextView, dateS: DateS?) {
//        dateS?.let {
//            val year = dateS.year.toString()
//            val month = (dateS.month + 1).toString()
//            val day = dateS.day.toString()
//            textView.text = "$year/$month/$day"
//        }
//    }
//
//    @BindingAdapter("android:setTimeF")
//    @JvmStatic
//    fun setTimeF(textView: TextView, timeS: TimeS?) {
//        timeS?.let {
//            val hour = timeS.hour.toString()
//            val minute = timeS.minute.toString()
//            textView.text = "$hour:$minute"
//            //String.format("%d : %d", hour, minute)
//        }
//    }
}
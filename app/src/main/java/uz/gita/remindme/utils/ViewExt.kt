package uz.gita.remindme.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun TextInputEditText.transformIntoDatePicker(
    context: Context,
    format: String,
    selected: Long?
) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    if (selected != null) {
        myCalendar.timeInMillis = selected
    }
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.UK)
            setText(sdf.format(myCalendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context,
            datePickerOnDataSetListener,
            myCalendar
                .get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            show()
        }
    }
}

fun String.toMillisecond(): Long {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
    val date = sdf.parse(this)
    return date.time
}

fun Long.toDateString(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

fun Long.toDaysLeft(date: Date): String {
    return TimeUnit.MILLISECONDS.toDays(this - date.time).toString() + " Days left"
}
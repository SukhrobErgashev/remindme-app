package uz.gita.remindme.adapters.birthdays

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.remindme.R
import uz.gita.remindme.data.models.Birthday
import uz.gita.remindme.databinding.ItemBirthdaysBinding
import uz.gita.remindme.utils.toDaysLeft
import uz.gita.remindme.utils.toMillisecond
import java.util.*

class BirthdayViewHolder(val binding: ItemBirthdaysBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(birthday: Birthday) {
        binding.apply {
            nameBirthday.text = birthday.name
            dateBirthday.text = birthday.date
            birthdayDayLeft.text = birthday.date.toMillisecond().toDaysLeft(Date())

            notificationBirthday.setImageResource(
                if (birthday.remindMe) {
                    R.drawable.ic_notification
                } else R.drawable.ic_notify_off
            )
            personBirthday.setImageResource(
                if (birthday.gender == "Male") {
                    R.drawable.ic_male
                } else R.drawable.ic_female
            )

        }
    }

    companion object {
        fun create(parent: ViewGroup): BirthdayViewHolder {
            val binding = ItemBirthdaysBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return BirthdayViewHolder(binding)
        }
    }

}
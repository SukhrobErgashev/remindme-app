package uz.gita.remindme.adapters.birthdays

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import uz.gita.remindme.data.models.Birthday

class BirthdaysAdapter : ListAdapter<Birthday, BirthdayViewHolder>(WordComparator()) {

    var listener: ((Birthday) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdayViewHolder {
        return BirthdayViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BirthdayViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            listener?.invoke(currentItem)
        }
    }

    class WordComparator: DiffUtil.ItemCallback<Birthday>() {
        override fun areItemsTheSame(oldItem: Birthday, newItem: Birthday): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Birthday, newItem: Birthday): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.date == newItem.date &&
                    oldItem.gender == newItem.gender
        }

    }

}
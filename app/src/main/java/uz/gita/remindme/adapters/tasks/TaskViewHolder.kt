package uz.gita.remindme.adapters.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import uz.gita.remindme.data.models.Task
import uz.gita.remindme.databinding.ItemDateSectionBinding
import uz.gita.remindme.databinding.ItemTasksBinding

sealed class TaskViewHolder(open val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class TaskCardViewHolder(override val binding: ItemTasksBinding) :
        TaskViewHolder(binding) {

        fun bind(item: Task, taskCardListener: TaskCardListener) {
            with(binding) {
                isCompletedTask.isChecked = item.isCompleted
                taskItem = item
                listener = taskCardListener
//                if (item.isCompleted) {
//                    dueDateTask.paintFlags = dueDateTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                    dueTimeTask.paintFlags = dueTimeTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                }
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): TaskViewHolder {
                val binding: ItemTasksBinding = ItemTasksBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TaskCardViewHolder(binding)
            }
        }

    }

    class HeaderViewHolder(override val binding: ItemDateSectionBinding) :
        TaskViewHolder(binding) {

        fun bind(item: TaskDataItem.TaskHeader) {
            with(binding) {
                dateSection.text = item.date
            }
        }

        companion object {
            fun from(parent: ViewGroup): TaskViewHolder {
                val binding = ItemDateSectionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return HeaderViewHolder(binding)
            }
        }

    }

}

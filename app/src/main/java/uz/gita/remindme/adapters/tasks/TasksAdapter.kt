package uz.gita.remindme.adapters.tasks

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.gita.remindme.adapters.toListOfDataItem
import uz.gita.remindme.data.models.Task
import java.lang.ClassCastException

class TasksAdapter(private val taskCardListener: TaskCardListener) :
    ListAdapter<TaskDataItem, TaskViewHolder>(DataItemDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val TASK_CARD_VIEW_TYPE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TaskDataItem.TaskHeader -> HEADER_VIEW_TYPE
            is TaskDataItem.TaskCardItem -> TASK_CARD_VIEW_TYPE
        }
    }

    fun submitTasksList(list: List<Task>?) {
        adapterScope.launch {
            val listDataItem = list?.toListOfDataItem()
            withContext(Dispatchers.Main) {
                submitList(listDataItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> TaskViewHolder.HeaderViewHolder.from(parent)
            TASK_CARD_VIEW_TYPE -> TaskViewHolder.TaskCardViewHolder.from(parent)
            else -> throw ClassCastException("ViewType not recognized: $viewType")
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder.HeaderViewHolder -> {
                val item = getItem(position) as TaskDataItem.TaskHeader
                holder.bind(item)
            }
            is TaskViewHolder.TaskCardViewHolder -> {
                val item = getItem(position) as TaskDataItem.TaskCardItem
                holder.bind(item.task, taskCardListener)
            }
        }
    }

    class DataItemDiffCallback : DiffUtil.ItemCallback<TaskDataItem>() {
        override fun areItemsTheSame(oldItem: TaskDataItem, newItem: TaskDataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TaskDataItem, newItem: TaskDataItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

}
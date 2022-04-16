package uz.gita.remindme.adapters.tasks

import uz.gita.remindme.data.models.Task

sealed class TaskDataItem {

    abstract val id: Int

    data class TaskCardItem(
        val task: Task,
        override val id: Int = task.id
    ) : TaskDataItem()

    data class TaskHeader(
        val date: String,
        override val id: Int = date.length
    ): TaskDataItem()
}

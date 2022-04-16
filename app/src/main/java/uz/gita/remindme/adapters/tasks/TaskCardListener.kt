package uz.gita.remindme.adapters.tasks

import uz.gita.remindme.data.models.Task

class TaskCardListener(
    val cardListener: (task: Task) -> Unit,
    val checkBoxListener: (task: Task) -> Unit
) {
    fun cardListenerF(task: Task) {
        cardListener(task)
    }

    fun checkboxListenerF(task: Task) {
        checkBoxListener(task)
    }
}
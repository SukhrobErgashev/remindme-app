package uz.gita.remindme.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.remindme.adapters.tasks.TaskDataItem
import uz.gita.remindme.adapters.notes.NoteDataItem
import uz.gita.remindme.data.models.Note
import uz.gita.remindme.data.models.Task
import uz.gita.remindme.utils.toDateString

fun List<Task>.toListOfDataItem(): List<TaskDataItem> {
    val grouping = this.groupBy { taskCard ->
        taskCard.dueCalendar.toDateString()
    }

    val listDataItem = mutableListOf<TaskDataItem>()
    grouping.forEach { mapEntry ->
        listDataItem.add(TaskDataItem.TaskHeader(mapEntry.key))
        listDataItem.addAll(
            mapEntry.value.map { taskCard ->
                TaskDataItem.TaskCardItem(taskCard)
            }
        )
    }

    return listDataItem
}

fun List<Note>.toListOfNoteDataItem(): List<NoteDataItem> {
    val grouping = this.groupBy { noteCard ->
        noteCard.dateNote
    }

    val listDataItem = mutableListOf<NoteDataItem>()
    grouping.forEach { mapEntry ->
        listDataItem.add(NoteDataItem.NoteHeader(mapEntry.key))
        listDataItem.addAll(
            mapEntry.value.map { noteCard ->
                NoteDataItem.NoteCardItem(noteCard)
            }
        )
    }

    return listDataItem
}

fun <T> LiveData<T>.toMutableLiveData(): MutableLiveData<T> {
    val mediatorLiveData = MediatorLiveData<T>()
    mediatorLiveData.addSource(this) {
        mediatorLiveData.value = it
    }
    return mediatorLiveData
}

fun Boolean.toInt() = if (this) 1 else 0
fun Int.toBoolean() = this == 1
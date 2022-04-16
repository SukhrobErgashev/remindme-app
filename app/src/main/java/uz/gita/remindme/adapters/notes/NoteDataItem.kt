package uz.gita.remindme.adapters.notes

import uz.gita.remindme.data.models.Note

/**
 * This sealed class is an abstraction of RecyclerView item types.
 * NoteCardItem: A Note card with note details.
 * NoteHeader: A string list divider used to separate notes by created date.
 */

sealed class NoteDataItem {

    abstract val id: Int

    data class NoteCardItem(
        val note: Note,
        override val id: Int = note.id
    ): NoteDataItem()

    data class NoteHeader(
        val date: String,
        override val id: Int = date.length
    ): NoteDataItem()
}

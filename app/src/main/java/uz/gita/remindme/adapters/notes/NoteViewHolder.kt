package uz.gita.remindme.adapters.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import uz.gita.remindme.data.models.Note
import uz.gita.remindme.databinding.ItemDateSectionBinding
import uz.gita.remindme.databinding.ItemNotesBinding

/**
 * This class holds the different ViewHolders fot NotesAdapter. I chose to use
 * a sealed class to limit the extent of this class.
 */

sealed class NoteViewHolder(open val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class NoteCardViewHolder(override val binding: ItemNotesBinding) : NoteViewHolder(binding) {

        fun bind(note: NoteDataItem.NoteCardItem) {
            with(binding) {
                titleNote.text = note.note.titleNote
                bodyNote.text = note.note.bodyNote
                timeNote.text = note.note.timeNote
                dateNote.text = note.note.dateNote
            }
        }

        /**
         * This companion object allows the ListAdapter to get an instance of the ViewHolder
         * by just passing the parent.
         */
        companion object {
            fun from(parent: ViewGroup): NoteViewHolder {
                val binding = ItemNotesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return NoteCardViewHolder(binding)
            }
        }

    }

    /**
     * This class represents a ViewHolder for the NoteHeader
     */
    class NoteHeaderViewHolder(override val binding: ItemDateSectionBinding) :
        NoteViewHolder(binding) {

        fun bind(item: NoteDataItem.NoteHeader) {
            binding.dateSection.text = item.date
        }

        companion object {
            fun from(parent: ViewGroup): NoteViewHolder {
                val binding = ItemDateSectionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return NoteHeaderViewHolder(binding)
            }
        }

    }

}
package uz.gita.remindme.adapters.notes

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.gita.remindme.adapters.toListOfNoteDataItem
import uz.gita.remindme.data.models.Note
import java.lang.ClassCastException

/**
 * The NotesAdapter implements the ListAdapter interface with DiffUtil.
 * Both 'layout inflation' and 'data binding' occur in the ViewHolder class.
 * Instead of receiving a list from the Note, this adapter expects a List<NoteDataItem>,
 * which in the superclass of the concrete data types (NoteCardItem or NoteHeader)
 */
class NotesAdapter : ListAdapter<NoteDataItem, NoteViewHolder>(DataItemDiffCallback()) {

    /**
     * Declare a specific coroutine scope for the Adapter. It will used for
     * situations where it is necessary to process the list
     */
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    var listener: ((Note) -> Unit)? = null

    /**
     * These constants indicate the type of item that will be returned
     */
    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val NOTE_CARD_VIEW_TYPE = 1
    }

    /**
     * Determine the ViewHolder type from the object in the list position
     */
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NoteDataItem.NoteHeader -> HEADER_VIEW_TYPE
            is NoteDataItem.NoteCardItem -> NOTE_CARD_VIEW_TYPE
        }
    }

    /**
     * This function get list as List<Note>
     * Create new list as NoteDataItem and add NoteHeader obj.s to the desired location
     * Submit new list to the adapter
     */
    fun submitNotesList(list: List<Note>?) {
        adapterScope.launch {
            val listDataItem = list?.toListOfNoteDataItem()
            withContext(Dispatchers.Main) {
                submitList(listDataItem)
            }
        }
    }

    /**
     * Get single Note Object from the list
     */
    fun getSingleNote(position: Int): Note {
        val item = getItem(position) as NoteDataItem.NoteCardItem
        return item.note
    }

    /**
     * This class implements the DiffUtil.ItemCallback<T> interface.
     */
    class DataItemDiffCallback : DiffUtil.ItemCallback<NoteDataItem>() {
        override fun areItemsTheSame(oldItem: NoteDataItem, newItem: NoteDataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteDataItem, newItem: NoteDataItem): Boolean {
            return oldItem.id == newItem.id
        }

    }

    /**
     * These constants indicate the type of item that will be returned
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> NoteViewHolder.NoteHeaderViewHolder.from(parent)
            NOTE_CARD_VIEW_TYPE -> NoteViewHolder.NoteCardViewHolder.from(parent)
            else -> throw ClassCastException("ViewType not recognized $viewType")
        }
    }

    /**
     * Selects data binding according to the ViewHolder type.
     */
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        when (holder) {
            is NoteViewHolder.NoteHeaderViewHolder -> {
                val item = getItem(position) as NoteDataItem.NoteHeader
                holder.bind(item)
            }
            is NoteViewHolder.NoteCardViewHolder -> {
                val item = getItem(position) as NoteDataItem.NoteCardItem
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    listener?.invoke(item.note)
                }
            }
        }
    }

//    fun submitList(list: ArrayList<Note>) {
//        val notesDiffUtil = BindingAdapters(data, list)
//        val diffUtilResult = DiffUtil.calculateDiff(notesDiffUtil)
//        data = list
//        diffUtilResult.dispatchUpdatesTo(this)
//    }


}
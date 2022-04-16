package uz.gita.remindme.fragments.notes

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.remindme.R
import uz.gita.remindme.data.models.Note
import uz.gita.remindme.databinding.FragmentEditNoteBinding
import java.text.DateFormat
import java.util.*

class EditNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()
    private lateinit var currentNote: Note

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        currentNote = EditNoteFragmentArgs.fromBundle(requireArguments()).note
        binding.apply {
            autoCompleteTextView.setText(currentNote.titleNote)
            bodyEditNote.setText(currentNote.bodyNote)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val titles = resources.getStringArray(R.array.titles)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, titles)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_button -> {
                saveEditedNote()
            }
            R.id.delete_button -> {
                deleteCurrentNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveEditedNote() {
        val date = Calendar.getInstance().time
        val editedNote = Note(
            id = currentNote.id,
            titleNote = binding.autoCompleteTextView.text.toString(),
            bodyNote = binding.bodyEditNote.text.toString(),
            timeNote = currentNote.timeNote,
            dateNote = currentNote.dateNote
//            timeNote = DateFormat.getTimeInstance().format(date).toString(),
//            dateNote = DateFormat.getDateInstance().format(date).toString()
        )
        viewModel.updateNote(editedNote)
        findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToNotesFragment())
    }

    private fun deleteCurrentNote() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Delete Note!")
        alertDialog.setMessage("Are you sure delete current Note?")
        alertDialog.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteNote(currentNote)
            findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToNotesFragment())
        }
        alertDialog.setNegativeButton("No") { _, _ -> }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
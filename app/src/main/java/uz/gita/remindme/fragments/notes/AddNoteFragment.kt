package uz.gita.remindme.fragments.notes

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.remindme.R
import uz.gita.remindme.data.models.Note
import uz.gita.remindme.databinding.FragmentAddNoteBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val titles = resources.getStringArray(R.array.titles)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, titles)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_button -> {
                addNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addNote() {
        if (binding.bodyAddNote.text.toString().isNotEmpty()) {
            val date = Calendar.getInstance().time
            val newNote = Note(
                id = 0,
                titleNote = binding.autoCompleteTextView.text.toString(),
                bodyNote = binding.bodyAddNote.text.toString(),
                timeNote = SimpleDateFormat("hh:mm aa", Locale.UK).format(date),
//                    timeNote = DateFormat.getTimeInstance().format(date),
                dateNote = DateFormat.getDateInstance().format(date)
            )
            viewModel.insertNote(newNote)

            findNavController().navigate(
                AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment()
            )
        } else {
            Toast.makeText(requireContext(), "Write some note", Toast.LENGTH_SHORT).show()
        }
    }

}
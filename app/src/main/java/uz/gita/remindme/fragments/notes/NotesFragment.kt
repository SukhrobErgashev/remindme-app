package uz.gita.remindme.fragments.notes

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import uz.gita.remindme.R
import uz.gita.remindme.adapters.notes.NotesAdapter
import uz.gita.remindme.data.models.Note
import uz.gita.remindme.databinding.FragmentNotesBinding

class NotesFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()

    private var notesAdapter: NotesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        notesAdapter = NotesAdapter()

        setupRecyclerview()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllNotes.observe(viewLifecycleOwner, Observer { data ->
            notesAdapter?.submitNotesList(data)
        })

        notesAdapter?.listener = {
            findNavController().navigate(
                NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(it)
            )
        }
    }

    private fun setupRecyclerview() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
//            itemAnimator = LandingAnimator().apply {
//                addDuration = 300
//            }
            adapter = this@NotesFragment.notesAdapter
        }
        swipeToDelete(binding.recyclerview)
    }

    private fun swipeToDelete(recyclerview: RecyclerView) {
        val callback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val deletedItem = notesAdapter?.getSingleNote(viewHolder.adapterPosition)
                    deletedItem?.let { viewModel.deleteNote(it) }

                    // Restore Deleted Item
                    deletedItem?.let {
                        restoreDeletedData(
                            viewHolder.itemView,
                            deletedItem,
                            viewHolder.adapterPosition
                        )
                    }
                }

            }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerview)
    }

    private fun restoreDeletedData(view: View, deletedItem: Note, position: Int) {
        val snackBar = Snackbar.make(
            view, "Note Deleted",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            viewModel.insertNote(deletedItem)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_menu, menu)

        val search = menu.findItem(R.id.search_notes)
        val searchView = search.actionView as SearchView
        //searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> {
                deleteAllNotes()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllNotes() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Delete all Notes")
        alertDialog.setMessage("Are you sure delete all notes?")
        alertDialog.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAll()
        }
        alertDialog.setNegativeButton("No") { _, _ -> }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        notesAdapter = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchThroughDatabase(newText)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery = query
        searchQuery = "%$searchQuery%"

        viewModel.searchNotes(searchQuery).observe(viewLifecycleOwner, Observer {
            it?.let {
                notesAdapter?.submitNotesList(it)
            }
        })
    }
}
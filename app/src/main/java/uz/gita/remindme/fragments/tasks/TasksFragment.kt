package uz.gita.remindme.fragments.tasks

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.remindme.R
import uz.gita.remindme.adapters.tasks.TaskCardListener
import uz.gita.remindme.adapters.tasks.TasksAdapter
import uz.gita.remindme.data.models.Task
import uz.gita.remindme.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val tasksViewModel: TasksViewModel by viewModels()
    private lateinit var tasksAdapter: TasksAdapter

    private var showCompleted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "onCreateView: ")
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        tasksAdapter = TasksAdapter(TaskCardListener(
            cardListener = {
                findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(it))
            },
            checkBoxListener = {
                it.isCompleted = !it.isCompleted
                tasksViewModel.updateTask(it)
            }
        ))

        setHasOptionsMenu(true)
        setupRecyclerView()

        tasksViewModel.currentList.observe(viewLifecycleOwner) {
            tasksAdapter.submitTasksList(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "onViewCreated: ")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("TAG", "onViewStateRestored: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        tasksViewModel.getTasks(showCompleted)
        Log.d("TAG", "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG", "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "onStop: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("TAG", "onSaveInstanceState: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "onDestroyView: ")
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = tasksAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tasks_menu, menu)

//        val searchView = menu.findItem(R.id.search_tasks).actionView as SearchView
//        searchView.isSubmitButtonEnabled = true
        //initSearchBar(searchView)
    }

//    private fun initSearchBar(searchView: SearchView) {
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    searchThroughDatabase(it)
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let {
//                    searchThroughDatabase(newText)
//                }
//                return true
//            }
//
//        })
//    }

//    private fun searchThroughDatabase(query: String) {
//        tasksViewModel.searchTasks("%$query%").observe(viewLifecycleOwner) {
//            tasksAdapter.submitTasksList(it)
//        }
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.completed -> {
                item.isChecked = !item.isChecked
                showCompleted = !showCompleted
                changeListData()
            }
//            R.id.missed -> {
//
//            }
//            R.id.delete_all_tasks -> {
//                deleteAllTasks()
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeListData() {
        tasksViewModel.getTasks(
            showCompleted
        )
    }

    private fun deleteAllTasks() {
        tasksViewModel.deleteAllTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy: ")
        _binding = null
    }
}
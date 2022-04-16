package uz.gita.remindme.fragments.tasks

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.remindme.fragments.DatePickerFragment
import uz.gita.remindme.R
import uz.gita.remindme.fragments.TimePickerFragment
import uz.gita.remindme.data.models.Task
import uz.gita.remindme.databinding.FragmentAddTaskBinding
import java.util.*

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TasksViewModel by viewModels()

    private var calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        binding.addTaskTime.setOnClickListener {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.show(childFragmentManager, "timePicker")
            timePickerFragment.listener = { hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                binding.addTaskTime.text = "$hourOfDay:$minute"
            }
        }

        binding.addTaskDate.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(childFragmentManager, "datePicker")
            newFragment.listener = { year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                binding.addTaskDate.text = "$day/$month/$year"
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val notifyTypes = resources.getStringArray(R.array.notify_types)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, notifyTypes)
        binding.addTaskNotifyType.setAdapter(arrayAdapter)

        val titles = resources.getStringArray(R.array.titles)
        val arrayAdapterTitles = ArrayAdapter(requireContext(), R.layout.item_dropdown, titles)
        binding.addTaskTitle.setAdapter(arrayAdapterTitles)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_task_button -> {
                viewModel.insertTask(
                    Task(
                        id = 0,
                        titleTask = binding.addTaskTitle.text.toString(),
                        bodyTask = binding.addTaskBody.text.toString(),
                        dueCalendar = calendar.timeInMillis,
                    )

                )

                findNavController().navigate(AddTaskFragmentDirections.actionAddTaskFragmentToTasksFragment())
            }
        }

        return super.onOptionsItemSelected(item)
    }
}


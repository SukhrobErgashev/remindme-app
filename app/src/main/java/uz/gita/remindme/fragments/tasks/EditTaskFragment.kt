package uz.gita.remindme.fragments.tasks

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uz.gita.remindme.fragments.DatePickerFragment
import uz.gita.remindme.R
import uz.gita.remindme.data.models.Task
import uz.gita.remindme.fragments.TimePickerFragment
import uz.gita.remindme.databinding.FragmentEditTaskBinding
import java.util.*

class EditTaskFragment : Fragment() {

    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TasksViewModel by viewModels()

    private var calendar = Calendar.getInstance()

    private lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        task = EditTaskFragmentArgs.fromBundle(requireArguments()).task

        binding.editTaskTime.setOnClickListener {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.show(childFragmentManager, "timePicker")
            timePickerFragment.listener = { hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                binding.editTaskTime.text = "$hourOfDay:$minute"
            }
        }

        binding.editTaskDate.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(childFragmentManager, "datePicker")
            newFragment.listener = { year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                binding.editTaskDate.text = "$day/$month/$year"
            }
        }

        setData()

        return binding.root
    }

    private fun setData() {
        binding.task = task
        binding.invalidateAll()
    }

    override fun onResume() {
        super.onResume()

        val notifyTypes = resources.getStringArray(R.array.notify_types)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, notifyTypes)
        binding.editTaskNotifyType.setAdapter(arrayAdapter)

        val titles = resources.getStringArray(R.array.titles)
        val arrayAdapterTitles = ArrayAdapter(requireContext(), R.layout.item_dropdown, titles)
        binding.editTaskTitle.setAdapter(arrayAdapterTitles)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_task_button -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }
}


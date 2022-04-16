package uz.gita.remindme.fragments.birthdays

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.remindme.R
import uz.gita.remindme.data.models.Birthday
import uz.gita.remindme.databinding.FragmentAddBirthdayBinding
import uz.gita.remindme.fragments.DatePickerFragment
import uz.gita.remindme.utils.transformIntoDatePicker
import java.util.*

class AddBirthdayFragment : Fragment() {

    private var _binding: FragmentAddBirthdayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BirthdaysViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBirthdayBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

//        binding.selectBirthdayDateInside.setOnClickListener {
//            val datePicker = DatePickerFragment()
//            datePicker.show(childFragmentManager, "Add Birth")
//            datePicker.listener = { year, month, day ->
//                val calendar: Calendar = Calendar.getInstance()
//                calendar.set(year, month, day)
//
//            }
//        }
        binding.selectBirthdayDateInside.transformIntoDatePicker(
            requireContext(),
            "dd/MM/yyyy",
            null
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, gender)
        binding.gender.setAdapter(arrayAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_task_button) {
            when {
                binding.addBirthdayName.text.toString().isEmpty() -> {
                    binding.addBirthdayName.error = "Name is empty"
                }
                binding.selectBirthdayDateInside.text.toString() == "Date" -> {
                    Toast.makeText(requireContext(), "Choose birthday date", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    insertBirthday()
                    findNavController().navigate(AddBirthdayFragmentDirections.actionAddBirthdayFragmentToBirthdaysFragment())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertBirthday() {
        viewModel.insert(
            Birthday(
                0,
                binding.addBirthdayName.text.toString(),
                binding.selectBirthdayDateInside.text.toString(),
                binding.gender.text.toString(),
                binding.remindMeSwitch.isChecked
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
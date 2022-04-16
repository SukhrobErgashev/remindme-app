package uz.gita.remindme.fragments.birthdays

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.remindme.R
import uz.gita.remindme.data.models.Birthday
import uz.gita.remindme.databinding.FragmentEditBirthdayBinding
import uz.gita.remindme.utils.toMillisecond
import uz.gita.remindme.utils.transformIntoDatePicker

class EditBirthdayFragment : Fragment() {

    private var _binding: FragmentEditBirthdayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BirthdaysViewModel by viewModels()

    private lateinit var currentBirthday: Birthday

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBirthdayBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        currentBirthday = EditBirthdayFragmentArgs.fromBundle(requireArguments()).birthday

        binding.editBirthdayDateInside.transformIntoDatePicker(
            requireContext(),
            "dd/MM/yyyy",
            currentBirthday.date.toMillisecond()
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setData()

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, gender)
        binding.editGender.setAdapter(arrayAdapter)

    }

    private fun setData() {
        binding.editBirthdayName.setText(currentBirthday.name)
        binding.editBirthdayDateInside.setText(currentBirthday.date)
        binding.editGender.setText(currentBirthday.gender)
        binding.remindMeSwitchEdit.isChecked = currentBirthday.remindMe
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_button) {
            when {
                binding.editBirthdayName.text.toString().isEmpty() -> {
                    binding.editBirthdayName.error = "Name is empty"
                }
                binding.editBirthdayDateInside.text.toString() == "Date" -> {
                    Toast.makeText(requireContext(), "Choose birthday date", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    insertBirthday()
                    findNavController().navigate(EditBirthdayFragmentDirections.actionEditBirthdayFragmentToBirthdaysFragment())
                }
            }
        } else if (item.itemId == R.id.delete_button) {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Delete Birthday")
            alertDialog.setMessage("Are you sure delete ${currentBirthday.name}\'s birthday?")
            alertDialog.setPositiveButton("Yes") { _, _ ->
                viewModel.delete(currentBirthday)
                findNavController().navigate(EditBirthdayFragmentDirections.actionEditBirthdayFragmentToBirthdaysFragment())
            }
            alertDialog.setNegativeButton("No") { _, _ -> }
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertBirthday() {
        viewModel.update(
            Birthday(
                currentBirthday.id,
                binding.editBirthdayName.text.toString(),
                binding.editBirthdayDateInside.text.toString(),
                binding.editGender.text.toString(),
                binding.remindMeSwitchEdit.isChecked
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
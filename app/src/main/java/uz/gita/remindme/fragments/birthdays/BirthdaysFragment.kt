package uz.gita.remindme.fragments.birthdays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.remindme.R
import uz.gita.remindme.adapters.birthdays.BirthdaysAdapter
import uz.gita.remindme.databinding.FragmentBirthdaysBinding

class BirthdaysFragment : Fragment() {

    private var _binding: FragmentBirthdaysBinding? = null
    private val binding get() = _binding!!

    private val birthdaysViewModel: BirthdaysViewModel by viewModels()

    private var adapter: BirthdaysAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBirthdaysBinding.inflate(inflater, container, false)

        adapter = BirthdaysAdapter()

        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        birthdaysViewModel.getBirthdays.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }

        adapter?.listener = {
            findNavController().navigate(BirthdaysFragmentDirections.actionBirthdaysFragmentToEditBirthdayFragment(it))
        }

    }

    private fun setupRecyclerView() {
        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@BirthdaysFragment.adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        adapter = null
    }
}
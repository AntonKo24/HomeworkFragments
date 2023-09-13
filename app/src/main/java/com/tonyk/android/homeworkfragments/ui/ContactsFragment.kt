package com.tonyk.android.homeworkfragments.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonyk.android.homeworkfragments.R
import com.tonyk.android.homeworkfragments.databinding.FragmentContactsBinding
import com.tonyk.android.homeworkfragments.util.DeviceChecker
import com.tonyk.android.homeworkfragments.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val contactsViewModel: ContactsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ContactsListAdapter { contact ->
            val detailsFragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable("contact", contact)
            detailsFragment.arguments = args
            if (DeviceChecker.isTablet(requireContext())) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.detailsFragmentContainer, detailsFragment)
                    .commit()
            }  // Для планшета добавлять в бекстек не стал. Чтобы рядом отображалось только окно нажатое последний раз.
            // С постоянно открытым списком контактов можно было бы нагрузить большой стек детелей, если их не закрывать, что мне не понравилось. Легко изменить, если что.
            else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, detailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactsViewModel.contactsList.collect {
                    adapter.submitList(it)
                }
            }
        }
        binding.contactsRcv.layoutManager = LinearLayoutManager(context)
        binding.contactsRcv.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
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
import com.tonyk.android.homeworkfragments.viewmodel.ContactsViewModel
import com.tonyk.android.homeworkfragments.R
import com.tonyk.android.homeworkfragments.databinding.FragmentContactsBinding
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
        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ContactsListAdapter { contact ->
            val detailsFragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable("contact", contact)
            detailsFragment.arguments = args
            if (resources.configuration.screenWidthDp >= 600) {
                parentFragmentManager.beginTransaction()
                    .add(R.id.detailsFragmentContainer, detailsFragment)
                    .addToBackStack(null)
                    .commit()
             }
            else {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, detailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactsViewModel.contactsList.collect{
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
package com.tonyk.android.homeworkfragments.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonyk.android.homeworkfragments.R
import com.tonyk.android.homeworkfragments.databinding.FragmentContactsBinding
import com.tonyk.android.homeworkfragments.model.Contact
import com.tonyk.android.homeworkfragments.util.ContactsListDecorationItem
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

        val adapter = ContactsListAdapter(
            onContactClicked = { contact ->
                val detailsFragment = DetailsFragment()
                val args = Bundle()
                args.putParcelable("contact", contact)
                detailsFragment.arguments = args
                if (DeviceChecker.isTablet(requireContext())) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.detailsFragmentContainer, detailsFragment)
                        .commit()
                }
                // Для планшета добавлять в бекстек не стал. Чтобы рядом отображалось только окно нажатое последний раз.
                // С постоянно открытым списком контактов можно было бы нагрузить большой стек детелей, если их не закрывать, что мне не понравилось. Легко изменить, если что.
                else {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, detailsFragment)
                        .addToBackStack(null)
                        .commit()
                }
            },
            onContactLongClicked = { contact -> showDeleteConfirmationDialog(contact) })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactsViewModel.contactsList.collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.apply {

            contactsRcv.layoutManager = LinearLayoutManager(context)
            contactsRcv.adapter = adapter
            contactsRcv.addItemDecoration(ContactsListDecorationItem(requireContext()))

            searchContact.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { performSearch(it, adapter) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { performSearch(it, adapter) }
                    return true
                }
            })
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDeleteConfirmationDialog(contact: Contact) {

        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Contact")
            .setMessage("Are you sure you want to delete this contact?")
            .setPositiveButton("Delete") { _, _ ->
                contactsViewModel.deleteContact(contact.id)
                Toast.makeText(context, "Contact Deleted!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { _, _ ->
            }
            .create()

        alertDialog.show()
    }

    private fun performSearch(query: String, adapter: ContactsListAdapter) {
        val filteredContacts = contactsViewModel.contactsList.value.filter { contact ->
            contact.name.contains(query, ignoreCase = true) || contact.surname.contains(
                query,
                ignoreCase = true
            )
        }
        adapter.submitList(filteredContacts)
    }
}
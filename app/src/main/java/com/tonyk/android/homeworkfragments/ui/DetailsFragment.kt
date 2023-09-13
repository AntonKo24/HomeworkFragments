package com.tonyk.android.homeworkfragments.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.tonyk.android.homeworkfragments.R
import com.tonyk.android.homeworkfragments.databinding.FragmentDetailsBinding
import com.tonyk.android.homeworkfragments.model.Contact
import com.tonyk.android.homeworkfragments.util.DeviceChecker
import com.tonyk.android.homeworkfragments.viewmodel.ContactsViewModel


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val contactsViewModel: ContactsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contact: Contact? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("contact", Contact::class.java)
        } else {
            arguments?.getParcelable("contact") as Contact?
        }

        contact?.let { setupUI(it) }

        binding.saveButton.setOnClickListener {
            if (contact != null) {
                saveChanges(contact)
            }
        }
        binding.closeButton.setOnClickListener {
            closeFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI(contact: Contact) {
        binding.apply {
            contactName.setText(contact.name)
            contactSurname.setText(contact.surname)
            contactPhoto.load("https://picsum.photos/200/300?random=${contact.photo}")
            contactPhone.setText(contact.phoneNumber)
            contactPhone.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.contactPhone.windowToken, 0)
                    return@setOnEditorActionListener true
                }
                false
            }
            // Изменил немного поведение после ввода номера телефона. Чтобы пропадала клава и фокус не падал вниз. Так кажется более юзер френдли. Можно и убрать.
        }
    }

    private fun closeFragment() {
        if (DeviceChecker.isTablet(requireContext())) {
            val existingDetailsFragment =
                parentFragmentManager.findFragmentById(R.id.detailsFragmentContainer)
            if (existingDetailsFragment != null) {
                parentFragmentManager.beginTransaction()
                    .remove(existingDetailsFragment)
                    .commit()
            }
        } else parentFragmentManager.popBackStack()
    }
    // На планшетах просто удаляю фрагмент из контейнера (который положил без стека), а на телефонах из бекстека.
    // Изначально я сделал стек и для планшета, но так как-то поприятнее поведение мне кажется. Заодно попрактиковался с remove фрагментом в FragmentManager.

    private fun saveChanges(contact: Contact) {
        val editedName = binding.contactName.text.toString()
        val editedSurname = binding.contactSurname.text.toString()
        val editedPhoneNumber = binding.contactPhone.text.toString()
        contactsViewModel.updateContacts(
            contact.id,
            editedName,
            editedSurname,
            editedPhoneNumber
        )
        closeFragment()
        Toast.makeText(context, "Contact changes saved!", Toast.LENGTH_SHORT).show()
    }
}
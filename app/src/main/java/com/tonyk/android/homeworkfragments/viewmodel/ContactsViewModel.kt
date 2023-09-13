package com.tonyk.android.homeworkfragments.viewmodel

import androidx.lifecycle.ViewModel
import com.tonyk.android.homeworkfragments.data.ContactsData
import com.tonyk.android.homeworkfragments.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class ContactsViewModel : ViewModel() {

    private val _contactsList = MutableStateFlow(ContactsData.contactList)
    val contactsList: StateFlow<List<Contact>> = _contactsList

    fun updateContacts(
        id: UUID,
        editedName: String,
        editedSurname: String,
        editedPhoneNumber: String
    ) {
        val updatedContacts = _contactsList.value.toMutableList()

        val index = updatedContacts.indexOfFirst { it.id == id }
        if (index != -1) {
            updatedContacts[index] = updatedContacts[index].copy(
                name = editedName,
                surname = editedSurname,
                phoneNumber = editedPhoneNumber
            )
            _contactsList.value = updatedContacts
        }
    }
    fun deleteContact(contactId: UUID) {
        _contactsList.value = _contactsList.value.filter { it.id != contactId }.toMutableList()
    }
}
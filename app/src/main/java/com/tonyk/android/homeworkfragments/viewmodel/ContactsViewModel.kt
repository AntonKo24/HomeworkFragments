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

    fun updateContacts(id: UUID, editedName: String, editedSurname: String, editedPhoneNumber: String) {
        _contactsList.value = _contactsList.value.map { contact ->
            if (contact.id == id) {
                contact.copy(
                    name = editedName,
                    surname = editedSurname,
                    phoneNumber = editedPhoneNumber
                )
            } else {
                contact
            }
        }
    }
}
// Можно было бы из без вьюмодели сделать, но она совсем небольшая и с помощью StateFlow легко следить за изменениями в списке.

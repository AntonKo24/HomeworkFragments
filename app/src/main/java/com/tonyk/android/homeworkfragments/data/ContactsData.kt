package com.tonyk.android.homeworkfragments.data

import com.tonyk.android.homeworkfragments.model.Contact
import java.util.UUID

object ContactsData {
    val contactList = listOf(
        Contact(UUID.randomUUID(), "Тарас", "Шевченко", "123456789"),
        Contact(UUID.randomUUID(), "Леся", "Українка", "987654321"),
        Contact(UUID.randomUUID(), "Іван", "Франко", "555555555"),
        Contact(UUID.randomUUID(), "Василь", "Стус", "414142414")
    )
}
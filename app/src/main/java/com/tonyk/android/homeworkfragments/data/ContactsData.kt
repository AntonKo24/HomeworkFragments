package com.tonyk.android.homeworkfragments.data

import com.tonyk.android.homeworkfragments.model.Contact
import java.util.UUID
import kotlin.random.Random

object ContactsData {
    val contactList = mutableListOf<Contact>()

    init {
        repeat(100) {
            val id = UUID.randomUUID()
            val name = generateRandomName()
            val surname = generateRandomSurname()
            val phoneNumber = generateRandomPhoneNumber()
            val photoID = it
            contactList.add(Contact(id, name, surname, phoneNumber, photoID))
        }
    }

    private fun generateRandomName(): String {
        val names = listOf(
            "Nikita",
            "Oto",
            "Vladislav",
            "Katerina",
            "Alex",
            "Maxim",
            "Olga",
            "Anton"
        )
        return names.random()
    }

    private fun generateRandomSurname(): String {
        val surnames = listOf(
            "Shevchenko",
            "Bondarenko",
            "Petrenko",
            "Melnik",
            "Pavlenko",
            "Sidorenko",
            "Koval"
        )
        return surnames.random()
    }

    private fun generateRandomPhoneNumber(): String {
        val sb = StringBuilder("+380")
        repeat(7) {
            sb.append(Random.nextInt(10))
        }
        return sb.toString()
    }

}

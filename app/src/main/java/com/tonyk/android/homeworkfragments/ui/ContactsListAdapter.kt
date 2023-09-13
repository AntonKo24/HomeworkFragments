package com.tonyk.android.homeworkfragments.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tonyk.android.homeworkfragments.databinding.ContactItemBinding
import com.tonyk.android.homeworkfragments.model.Contact

class ContactViewHolder(
    private val binding: ContactItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        contact: Contact,
        onContactClicked: (contact: Contact) -> Unit,
        onContactLongClicked: (contact: Contact) -> Unit
    ) {
        binding.apply {
            nameTxt.text = contact.name
            surnameTxt.text = contact.surname
            phoneNumberTxt.text = contact.phoneNumber
            photoImage.load("https://picsum.photos/200/300?random=${contact.photo}")
            root.setOnClickListener {
                onContactClicked(contact)
            }
            root.setOnLongClickListener {
                onContactLongClicked(contact)
                return@setOnLongClickListener true
            }
        }
    }
}

class ContactsListAdapter(
    private val onContactClicked: (contact: Contact) -> Unit,
    private val onContactLongClicked: (contact: Contact) -> Unit
) : ListAdapter<Contact, ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContactViewHolder(ContactItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact, onContactClicked, onContactLongClicked)
    }
}

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}
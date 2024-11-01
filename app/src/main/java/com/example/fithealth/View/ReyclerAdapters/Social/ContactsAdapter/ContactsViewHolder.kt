package com.example.fithealth.View.ReyclerAdapters.Social.ContactsAdapter

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.R
import com.example.fithealth.View.Activitys.Social.MessageActivity
import com.example.fithealth.databinding.ItemContactosBinding

class ContactsViewHolder(private val binding: ItemContactosBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(contact: Contact) {
        userName(contact.userName)
        userIcon(contact.icon)
        goChatFragment(contact.contactId)
    }

    private fun goChatFragment(contactId: String) {
        binding.tvNombreContacto.setOnClickListener {
            val context = it.context
            context.startActivity(Intent(context, MessageActivity::class.java).putExtra("contactId", contactId))
        }
    }


    private fun userName(name: String) {
        binding.tvNombreContacto.text = name
    }

    private fun userIcon(icon: Int) {
        binding.cvImagenPerfil.apply {
            if (icon != 0) setImageResource(icon)
            else setImageResource(R.drawable.ic_logo_contacto)
        }
    }

}
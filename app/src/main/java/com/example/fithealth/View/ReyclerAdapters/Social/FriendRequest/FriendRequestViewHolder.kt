package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.FriendRequest

import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.databinding.ItemUserRequestBinding

class FriendRequestViewHolder(private val binding: ItemUserRequestBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(userSearch: UserSearch, onAcceptRequest: (UserSearch) -> Unit, onRejectRequst: (String) -> Unit) {
        binding.tvUserName.text = userSearch.userName

        binding.btnAcceptRequest.setOnClickListener {
            onAcceptRequest(userSearch)
        }

        binding.btnRejectRequest.setOnClickListener {
            onRejectRequst(userSearch.contactId)
        }
    }




}
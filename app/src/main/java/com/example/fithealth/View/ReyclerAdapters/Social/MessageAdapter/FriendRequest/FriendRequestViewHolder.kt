package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.FriendRequest

import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.databinding.ItemUserRequestBinding

class FriendRequestViewHolder(private val binding: ItemUserRequestBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(searchUser: SearchUser, onAcceptRequest: (SearchUser) -> Unit, onRejectRequst: (String) -> Unit) {
        binding.tvUserName.text = searchUser.userName

        binding.btnAcceptRequest.setOnClickListener {
            onAcceptRequest(searchUser)
        }

        binding.btnRejectRequest.setOnClickListener {
            onRejectRequst(searchUser.id)
        }
    }




}
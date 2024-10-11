package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.BusquedaSocial

import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.databinding.ItemUserSearchBinding

class UserSearchViewHolder(private val binding: ItemUserSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(itemUserSearch: SearchUser, onUserRequest: (user: SearchUser) -> Unit) {
        binding.apply {
            itemUserSearch.let { userSearch ->
                tvUserName.text = userSearch.userName
                tvUserHashtag.text = userSearch.uniqueName

                setUserIcon(userSearch)

                btnUserRequest.setOnClickListener {
                    onUserRequest(userSearch)
                }
            }
        }

    }

    private fun setUserIcon(userSearch: SearchUser) {

    }


}
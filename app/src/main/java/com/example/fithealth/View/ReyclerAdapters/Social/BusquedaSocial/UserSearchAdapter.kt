package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.BusquedaSocial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.databinding.ItemUserSearchBinding

class UserSearchAdapter (private var userSearchList : List<UserSearch>, private val onUserRequest : (user : UserSearch)-> Unit) : RecyclerView.Adapter<UserSearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        val binding = ItemUserSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        val itemUserSearch = userSearchList[position]

        holder.bind(itemUserSearch, onUserRequest)
    }

    override fun getItemCount(): Int = userSearchList.size

    fun updateList(newList : List<UserSearch>){
        userSearchList = newList
        notifyDataSetChanged()
    }
}

package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.FriendRequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.View.ReyclerAdapters.DiffUtils.DiffUtilsSearchUser
import com.example.fithealth.databinding.ItemUserRequestBinding

class FriendRequestAdapter(
    private var friendRequests: List<UserSearch>,
    private val onAcceptRequest: (UserSearch) -> Unit,
    private val onRejectRequst: (String) -> Unit
) :
    RecyclerView.Adapter<FriendRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val binding =
            ItemUserRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendRequestViewHolder(binding)
    }

    override fun getItemCount(): Int = friendRequests.size

    override fun onBindViewHolder(holder: FriendRequestViewHolder, position: Int) {
        holder.bind(friendRequests[position], onAcceptRequest, onRejectRequst)
    }

    fun updateList(newList: List<UserSearch>) {
        //las diferencias entre las listas
        val diffCallback = DiffUtilsSearchUser(friendRequests, newList)
        val diffResults = DiffUtil.calculateDiff(diffCallback)

        friendRequests = newList
        diffResults.dispatchUpdatesTo(this)
    }


}

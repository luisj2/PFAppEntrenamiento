package com.example.fithealth.Model.Utils

import androidx.recyclerview.widget.DiffUtil
import com.example.fithealth.Model.DataClass.UserSearch

class DiffUtilsSearchUser(
    private val oldList: List<UserSearch>,
    private val newList: List<UserSearch>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].contactId == newList[newItemPosition].contactId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        checkSameContents(oldList[oldItemPosition], newList[newItemPosition])

    private fun checkSameContents(oldUser: UserSearch, newUser: UserSearch): Boolean =
        oldUser.userName == newUser.userName && oldUser.uniqueName == newUser.uniqueName && oldUser.icon == newUser.icon

}
package com.example.fithealth.Model.Utils

import androidx.recyclerview.widget.DiffUtil
import com.example.fithealth.Model.DataClass.SearchUser

class DiffUtilsSearchUser(
    private val oldList: List<SearchUser>,
    private val newList: List<SearchUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        checkSameContents(oldList[oldItemPosition], newList[newItemPosition])

    private fun checkSameContents(oldUser: SearchUser, newUser: SearchUser): Boolean =
        oldUser.userName == newUser.userName && oldUser.uniqueName == newUser.uniqueName && oldUser.icon == newUser.icon

}
package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter

import com.example.fithealth.View.ReyclerAdapters.BaseViewHolder
import com.example.fithealth.databinding.ItemMessageDayBinding

class MessageDayViewHolder (private val binding : ItemMessageDayBinding) : BaseViewHolder<ItemMessageDayBinding,String>(binding) {
    override fun bind(item: String) {
        binding.tvDia.text = item
    }
}
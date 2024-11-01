package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter

import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.View.ReyclerAdapters.BaseViewHolder
import com.example.fithealth.databinding.ItemMessageReceivedBinding

class MessageReceivedViewHolder(private val binding : ItemMessageReceivedBinding) : BaseViewHolder<ItemMessageReceivedBinding,Message>(binding) {
    override fun bind(item: Message) {
        changeMessageContent(item.messageContent)
        changeMessageHour(item.messageHour)
    }

    private fun changeMessageHour(messageHour: String) {
        binding.tvMessageHour.text = messageHour
    }

    private fun changeMessageContent(messageContent: String) {
        binding.tvMessageContent.text = messageContent
    }
}
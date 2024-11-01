package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter

import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.View.ReyclerAdapters.BaseViewHolder
import com.example.fithealth.databinding.ItemMessageSentBinding


class MessageSentViewHolder (private val binding : ItemMessageSentBinding)  : BaseViewHolder<ItemMessageSentBinding,Message>(binding){
    override fun bind(item: Message) {
        changeMessageContent(item.messageContent)
        changeMessageHour(item.messageHour)
    }

    private fun changeMessageHour(messageHour: String) {
        binding.tvHoraEnviado.text = messageHour
    }

    private fun changeMessageContent(messageContent: String) {
        binding.tvMessageContent.text = messageContent
    }


}
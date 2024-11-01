package com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.View.ReyclerAdapters.BaseViewHolder
import com.example.fithealth.databinding.ItemMessageDayBinding
import com.example.fithealth.databinding.ItemMessageReceivedBinding
import com.example.fithealth.databinding.ItemMessageSentBinding

class MessageAdapter(elementList: List<Any> = emptyList()) :
    RecyclerView.Adapter<BaseViewHolder<*,*>>() {

    private var mutableElementList: MutableList<Any> = elementList.toMutableList()

    private companion object {
        const val MESSAGE_SENT = 1
        const val MESSAGE_RECEIVED = 2
        const val MESSAGE_DAY = 3
    }

    override fun getItemViewType(position: Int): Int {
        val item = mutableElementList[position]
        return when (item) {
            is Message -> if (item.messageMine) MESSAGE_SENT else MESSAGE_RECEIVED
            else -> MESSAGE_DAY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*,*> {

        val binding : ViewBinding
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType){
            MESSAGE_SENT->{
                binding = ItemMessageSentBinding.inflate(inflater,parent,false)
                MessageSentViewHolder(binding)
            }
            MESSAGE_RECEIVED->{
                binding = ItemMessageReceivedBinding.inflate(inflater,parent,false)
                MessageReceivedViewHolder(binding)
            }
            else->{
                binding = ItemMessageDayBinding.inflate(inflater,parent,false)
                MessageDayViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = mutableElementList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*, *>, position: Int) {
        val item = mutableElementList[position]

        when (holder) {
            is MessageReceivedViewHolder -> holder.bind(item as Message)
            is MessageSentViewHolder -> holder.bind(item as Message)
            is MessageDayViewHolder -> holder.bind(item as String)
            else -> throw IllegalArgumentException("Unsupported ViewHolder type")
        }
    }



    fun addMessage(message: Message) {
        mutableElementList.add(message)
        notifyDataSetChanged()
    }

    fun replaceList(newList: List<Any>) {
        mutableElementList = newList.toMutableList()
        notifyDataSetChanged()
    }


}
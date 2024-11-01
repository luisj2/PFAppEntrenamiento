package com.example.fithealth.View.ReyclerAdapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<out T : ViewBinding,U>(private val binding: T) :
    RecyclerView.ViewHolder(binding.root){
        abstract fun bind (item : U)

}



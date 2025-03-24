package com.example.fithealth.View.SpinnerAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.fithealth.databinding.ItemsCustomSpinnerBinding

class SpinnerAdapterCustom(context: Context, private val items: List<Pair<String, Int>>) :
    ArrayAdapter<Pair<String, Int>>(context, android.R.layout.simple_spinner_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        getIconTypeExerciseView(position, convertView, parent)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        getIconTypeExerciseView(position, convertView, parent)

    private fun getIconTypeExerciseView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val binding = if (convertView == null) inflateBinding(parent) else reuseBinding(convertView)

        val item = getItem(position)

        binding.apply {
            tvExerciseName.text = item?.first ?: "???"
            ivTypeExercise.setImageResource(item?.second ?: 0)
        }
        return binding.root
    }

    private fun reuseBinding(convertView: View): ItemsCustomSpinnerBinding =
        convertView.tag as ItemsCustomSpinnerBinding

    private fun inflateBinding(parent: ViewGroup): ItemsCustomSpinnerBinding =
        createBinding(parent).apply { root.tag = this }


    private fun createBinding(parent: ViewGroup): ItemsCustomSpinnerBinding =
        ItemsCustomSpinnerBinding.inflate(
            LayoutInflater.from(context), parent, false
        )

    fun getTypeByPosition(position: Int) = items[position].first
}
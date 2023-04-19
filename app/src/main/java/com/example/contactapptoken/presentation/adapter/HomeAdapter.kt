package com.example.contactapptoken.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapptoken.data.source.remote.response.AllContacts
import com.example.contactapptoken.data.source.remote.response.GetAllContactsResponse
import com.example.contactapptoken.databinding.ItemContactBinding

class HomeAdapter: ListAdapter<AllContacts,HomeAdapter.HomeViewHolder>(DIFF_CALL_BACK) {

    private var onClick: ((AllContacts) -> Unit)? = null
    fun setOnClick(block: (AllContacts) -> Unit) {
        onClick = block
    }

    inner class HomeViewHolder(private val binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonMore.setOnClickListener {
                onClick?.invoke(currentList[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(allContacts: AllContacts) {
            binding.phone.text = allContacts.phone
            binding.name.text = "${allContacts.firstName} ${allContacts.lastName}"
        }
    }


    companion object {
        private val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<AllContacts>() {
            override fun areItemsTheSame(
                oldItem: AllContacts,
                newItem: AllContacts
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AllContacts,
                newItem: AllContacts
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
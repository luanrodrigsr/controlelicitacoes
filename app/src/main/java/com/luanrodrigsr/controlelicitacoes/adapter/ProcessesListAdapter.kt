package com.luanrodrigsr.controlelicitacoes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luanrodrigsr.controlelicitacoes.data.model.Process
import com.luanrodrigsr.controlelicitacoes.databinding.ItemListProcessBinding

class ProcessListAdapter(
    private val onItemClicked: (Process) -> Unit
) : ListAdapter<Process, ProcessListAdapter.ProcessViewHolder>(DiffCallback) {

    class ProcessViewHolder(
        private val binding: ItemListProcessBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(process: Process) {
            binding.textviewCity.text = process.city
            binding.textviewDate.text = process.date
            binding.textviewDescription.text = process.description
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProcessViewHolder {
        return ProcessViewHolder(
            ItemListProcessBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(
        holder: ProcessViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }
        holder.bind(item)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Process>() {
            override fun areItemsTheSame(oldItem: Process, newItem: Process): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Process, newItem: Process): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
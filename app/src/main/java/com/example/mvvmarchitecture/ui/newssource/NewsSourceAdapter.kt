package com.example.mvvmarchitecture.ui.newssource


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.data.model.Source
import com.example.mvvmarchitecture.databinding.NewsSourceItemLayoutBinding

class NewsSourceAdapter(
    private val sourceList: ArrayList<Source>
) : RecyclerView.Adapter<NewsSourceAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: Source) {
            binding.textViewSource.text = source.name
            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            NewsSourceItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = sourceList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(sourceList[position])

    fun addData(list: List<Source>) {
        sourceList.addAll(list)
    }

}

package com.example.mvvmarchitecture.ui.newssource


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.data.model.Source
import com.example.mvvmarchitecture.databinding.NewsSourceItemLayoutBinding
import com.example.mvvmarchitecture.utils.OnItemClickListener

class NewsSourceAdapter(
    private val sourceList: ArrayList<Source>
) : RecyclerView.Adapter<NewsSourceAdapter.DataViewHolder>() {

    lateinit var itemClickListener: OnItemClickListener<Source>

    class DataViewHolder(private val binding: NewsSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: Source, itemClickListener: OnItemClickListener<Source>) {
            binding.textViewSource.text = source.name
            itemView.setOnClickListener {
                itemClickListener(source)
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
        holder.bind(sourceList[position], itemClickListener)

    fun addData(list: List<Source>) {
        sourceList.addAll(list)
    }

}

package com.example.mvvmarchitecture.ui.language


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.data.model.Country
import com.example.mvvmarchitecture.data.model.Language
import com.example.mvvmarchitecture.databinding.NewsSourceItemLayoutBinding
import com.example.mvvmarchitecture.utils.OnItemClickListener

class LanguageAdapter(
    private val languageList: ArrayList<Language>
) : RecyclerView.Adapter<LanguageAdapter.DataViewHolder>() {
    lateinit var itemClickListener: OnItemClickListener<Language>

    class DataViewHolder(private val binding: NewsSourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Language, itemClickListener: OnItemClickListener<Language>) {
            binding.textViewSource.text = model.name
            binding.textViewSource.setOnClickListener {
                itemClickListener(model)
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

    override fun getItemCount(): Int = languageList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(languageList[position], itemClickListener)

    fun addData(list: List<Language>) {
        languageList.addAll(list)
    }

}

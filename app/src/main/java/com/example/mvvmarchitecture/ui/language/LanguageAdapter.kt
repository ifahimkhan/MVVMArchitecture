package com.example.mvvmarchitecture.ui.language


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.data.model.Language
import com.example.mvvmarchitecture.databinding.LanguageItemLayoutBinding
import com.example.mvvmarchitecture.utils.OnItemClickListener

class LanguageAdapter(
    private val languageList: ArrayList<Language>
) : RecyclerView.Adapter<LanguageAdapter.DataViewHolder>() {
    lateinit var itemClickListener: OnItemClickListener<Language>

    class DataViewHolder(private val binding: LanguageItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Language) {
            binding.textViewLanguage.text = model.name
            binding.textViewLanguage.isChecked = model.isChecked
            binding.textViewLanguage.setOnClickListener {
                model.isChecked = !model.isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LanguageItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = languageList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(languageList[position])

    fun addData(list: List<Language>) {
        languageList.addAll(list)
    }

    fun getSelectedLanguages(): List<Language> {
        return languageList.filter { it.isChecked }
    }

}

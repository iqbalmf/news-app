package net.iqbalfauzan.newsapplication.feature.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.anteraja.guardian.framework.listener.CommonActionDataListener
import net.iqbalfauzan.newsapplication.databinding.ItemCategoryBinding
import java.util.Locale

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.feature.adapter
 */
class CategoryAdapter(
    private val categories: ArrayList<String>,
    private val listener: CommonActionDataListener<String>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val cat = categories[position]
        holder.apply {
            binding.apply {
                nameCategory.text = cat.uppercase()
                layoutCategory.setOnClickListener {
                    listener.onCLick(cat)
                }
            }
        }
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateCategories(cats: List<String>){
        categories.clear()
        categories.addAll(cats)
        notifyDataSetChanged()
    }
}
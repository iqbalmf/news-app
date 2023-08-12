package net.iqbalfauzan.newsapplication.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.anteraja.guardian.framework.listener.CommonActionDataListener
import net.iqbalfauzan.newsapplication.data.model.NewsSourceResponse
import net.iqbalfauzan.newsapplication.databinding.ItemSourceBinding

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.feature.adapter
 */
class SourceAdapter(
    private val listener: CommonActionDataListener<NewsSourceResponse>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemSource(
            ItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mDiffer.currentList[position]
        if (data is SourceModel.SourceItem && holder is ItemSource) {
            holder.apply {
                binding.apply {
                    nameSource.text = data.source.name
                }
            }
        }
    }

    sealed class SourceModel {
        abstract val id: String

        data class SourceItem(val source: NewsSourceResponse) : SourceModel() {
            override val id: String
                get() = source.id.toString()
        }
    }

    inner class ItemSource(val binding: ItemSourceBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCalback = object : DiffUtil.ItemCallback<SourceModel>() {
        override fun areContentsTheSame(
            oldItem: SourceModel,
            newItem: SourceModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: SourceModel,
            newItem: SourceModel
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
    private val mDiffer = AsyncListDiffer(this, diffCalback)
    private val tempList: ArrayList<SourceModel> = arrayListOf()
    fun addSources(sources: List<NewsSourceResponse>) {
        val temp = ArrayList<SourceModel>(sources.map {
            SourceModel.SourceItem(it)
        }).toList()
        mDiffer.submitList(temp)
        tempList.apply {
            clear()
            addAll(temp)
        }
    }
}
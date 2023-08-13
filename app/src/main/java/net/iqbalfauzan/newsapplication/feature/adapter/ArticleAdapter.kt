package net.iqbalfauzan.newsapplication.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.anteraja.guardian.framework.listener.CommonActionDataListener
import net.iqbalfauzan.newsapplication.common.FLAG_IMAGE_URL
import net.iqbalfauzan.newsapplication.data.model.NewsArticleResponse
import net.iqbalfauzan.newsapplication.data.model.NewsSourceResponse
import net.iqbalfauzan.newsapplication.databinding.ItemArticleBinding

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.feature.adapter
 */
class ArticleAdapter(
    private val listener: CommonActionDataListener<NewsArticleResponse>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemArticle(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mDiffer.currentList[position]
        if (data is ArticleModel.ArticleItem && holder is ItemArticle){
            holder.apply {
                binding.apply {
                    labelTitle.text = data.article.title
                    labelAuthor.text = "Author: ${data.article.author}"
                    labelDescription.text = data.article.description
                    Glide.with(this.root.context).load(data.article.urlToImage).into(imageArticle)
                    layoutArticle.setOnClickListener {
                        listener.onCLick(data.article)
                    }
                }
            }
        }
    }

    sealed class ArticleModel {
        abstract val id: String

        data class ArticleItem(val article: NewsArticleResponse) : ArticleModel() {
            override val id: String
                get() = article.title
        }
    }

    inner class ItemArticle(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ArticleModel>() {
        override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
    private val mDiffer = AsyncListDiffer(this, diffCallback)
    private val tempList: ArrayList<ArticleModel> = arrayListOf()

    fun addSources(sources: List<NewsArticleResponse>) {
        val temp = ArrayList<ArticleModel>(sources.map {
            ArticleModel.ArticleItem(it)
        }).toList()
        mDiffer.submitList(temp)
        tempList.apply {
            clear()
            addAll(temp)
        }
    }
}

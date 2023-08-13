package net.iqbalfauzan.newsapplication.feature.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.anteraja.guardian.framework.listener.CommonActionDataListener
import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.NewsArticleResponse
import net.iqbalfauzan.newsapplication.databinding.FragmentSearchBinding
import net.iqbalfauzan.newsapplication.feature.adapter.ArticleAdapter
import net.iqbalfauzan.newsapplication.feature.viewmodel.ArticleVm

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.feature.fragment
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding
    private lateinit var articleAdapter: ArticleAdapter
    private val articleVm: ArticleVm by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        bindVm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun onBindView() {
        binding?.apply {
            searchNews.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
                if (id == EditorInfo.IME_ACTION_SEARCH){
                    if (searchNews.text.isNotEmpty() && searchNews.text.toString().length > 3){
                        articleVm.getArticlesBySearch(searchNews.text.toString().trim())
                    }
                    return@OnEditorActionListener true
                }
                false
            })
            articleAdapter = ArticleAdapter(
                object : CommonActionDataListener<NewsArticleResponse> {
                    override fun onCLick(data: NewsArticleResponse) {
                        Log.d("ARTICLE", data.title)
                        findNavController().navigate(SearchFragmentDirections.actionSearchToDetailArticle(data.url))
                    }
                }
            )
            listArticle.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = articleAdapter
            }
        }
    }

    private fun bindVm() {
        articleVm.apply {
            showLoading.observe(viewLifecycleOwner) {

            }
            showMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            listArticle.observe(viewLifecycleOwner) { result ->
                when(result){
                    is NetworkStatus.Loading -> {
                        showLoading.value = true
                    }
                    is NetworkStatus.Success -> {
                        result.data?.let {
                            articleAdapter.addSources(result.data.articles ?: arrayListOf())
                        }
                    }
                    is NetworkStatus.Error -> {
                        showingMessage(result.errorMessage ?: "")
                    }
                }
            }
        }
    }
}
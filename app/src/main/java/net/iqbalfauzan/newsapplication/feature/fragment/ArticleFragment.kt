package net.iqbalfauzan.newsapplication.feature.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.anteraja.guardian.framework.listener.CommonActionDataListener
import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.NewsArticleResponse
import net.iqbalfauzan.newsapplication.databinding.FragmentArticleBinding
import net.iqbalfauzan.newsapplication.feature.adapter.ArticleAdapter
import net.iqbalfauzan.newsapplication.feature.viewmodel.ArticleVm

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.feature.fragment
 */
@AndroidEntryPoint
class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding
    private lateinit var articleAdapter: ArticleAdapter
    private val articleVm: ArticleVm by viewModels()
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        bindVm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onBindView() {
        articleAdapter = ArticleAdapter(
            object : CommonActionDataListener<NewsArticleResponse> {
                override fun onCLick(data: NewsArticleResponse) {
                    Log.d("ARTICLE", data.title)
                    findNavController().navigate(ArticleFragmentDirections.actionArticleToDetailArticle(data.url))
                }
            }
        )
        binding?.apply {
            labelSource.text = "Source: ${args.sources}"
            listArticle.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = articleAdapter
            }
        }
    }

    private fun bindVm() {
        articleVm.apply {
            getArticles(args.sourceId)
            showLoading.observe(viewLifecycleOwner) {
                binding?.progressBar?.visibility = if (it) View.VISIBLE else View.GONE
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
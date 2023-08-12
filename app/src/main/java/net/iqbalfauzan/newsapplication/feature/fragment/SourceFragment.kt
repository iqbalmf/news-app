package net.iqbalfauzan.newsapplication.feature.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.anteraja.guardian.framework.listener.CommonActionDataListener
import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.NewsSourceResponse
import net.iqbalfauzan.newsapplication.databinding.FragmentSourceBinding
import net.iqbalfauzan.newsapplication.feature.adapter.SourceAdapter
import net.iqbalfauzan.newsapplication.feature.viewmodel.SourceVm

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.feature.fragment
 */
@AndroidEntryPoint
class SourceFragment : Fragment() {
    private var _binding: FragmentSourceBinding? = null
    private val binding get() = _binding
    private val args: SourceFragmentArgs by navArgs()
    private val sourceVm: SourceVm by viewModels()
    private lateinit var sourceAdapter: SourceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSourceBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindVm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindView() {
        sourceAdapter = SourceAdapter(
            object : CommonActionDataListener<NewsSourceResponse>{
                override fun onCLick(data: NewsSourceResponse) {
                    Log.d("SOURCE", data.name.toString())
                }
            }
        )
        binding?.apply {
            labelSource.text = "Category: ${args.category.uppercase()}"
            listSource.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = sourceAdapter
            }
        }
    }

    private fun bindVm() {
        sourceVm.apply {
            getNewsSource(args.category)
            showLoading.observe(viewLifecycleOwner) {

            }
            showMessage.observe(viewLifecycleOwner) {

            }
            listSource.observe(viewLifecycleOwner){result ->
                when(result){
                    is NetworkStatus.Loading -> {
                        showLoading.value = true
                    }
                    is NetworkStatus.Success -> {
                        result.data?.let {
                            sourceAdapter.addSources(result.data.sources ?: arrayListOf())
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
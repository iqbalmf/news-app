package net.iqbalfauzan.newsapplication.feature.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.anteraja.guardian.framework.listener.CommonActionDataListener
import net.iqbalfauzan.newsapplication.common.CATEGORY_SOURCE_NEWS
import net.iqbalfauzan.newsapplication.databinding.FragmentHomeCategoryBinding
import net.iqbalfauzan.newsapplication.feature.adapter.CategoryAdapter

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.feature.fragment
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeCategoryBinding? = null
    private val binding get() = _binding
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeCategoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        binding?.apply {
            listCategory.layoutManager = LinearLayoutManager(requireContext())
            listCategory.adapter = categoryAdapter
            listCategory.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindView() {
        categoryAdapter = CategoryAdapter(
            CATEGORY_SOURCE_NEWS,
            object : CommonActionDataListener<String> {
                override fun onCLick(data: String) {
                    Log.d("CATEGORY", data)
                }
            }
        )
    }
}
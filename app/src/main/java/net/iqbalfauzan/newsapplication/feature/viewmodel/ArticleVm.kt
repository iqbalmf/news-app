package net.iqbalfauzan.newsapplication.feature.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.ArticleResponse
import net.iqbalfauzan.newsapplication.domain.usecase.GetNewsUseCase
import javax.inject.Inject

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.feature.viewmodel
 */
@HiltViewModel
class ArticleVm @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {
    val showLoading = MutableLiveData(false)
    val showMessage = MutableLiveData("")
    private val _listArticle = MutableLiveData<NetworkStatus<ArticleResponse>>()
    val listArticle = _listArticle

    fun showingMessage(message: String) {
        showLoading.value = false
        showMessage.value = message
    }

    fun getArticles(source: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _listArticle.postValue(NetworkStatus.Loading())
            when (val result = getNewsUseCase.getArticleBySource(source)) {
                is NetworkStatus.Success -> {
                    result.data?.let {
                        _listArticle.postValue(NetworkStatus.Success(it))
                        showLoading.postValue(false)
                    }
                }

                is NetworkStatus.Error -> {
                    _listArticle.postValue(NetworkStatus.Error(result.errorMessage))
                }

                else -> {}
            }
        }
    }

    fun getArticlesBySearch(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _listArticle.postValue(NetworkStatus.Loading())
            when (val result = getNewsUseCase.getArticleBySearch(search)) {
                is NetworkStatus.Success -> {
                    result.data?.let {
                        _listArticle.postValue(NetworkStatus.Success(it))
                        showLoading.postValue(false)
                    }
                }

                is NetworkStatus.Error -> {
                    _listArticle.postValue(NetworkStatus.Error(result.errorMessage))
                }

                else -> {}
            }
        }
    }
}
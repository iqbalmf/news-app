package net.iqbalfauzan.newsapplication.feature.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.iqbalfauzan.newsapplication.common.utils.NetworkStatus
import net.iqbalfauzan.newsapplication.data.model.SourceResponse
import net.iqbalfauzan.newsapplication.domain.usecase.GetNewsUseCase
import javax.inject.Inject

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.feature.viewmodel
 */
@HiltViewModel
class SourceVm @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {
    val showLoading = MutableLiveData(false)
    val showMessage = MutableLiveData("")
    private val _listSource = MutableLiveData<NetworkStatus<SourceResponse>>()
    val listSource = _listSource

    fun showingMessage(message: String) {
        showLoading.value = false
        showMessage.value = message
    }

    fun getNewsSource(category: String){
        viewModelScope.launch(Dispatchers.IO) {
            _listSource.postValue(NetworkStatus.Loading())
            when(val result = getNewsUseCase.getSourceByCategory(category)){
                is NetworkStatus.Success -> {
                    result.data?.let {
                        _listSource.postValue(NetworkStatus.Success(it))
                        showLoading.postValue(false)
                    }
                }
                is NetworkStatus.Error -> {
                    _listSource.postValue(NetworkStatus.Error(result.errorMessage))
                }
                else -> {}
            }
        }
    }
}
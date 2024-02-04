package com.example.taskmoengage.ui.viewModel

import androidx.lifecycle.viewModelScope
import com.example.taskmoengage.base.BaseViewModel
import com.example.taskmoengage.base.UiState
import com.example.taskmoengage.model.newsModel.Article
import com.example.taskmoengage.model.newsModel.NewsResponse
import com.example.taskmoengage.network.RequestHandler
import com.example.taskmoengage.utils.Constants
import com.example.taskmoengage.utils.OrderType
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<UiState>() {

    private var newsList: List<Article> = ArrayList()

    fun getNewsData(url: String, orderType: OrderType) {
        onSuccessResponse.value = UiState.Loading
        if (newsList.isEmpty()) {
            val getNewsData = viewModelScope.async(Dispatchers.IO) {
                RequestHandler.requestGet(url)
            }
            viewModelScope.launch(Dispatchers.Main) {
                try {
                    val response =
                        Gson().fromJson(getNewsData.await(), NewsResponse::class.java)
                    newsList = response.articles as ArrayList<Article>
                    onSuccessResponse.value = UiState.Success(sortListBasedOnOrder(orderType))
                } catch (e: Exception) {
                    onSuccessResponse.value = UiState.Error(e.message.toString())
                }
            }
        } else {
            onSuccessResponse.value =
                UiState.Success(sortListBasedOnOrder(orderType))
        }
    }

    private fun sortListBasedOnOrder(orderType: OrderType): List<Article> {
        newsList = when (orderType) {
            is OrderType.LatestFirst -> {
                newsList.sortedBy { Constants.convertStringToDate(it.publishedAt) }
            }

            is OrderType.OldestFirst -> {
                newsList.sortedByDescending { Constants.convertStringToDate(it.publishedAt) }
            }
        }
        return newsList
    }


}
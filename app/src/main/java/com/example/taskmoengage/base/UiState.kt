package com.example.taskmoengage.base

import com.example.taskmoengage.model.newsModel.Article

sealed class UiState {

    object Loading : UiState()
    data class Success(
        val responseData: List<Article>
    ) : UiState()

    data class Error(val message: String) : UiState()


}

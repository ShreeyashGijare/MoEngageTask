package com.example.taskmoengage.ui.newsInterface

import com.example.taskmoengage.model.newsModel.Article
import com.example.taskmoengage.utils.KeyValuePairImpl

interface NewsClickInterface {
    fun newsClickListener(newsItem: Article)
    fun bottomSheetFilterClickListener(filterItem: KeyValuePairImpl)
}
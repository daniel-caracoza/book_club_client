package com.example.bookclub.models

import com.example.ApiServiceSearchQuery

data class SearchResultItem(
    val title: String,
    val image: String,
    val isbn13: String,
    val authors: String
)

fun List<ApiServiceSearchQuery.ApiServiceSearch>.mapToSearchResultItem(): List<SearchResultItem> {
    return map {
        SearchResultItem(
            it.title,
            it.image,
            it.isbn13,
            it.authors.joinToString()
        )
    }
}
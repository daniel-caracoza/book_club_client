package com.example.bookclub.models

import com.example.FindByIsbnQuery
import com.example.type.BookInput

data class SearchBook(
    val title: String,
    val image: String,
    val publisher: String?,
    val isbn13: String,
    val synopsys: String?,
    val pages: Int?,
    val authors: String
)

fun FindByIsbnQuery.FindByISBN.mapToSearchBook(): SearchBook {
    return let {
        SearchBook(
            it.title,
            it.image,
            it.publisher,
            it.isbn13,
            it.synopsys,
            it.pages,
            it.authors.joinToString()
        )
    }
}

fun SearchBook.mapToBookInput(): BookInput {
    return let {
        BookInput(
            it.isbn13,
            it.title,
            it.image,
            it.authors
        )
    }
}
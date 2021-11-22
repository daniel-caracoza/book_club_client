package com.example.bookclub.models

import com.example.ClubTopicsQuery
import com.example.GetClubsQuery
import java.io.Serializable

data class Club(
    val id: String,
    val clubName: String,
    val clubImage: String,
    val clubBookTitle: String,
    val clubBookAuthor: String
): Serializable

data class ClubTopic(
    val id: String,
    val topic: String
)

fun List<ClubTopicsQuery.ClubTopic>.mapToDomainClubTopic(): List<ClubTopic> {
    return map {
        ClubTopic(
            it.id,
            it.topic
        )
    }
}

fun List<GetClubsQuery.GetClub>.mapToDomainClub(): List<Club> {
    return map {
        Club(
            it.id,
            it.clubName,
            it.clubImage,
            it.clubBookTitle,
            it.clubBookAuthor
        )
    }
}


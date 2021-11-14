package com.example.bookclub.models

import com.example.GetClubsQuery

data class Club(
    val id: Double,
    val clubName: String
)

fun List<GetClubsQuery.GetClub>.mapToDomainClub(): List<Club> {
    return map {
        Club(
            it.id,
            it.clubName
        )
    }
}
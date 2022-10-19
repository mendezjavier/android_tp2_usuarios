package com.javiermendez.tp2_usuarios


data class RandomUserResponse(
    val results: List<RandomUser>
//    val info: InfoResponse
)

data class InfoResponse(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)

package com.meowbynykaa.app.data

import retrofit2.http.GET

interface CatsAPI {

    @GET("images/search?limit=10")
    suspend fun getCats(): List<Cat>

    companion object {
        const val baseURL = "https://api.thecatapi.com/v1/"
    }
}
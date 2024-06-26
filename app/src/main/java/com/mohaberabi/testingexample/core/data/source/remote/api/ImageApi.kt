package com.mohaberabi.testingexample.core.data.source.remote.api

import com.mohaberabi.testingexample.core.data.source.remote.ApiConst
import com.mohaberabi.testingexample.core.data.source.remote.dto.ImageResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET(ApiConst.SEARCH_ENDPOINT)
    suspend fun searchImage(
        @Query("q") query: String,
        @Query("key") apiKey: String = ApiConst.API_KEY
    ): ImageResponseDto?
}
package com.mohaberabi.testingexample.core.di.fakes.data.source

import com.mohaberabi.testingexample.core.data.source.remote.api.ImageApi
import com.mohaberabi.testingexample.core.data.source.remote.dto.ImageResponseDto

class FakeImagesApi : ImageApi {
    override suspend fun searchImage(
        query: String, apiKey: String,
    ): ImageResponseDto? {
        return ImageResponseDto(hits = listOf())
    }
}
package com.mohaberabi.testingexample.core.domain.repository

import com.mohaberabi.testingexample.core.domain.model.ImageResponse


interface ImageRepository {
    suspend fun searchImage(query: String): Result<ImageResponse>
}
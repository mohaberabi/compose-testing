package com.mohaberabi.testingexample.fakes.data.repos

import com.mohaberabi.testingexample.core.domain.model.ImageModel
import com.mohaberabi.testingexample.core.domain.model.ImageResponse
import com.mohaberabi.testingexample.core.domain.repository.ImageRepository
import net.bytebuddy.implementation.bytecode.Throw


class FakeImageRepository : ImageRepository {
    val exception: Throwable = Exception("FakeImageRepositoryError")
    var returnFailure: Boolean = false
    val images = listOf("url1", "url2", "url3").map { ImageModel(it) }
    var called: Int = 0
    override suspend fun searchImage(
        query: String,
    ): Result<ImageResponse> {
        called++
        return if (returnFailure) {
            Result.failure(exception)
        } else {
            Result.success(ImageResponse(images))
        }
    }
}
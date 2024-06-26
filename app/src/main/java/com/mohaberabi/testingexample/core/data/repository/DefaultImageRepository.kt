package com.mohaberabi.testingexample.core.data.repository

import com.mohaberabi.testingexample.core.data.source.remote.api.ImageApi
import com.mohaberabi.testingexample.core.data.source.remote.toImageModel
import com.mohaberabi.testingexample.core.domain.model.ImageResponse
import com.mohaberabi.testingexample.core.domain.repository.ImageRepository
import kotlinx.coroutines.CancellationException

import javax.inject.Inject

class DefaultImageRepository @Inject constructor(
    private val api: ImageApi,
) : ImageRepository {


    override suspend fun searchImage(
        query: String,
    ): Result<ImageResponse> {
        return try {
            val response = api.searchImage(query = query)
            val images =
                response?.let { it.hits?.map { img -> img.toImageModel() } } ?: listOf()
            Result.success(ImageResponse(images))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            } else {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
}
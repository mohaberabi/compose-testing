package com.mohaberabi.testingexample.core.domain.usecase

import com.mohaberabi.testingexample.core.domain.model.ImageResponse
import com.mohaberabi.testingexample.core.domain.repository.ImageRepository
import javax.inject.Inject

class SearchImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {


    suspend operator fun invoke(query: String): Result<ImageResponse> =
        imageRepository.searchImage(query = query)
}

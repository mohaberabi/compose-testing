package com.mohaberabi.testingexample.core.data.source.remote

import com.mohaberabi.testingexample.core.data.source.remote.dto.ImageDto
import com.mohaberabi.testingexample.core.domain.model.ImageModel
import com.mohaberabi.testingexample.core.domain.model.ImageResponse


fun ImageDto.toImageModel(): ImageModel = ImageModel(url = previewURL ?: "")


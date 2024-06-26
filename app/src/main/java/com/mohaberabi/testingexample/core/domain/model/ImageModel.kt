package com.mohaberabi.testingexample.core.domain.model

data class ImageModel(


    val url: String
)

data class ImageResponse(val images: List<ImageModel>)

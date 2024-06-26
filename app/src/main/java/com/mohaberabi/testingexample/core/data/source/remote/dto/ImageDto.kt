package com.mohaberabi.testingexample.core.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ImageDto(
    @SerialName("previewURL")
    val previewURL: String?
)


@Serializable
data class ImageResponseDto(
    val hits: List<ImageDto>?,
)
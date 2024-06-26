package com.mohaberabi.testingexample.core.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.mohaberabi.testingexample.core.domain.usecase.SearchImageUseCase
import com.mohaberabi.testingexample.fakes.data.repos.FakeImageRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class SearchImagesTest {


    private lateinit var imageRepository: FakeImageRepository


    private lateinit var searchImageUseCase: SearchImageUseCase


    @Before
    fun setup() {
        imageRepository = FakeImageRepository()
        searchImageUseCase = SearchImageUseCase(imageRepository)
    }

    @Test
    fun `returns done with images called once`() = runTest {
        val res = searchImageUseCase("q")
        assertThat(res.isSuccess).isTrue()
        assertThat(imageRepository.called).isEqualTo(1)
    }

    @Test
    fun `returns failure  called once`() = runTest {
        imageRepository.returnFailure = true
        val res = searchImageUseCase("q")
        assertThat(res.isFailure).isTrue()
        assertThat(imageRepository.called).isEqualTo(1)
    }
}
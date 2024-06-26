package com.mohaberabi.testingexample.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mohaberabi.testingexample.core.presentation.navigation.AppNavHost
import com.mohaberabi.testingexample.core.presentation.theme.TestingExampleTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestingExampleTheme {
                AppNavHost()
            }
        }
    }
}


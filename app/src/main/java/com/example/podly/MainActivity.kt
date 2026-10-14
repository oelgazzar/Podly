package com.example.podly

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.podly.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(
            window,
            window.decorView
        ).hide(WindowInsetsCompat.Type.navigationBars())
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val window = (LocalView.current.context as Activity).window
                    val controller = remember(window) {
                        WindowCompat.getInsetsController(
                            window,
                            window.decorView
                        )
                    }

                    DisposableEffect(controller) {
                        controller.hide(
                            WindowInsetsCompat.Type.systemBars()
                        )

                        controller.systemBarsBehavior =
                            WindowInsetsControllerCompat
                                .BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

                        onDispose {
                            controller.show(
                                WindowInsetsCompat.Type.systemBars()
                            )
                        }
                    }
                    App()
                }
            }
        }
    }
}
package com.sachin.freedium

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sachin.freedium.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Check if the Intent is for shared text
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            handleIncomingText(intent) // Custom method to process the text
        }

        setContent {
            App()
        }
    }

    private fun handleIncomingText(intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        SharedTextHandler.handleText(sharedText)
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
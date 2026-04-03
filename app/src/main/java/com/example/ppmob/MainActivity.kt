package com.example.ppmob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.artguess.presentation.navigation.NavigHost
import com.example.ppmob.ui.theme.PpMobTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PpMobTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                ){
                    NavigHost()
                }
            }
        }
    }
}
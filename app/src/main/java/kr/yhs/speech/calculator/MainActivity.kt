package kr.yhs.speech.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kr.yhs.speech.calculator.navigator.AppTheme
import kr.yhs.speech.calculator.navigator.ComposeApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                ComposeApp(activity = this)
            }
        }
    }
}
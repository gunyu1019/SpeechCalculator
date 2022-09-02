package kr.yhs.speech.calculator.navigator

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme


@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = WearableColors,
        content = content
    )
}
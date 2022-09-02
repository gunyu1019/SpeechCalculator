package kr.yhs.speech.calculator.pages

sealed class Screen (val route: String) {
    object SpeechRequire: Screen("SpeechRequire")
    object CalculateResult: Screen("CalculateResult")
}

package kr.yhs.speech.calculator.pages

const val QUERY = "query"


sealed class Screen (val route: String) {
    object SpeechRequire: Screen("SpeechRequire")
    object CalculateResult: Screen("CalculateResult")
}

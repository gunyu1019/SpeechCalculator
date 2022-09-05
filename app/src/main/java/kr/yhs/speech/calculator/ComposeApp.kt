package kr.yhs.speech.calculator

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import kr.yhs.speech.calculator.model.Word
import kr.yhs.speech.calculator.model.WordType
import kr.yhs.speech.calculator.pages.HorizontalPagerScreen
import kr.yhs.speech.calculator.pages.PagerItemScreen
import kr.yhs.speech.calculator.pages.QUERY
import kr.yhs.speech.calculator.pages.Screen


fun isNumeric(s: String): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return s.matches(regex)
}


@Composable
fun ComposeApp(activity: ComponentActivity) {
    val navController = rememberSwipeDismissableNavController()
    val coroutineScope = rememberCoroutineScope()

    val intent = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data !== null) {
            navController.navigate(
                Screen.CalculateResult.route + "?$QUERY=${it.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)}"
            )
        }
    }

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Screen.SpeechRequire.route
    ) {
        composable(Screen.SpeechRequire.route) {
            HorizontalPagerScreen(
                coroutineScope, listOf(
                    PagerItemScreen(
                        title = "일반계산기",
                        description = "사칙연산을 시도해보세요!",
                        icon = Icons.Outlined.Calculate
                    ) {
                        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                        }
                        intent.launch(recognizerIntent)
                    }
                )
            )
        }
        composable(
            Screen.CalculateResult.route + "?$QUERY={$QUERY}",
            listOf(navArgument(QUERY) { type = NavType.StringType })
        ) {
            val query = it.arguments?.getString(QUERY)!!
            val queryArray = arrayListOf(
                Word(query[0].toString(), WordType.NUMBER)
            )
            query.trim().takeLast(1).forEach {
                word: Char ->
                val lastIndex = queryArray.lastIndex
                if (isNumeric(word.toString())) {
                    if (queryArray.last().type == WordType.NUMBER) {
                        queryArray[lastIndex].string += word.toString()
                    } else {
                        queryArray.add(
                            Word(word.toString(), WordType.SIGN)
                        )
                    }
                } else {
                    if (queryArray.last().type == WordType.SIGN) {
                        queryArray[lastIndex].string += word.toString()
                    } else {
                        queryArray.add(
                            Word(word.toString(), WordType.NUMBER)
                        )
                    }
                }
            }

            var value = queryArray[0].string.toInt()
            queryArray.takeLast(1).forEach { word: Word ->
                if (word.type == WordType.NUMBER) {

                }
            }
        }
    }
}



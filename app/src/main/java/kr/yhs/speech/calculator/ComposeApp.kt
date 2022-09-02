package kr.yhs.speech.calculator.navigator

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.runtime.*
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import kr.yhs.speech.calculator.pages.HorizontalPagerScreen
import kr.yhs.speech.calculator.pages.PagerItemScreen
import kr.yhs.speech.calculator.pages.Screen


@Composable
fun ComposeApp(activity: ComponentActivity) {
    val navController = rememberSwipeDismissableNavController()
    val coroutineScope = rememberCoroutineScope()
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

                    }
                )
            )
        }
        composable(Screen.CalculateResult.route) {

        }
    }
}

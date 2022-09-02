package kr.yhs.speech.calculator.pages

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun HorizontalPagerScreen(
    scope: CoroutineScope,
    pages: List<Unit>
) {
    val pagerState = rememberPagerState()
    val focusRequester = remember { FocusRequester() }
    val indicatorState = remember {
        object : PageIndicatorState {
            override val pageCount: Int
                get() = pagerState.pageCount
            override val pageOffset: Float
                get() = pagerState.currentPageOffset
            override val selectedPage: Int
                get() = pagerState.currentPage
        }
    }
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                count = pages.size,
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .onRotaryScrollEvent {
                        scope.launch {
                            when {
                                it.horizontalScrollPixels > 0 && pagerState.currentPage < pages.count() - 1 -> pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                )
                                it.horizontalScrollPixels < 0 && pagerState.currentPage > 0 -> pagerState.animateScrollToPage(
                                    pagerState.currentPage - 1
                                )
                            }
                        }
                        true
                    }
                    .focusRequester(focusRequester)
                    .focusable()
            ) {
                page: Int -> pages[page]
            }
            HorizontalPageIndicator(
                pageIndicatorState = indicatorState
            )
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
package ru.neexol.rtut.presentation.components

import androidx.compose.runtime.snapshotFlow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import java.math.BigDecimal

@ExperimentalPagerApi
fun scrollingPair(state1: PagerState, state2: PagerState) = when {
	state1.isScrollInProgress -> state1 to state2
	state2.isScrollInProgress -> state2 to state1
	else -> null
}

@ExperimentalPagerApi
suspend fun syncScroll(pair: Pair<PagerState, PagerState>?) {
	val (scrollingState, followingState) = pair ?: return
	snapshotFlow { scrollingState.currentPage + scrollingState.currentPageOffset }
		.collect { pagePart ->
			val divideAndRemainder = BigDecimal.valueOf(pagePart.toDouble())
				.divideAndRemainder(BigDecimal.ONE)

			followingState.scrollToPage(
				divideAndRemainder[0].toInt(),
				divideAndRemainder[1].toFloat(),
			)
		}
}
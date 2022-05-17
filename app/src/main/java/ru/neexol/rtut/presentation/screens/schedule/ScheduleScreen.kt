package ru.neexol.rtut.presentation.screens.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.LessonItem
import ru.neexol.rtut.presentation.components.PagerTopBar
import java.math.BigDecimal

@ExperimentalPagerApi
@Composable
fun ScheduleScreen(vm: ScheduleViewModel) {
	LaunchedEffect(Unit) {
		vm.fetchGroup()
	}

	val uiState = vm.uiState
	if (!uiState.lessons.isNullOrEmpty()) {
		val weekPager = rememberPagerState(vm.dayWeek.second)
		val dayPager = rememberPagerState(vm.dayWeek.first)
		val lessonsPager = rememberPagerState(vm.dayWeek.first)

		val scrollingFollowingPair by remember {
			derivedStateOf {
				when {
					dayPager.isScrollInProgress -> dayPager to lessonsPager
					lessonsPager.isScrollInProgress -> lessonsPager to dayPager
					else -> null
				}
			}
		}

		LaunchedEffect(scrollingFollowingPair) {
			val (scrollingState, followingState) = scrollingFollowingPair ?: return@LaunchedEffect
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

		Column {
			PagerTopBar(
				state = weekPager,
				title = stringResource(R.string.week_letter),
				items = uiState.lessons.indices.map { (it + 1).toString() }
			)

			PagerTopBar(
				state = dayPager,
				title = stringResource(R.string.day_letter),
				items = stringArrayResource(R.array.days_cut).toList(),
				isLast = true
			)

			HorizontalPager(
				state = lessonsPager,
				count = 6
			) { day ->
				if (!uiState.times.isNullOrEmpty()) {
					LazyColumn(
						modifier = Modifier.fillMaxSize(),
						contentPadding = PaddingValues(vertical = 14.dp, horizontal = 20.dp),
						verticalArrangement = Arrangement.spacedBy(14.dp),
					) {
						itemsIndexed(uiState.lessons[weekPager.currentPage][day]) { number, lesson ->
							LessonItem(lesson, uiState.times[number])
						}
					}
				}
			}
		}
	}
}
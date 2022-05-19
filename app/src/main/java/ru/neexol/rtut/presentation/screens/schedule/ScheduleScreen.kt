package ru.neexol.rtut.presentation.screens.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.neexol.rtut.R
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime
import ru.neexol.rtut.presentation.components.LessonItem
import ru.neexol.rtut.presentation.components.PagerTopBar
import java.math.BigDecimal

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun ScheduleScreen(vm: ScheduleViewModel = hiltViewModel(), onLessonClick: (Lesson, String) -> Unit) {
	LaunchedEffect(Unit) { vm.fetchGroup() }

	val uiState = vm.uiState
	if (!uiState.lessons.isNullOrEmpty()) {
		val coroutineScope = rememberCoroutineScope()
		val weekPagerState = rememberPagerState(vm.dayWeek.second)
		val dayPagerState = rememberPagerState(vm.dayWeek.first)
		val lessonsPagerState = rememberPagerState(vm.dayWeek.first)

		val scrollingPair by remember {
			derivedStateOf { scrollingPair(dayPagerState, lessonsPagerState) }
		}
		LaunchedEffect(scrollingPair) { syncScroll(scrollingPair) }

		Column {
			WeekPagerBar(weekPagerState, (1..uiState.lessons.size).map(Int::toString)) {
				coroutineScope.launch {
					weekPagerState.animateScrollToPage(vm.dayWeek.second)
				}
			}
			DayPagerBar(dayPagerState) {
				coroutineScope.launch {
					dayPagerState.animateScrollToPage(vm.dayWeek.first)
				}
			}
			if (!uiState.times.isNullOrEmpty()) {
				LessonsPager(
					lessonsPagerState,
					uiState.lessons,
					uiState.times,
					weekPagerState.currentPage,
					onLessonClick
				)
			}
		}
	} else if (uiState.isLessonsLoading) {
		Box(Modifier.fillMaxSize()) {
			CircularProgressIndicator(Modifier.align(Alignment.Center))
		}
	}
}

@ExperimentalPagerApi
private fun scrollingPair(state1: PagerState, state2: PagerState) = when {
	state1.isScrollInProgress -> state1 to state2
	state2.isScrollInProgress -> state2 to state1
	else -> null
}

@ExperimentalPagerApi
private suspend fun syncScroll(pair: Pair<PagerState, PagerState>?) {
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

@ExperimentalPagerApi
@Composable
private fun WeekPagerBar(state: PagerState, items: List<String>, onTitleClick: () -> Unit) {
	PagerTopBar(
		state = state,
		title = stringResource(R.string.week_letter),
		items = items,
		onTitleClick = onTitleClick
	)
}

@ExperimentalPagerApi
@Composable
private fun DayPagerBar(state: PagerState, onTitleClick: () -> Unit) {
	PagerTopBar(
		state = state,
		title = stringResource(R.string.day_letter),
		items = stringArrayResource(R.array.days_cut).toList(),
		onTitleClick = onTitleClick,
		isLast = true
	)
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
private fun LessonsPager(
	state: PagerState,
	lessons: List<List<List<Lesson?>>>,
	times: List<LessonTime>,
	week: Int,
	onLessonClick: (Lesson, String) -> Unit
) {
	HorizontalPager(
		state = state,
		count = 6
	) { day ->
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
			contentPadding = PaddingValues(vertical = 14.dp, horizontal = 20.dp),
			verticalArrangement = Arrangement.spacedBy(14.dp),
		) {
			itemsIndexed(lessons[week][day]) { number, lesson ->
				LessonItem(lesson, times[number]) { onLessonClick(lesson!!, (week + 1).toString()) }
			}
		}
	}
}
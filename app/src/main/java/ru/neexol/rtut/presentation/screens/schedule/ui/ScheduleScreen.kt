package ru.neexol.rtut.presentation.screens.schedule.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.neexol.rtut.R
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.presentation.screens.schedule.ScheduleViewModel
import java.math.BigDecimal

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun ScheduleScreen(vm: ScheduleViewModel = hiltViewModel(), onLessonClick: (Lesson, String) -> Unit) {
	LaunchedEffect(Unit) { vm.fetchGroup() }

	val uiState = vm.uiState
	val coroutineScope = rememberCoroutineScope()
	val snackbarHostState = remember { SnackbarHostState() }
	val weekPagerState = rememberPagerState(vm.dayWeek.second.coerceAtMost(15))
	val dayPagerState = rememberPagerState(vm.dayWeek.first)
	val lessonsPagerState = rememberPagerState(vm.dayWeek.first)

	val scrollingPair by remember {
		derivedStateOf { scrollingPair(dayPagerState, lessonsPagerState) }
	}
	LaunchedEffect(scrollingPair) { syncScroll(scrollingPair) }

	LaunchedEffect(vm.uiState.message) {
		vm.uiState.message?.let {
			coroutineScope.launch {
				snackbarHostState.currentSnackbarData?.dismiss()
				snackbarHostState.showSnackbar(it)
				vm.clearMessage()
			}
		}
	}

	val classroomCopiedMessage = stringResource(R.string.classroom_copied)

	Column {
		WeekPagerBar(weekPagerState, (1..(uiState.lessons?.size ?: 16)).map(Int::toString)) {
			coroutineScope.launch {
				weekPagerState.animateScrollToPage(vm.dayWeek.second)
			}
		}
		DayPagerBar(dayPagerState) {
			coroutineScope.launch {
				dayPagerState.animateScrollToPage(vm.dayWeek.first)
			}
		}
		Box(Modifier.fillMaxSize()) {
			when {
				uiState.isLessonsLoading -> {
					Box(Modifier.fillMaxSize()) {
						CircularProgressIndicator(Modifier.align(Alignment.Center))
					}
				}
				uiState.lessons != null -> {
					LessonsPager(
						state = lessonsPagerState,
						lessons = uiState.lessons,
						times = uiState.times!!,
						week = weekPagerState.currentPage,
						onLessonClick = onLessonClick,
						onClassroomCopy = {
							coroutineScope.launch {
								snackbarHostState.currentSnackbarData?.dismiss()
								snackbarHostState.showSnackbar(classroomCopiedMessage)
							}
						}
					)
				}
			}
			SnackbarHost(
				hostState = snackbarHostState,
				modifier = Modifier.align(Alignment.BottomCenter)
			)
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
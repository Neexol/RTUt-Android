package ru.neexol.rtut.presentation.screens.teacher.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.neexol.rtut.presentation.screens.teacher.TeacherViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun TeacherScreen(vm: TeacherViewModel = hiltViewModel()) {
	val coroutineScope = rememberCoroutineScope()
	val weekPagerState = rememberPagerState(vm.dayWeek.second)

	val uiState = vm.uiState
	Column {
		WeekPagerBar(weekPagerState) {
			coroutineScope.launch {
				weekPagerState.animateScrollToPage(vm.dayWeek.second)
			}
		}
		FindTeacherBar(vm)
		if (!uiState.lessons.isNullOrEmpty() && !uiState.times.isNullOrEmpty()) {
			LessonsList(uiState.lessons, uiState.times, weekPagerState.currentPage)
		} else if (uiState.isLessonsLoading) {
			Box(Modifier.fillMaxSize()) {
				CircularProgressIndicator(Modifier.align(Alignment.Center))
			}
		}
	}
}
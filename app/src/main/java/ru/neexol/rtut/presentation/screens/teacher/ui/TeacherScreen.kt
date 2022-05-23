package ru.neexol.rtut.presentation.screens.teacher.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
	val weekPagerState = rememberPagerState(vm.dayWeek.second.coerceAtMost(15))

	Column {
		WeekPagerBar(weekPagerState) {
			coroutineScope.launch {
				weekPagerState.animateScrollToPage(vm.dayWeek.second)
			}
		}
		FindTeacherBar(vm)
		LessonsList(vm.uiState, weekPagerState.currentPage)
	}
}
package ru.neexol.rtut.presentation.screens.teacher.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.scrollingPair
import ru.neexol.rtut.presentation.components.syncScroll
import ru.neexol.rtut.presentation.screens.teacher.TeacherViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun TeacherScreen(vm: TeacherViewModel = hiltViewModel()) {
	val coroutineScope = rememberCoroutineScope()
	val snackbarHostState = remember { SnackbarHostState() }
	val weekPagerState = rememberPagerState(vm.dayWeek.second.coerceAtMost(15))
	val lessonsPagerState = rememberPagerState(vm.dayWeek.second.coerceAtMost(15))

	val scrollingPair by remember {
		derivedStateOf { scrollingPair(weekPagerState, lessonsPagerState) }
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
		WeekPagerBar(weekPagerState) {
			coroutineScope.launch {
				weekPagerState.animateScrollToPage(vm.dayWeek.second)
			}
		}
		FindTeacherBar(vm)
		Box(Modifier.fillMaxSize()) {
			LessonsPager(
				state = lessonsPagerState,
				uiState = vm.uiState,
				onClassroomCopy = {
					coroutineScope.launch {
						snackbarHostState.currentSnackbarData?.dismiss()
						snackbarHostState.showSnackbar(classroomCopiedMessage)
					}
				}
			)
			SnackbarHost(
				hostState = snackbarHostState,
				modifier = Modifier.align(Alignment.BottomCenter)
			)
		}
	}
}
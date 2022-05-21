package ru.neexol.rtut.presentation.screens.teacher

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.neexol.rtut.R
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime
import ru.neexol.rtut.presentation.components.FindTopBar
import ru.neexol.rtut.presentation.components.LessonItem
import ru.neexol.rtut.presentation.components.PagerTopBar

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

@ExperimentalPagerApi
@Composable
private fun WeekPagerBar(state: PagerState, onTitleClick: () -> Unit) {
	PagerTopBar(
		state = state,
		title = stringResource(R.string.week_letter),
		items = (1..16).map(Int::toString),
		onTitleClick = onTitleClick,
		isLast = true
	)
}

@ExperimentalAnimationApi
@Composable
private fun FindTeacherBar(vm: TeacherViewModel) {
	FindTopBar(
		value = vm.teacher,
		placeholder = stringResource(R.string.teacher),
		onValueChange = { vm.teacher = it.trimStart() },
		onImeAction = { vm.fetchLessons() },
		onClearAction = { vm.fetchLessons() }
	)
}

@ExperimentalMaterialApi
@Composable
private fun LessonsList(lessons: List<List<List<Lesson>>>, times: List<LessonTime>, week: Int) {
	val daysLabels = stringArrayResource(R.array.days).toList()
	LazyColumn(
		modifier = Modifier.fillMaxSize(),
		contentPadding = PaddingValues(vertical = 14.dp, horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(14.dp),
	) {
		lessons[week].forEachIndexed { day, dayLessons ->
			if (dayLessons.isNotEmpty()) {
				item {
					Text(
						text = daysLabels[day],
						style = MaterialTheme.typography.h6,
						modifier = Modifier
							.padding(vertical = 8.dp, horizontal = 16.dp)
							.padding(top = 8.dp)
					)
				}
				items(dayLessons) { lesson ->
					LessonItem(lesson, times[lesson.number])
				}
			}
		}
	}
}
package ru.neexol.rtut.presentation.screens.schedule.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.lessons.models.LessonTime
import ru.neexol.rtut.presentation.components.LessonItem

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
internal fun LessonsPager(
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
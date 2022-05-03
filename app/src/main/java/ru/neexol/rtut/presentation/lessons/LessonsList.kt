package ru.neexol.rtut.presentation.lessons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LessonsList(vm: LessonsViewModel) {
	LaunchedEffect(key1 = Unit, block = {
		vm.getGroupLessons()
	})

	Scaffold (
		topBar = {
			TopAppBar(
				title = {
					Text("ИКБО-12-19")
				}
			)
		}
	) {
		Column(modifier = Modifier.padding(16.dp)) {
			LazyColumn(modifier = Modifier.fillMaxHeight()) {
				items(vm.lessons) { lesson ->
					Text("${lesson.name} ${lesson.classroom} ${lesson.teacher}")
					Divider()
				}
			}
		}
	}
}
package ru.neexol.rtut.presentation.grouplessons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun GroupLessonsList(vm: GroupLessonsViewModel) {
	Scaffold (
		topBar = {
			TopAppBar(
				title = {
					Text("ИКБО-12-19")
				}
			)
		}
	) {
		val lessons by vm.lessonsResource.collectAsState()
		lessons(
			onSuccess = {
				LazyColumn(modifier = Modifier.fillMaxHeight()) {
					items(it.times) { time ->
						Text("${time.begin} ${time.end}")
						Divider()
					}
					items(it.lessons) { lesson ->
						Text("${lesson.name} ${lesson.classroom} ${lesson.teacher}")
						Divider()
					}
				}
			},
			onError = {
				Text(text = it.toString())
			},
			onLoading = {
				CircularProgressIndicator()
			}
		)
	}
}
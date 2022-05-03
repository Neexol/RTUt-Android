package ru.neexol.rtut.presentation.grouplessons

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun GroupLessonsList(vm: GroupLessonsViewModel) {
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
		vm.lessonsResource(
			onSuccess = {
				LazyColumn(modifier = Modifier.fillMaxHeight()) {
					items(it) { lesson ->
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
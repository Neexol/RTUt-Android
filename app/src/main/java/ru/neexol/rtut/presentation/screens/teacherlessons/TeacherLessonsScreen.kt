package ru.neexol.rtut.presentation.screens.teacherlessons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TeacherLessonsScreen(vm: TeacherLessonsViewModel) {
	val coroutineScope = rememberCoroutineScope()
	val weekPagerState = rememberPagerState()
	val keyboardController = LocalSoftwareKeyboardController.current

	Column {
		Box {
			HorizontalPager(
				modifier = Modifier
					.height(96.dp)
					.zIndex(1f),
				state = weekPagerState,
				count = 16,
				contentPadding = PaddingValues(horizontal = 150.dp)
			) { page ->
				Box(
					modifier = Modifier
						.size(96.dp)
						.clip(CircleShape)
						.clickable(
							interactionSource = remember { MutableInteractionSource() },
							indication = null
						) {
							coroutineScope.launch { weekPagerState.animateScrollToPage(page) }
						},
				) {
					Text(
						modifier = Modifier.align(Alignment.Center),
						text = (page + 1).toString(),
						fontSize = 24.sp
					)
				}
			}
			Box(
				modifier = Modifier
					.background(Color.Gray, shape = CircleShape)
					.size(64.dp)
					.align(Alignment.Center)
			)
		}
		TextField(
			value = vm.teacher,
			onValueChange = { vm.teacher = it.trimStart() },
			label = { Text("Teacher") },
			singleLine = true,
			keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
			keyboardActions = KeyboardActions {
				vm.fetchLessons()
				keyboardController?.hide()
			}
		)
		val lessons = vm.uiState.lessons
		val times = vm.uiState.times
		if (lessons != null && times != null) {
			LazyColumn {
				itemsIndexed(lessons[weekPagerState.currentPage]) { day, dayLessons ->
					if (dayLessons.isNotEmpty()) {
						Column(
							modifier = Modifier
								.fillMaxWidth()
								.padding(16.dp)
						) {
							Text(
								text = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ")[day],
								modifier = Modifier.align(Alignment.CenterHorizontally)
							)
							dayLessons.forEach {
								Row {
									Column {
										Text(times[it.number].begin)
										Text(times[it.number].end)
									}
									Column {
										Text(it.name)
										Row {
											Text("${it.teacher}    |   ${it.classroom}")
										}
									}
								}
								Divider()
							}
						}
						Spacer(modifier = Modifier.height(16.dp))
					}
				}
			}
		}
	}
}
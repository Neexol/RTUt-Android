package ru.neexol.rtut.presentation.teacherlessons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
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
import ru.neexol.rtut.core.Resource

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
			value = vm.teacherState,
			onValueChange = { vm.teacherState = it.trimStart() },
			label = { Text("Teacher") },
			singleLine = true,
			keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
			keyboardActions = KeyboardActions {
				vm.loadLessons()
				keyboardController?.hide()
			}
		)
		val lessonsResource by vm.lessonsResourceFlow.collectAsState()
		val times by vm.timesFlow.collectAsState()
		(lessonsResource as? Resource.Success)?.let { res ->
			val lessons = res.data.filter {
				weekPagerState.currentPage + 1 in it.weeks
			}.sortedWith(compareBy({ it.day }, { it.number }))
			LazyColumn {
				items(lessons) {
					Column(
						modifier = Modifier.fillMaxWidth().padding(16.dp)
					) {
						Text(
							text = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ")[it.day],
							modifier = Modifier.align(Alignment.CenterHorizontally)
						)
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
					}
					Divider()
				}
			}
		}
	}
}
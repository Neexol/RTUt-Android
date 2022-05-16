package ru.neexol.rtut.presentation.screens.teacher

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.FindTopBar
import ru.neexol.rtut.presentation.components.PagerTopBar

@ExperimentalPagerApi
@Composable
fun TeacherScreen(vm: TeacherViewModel) {
	val uiState = vm.uiState

	val weekPager = rememberPagerState(vm.dayWeek.second)

	Column {
		PagerTopBar(
			state = weekPager,
			title = stringResource(R.string.week_letter),
			items = (1..16).map(Int::toString),
			isLast = true
		)

		FindTopBar(
			value = vm.teacher,
			placeholder = stringResource(R.string.teacher),
			onValueChange = { vm.teacher = it.trimStart() },
			onImeAction = { vm.fetchLessons() }
		)

		if (uiState.lessons != null && uiState.times != null) {
			LazyColumn {
				itemsIndexed(uiState.lessons[weekPager.currentPage]) { day, dayLessons ->
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
										Text(uiState.times[it.number].begin)
										Text(uiState.times[it.number].end)
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
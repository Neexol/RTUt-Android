package ru.neexol.rtut.presentation.screens.teacher.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.FindTopBar
import ru.neexol.rtut.presentation.screens.teacher.TeacherViewModel

@ExperimentalAnimationApi
@Composable
internal fun FindTeacherBar(vm: TeacherViewModel) {
	FindTopBar(
		value = vm.teacher,
		placeholder = stringResource(R.string.teacher),
		onValueChange = { vm.teacher = it.trimStart() },
		onImeAction = { vm.fetchLessons() },
		onClearAction = { vm.fetchLessons() }
	)
}
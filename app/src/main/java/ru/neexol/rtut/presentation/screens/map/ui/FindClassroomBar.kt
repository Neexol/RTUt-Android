package ru.neexol.rtut.presentation.screens.map.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.FindTopBar
import ru.neexol.rtut.presentation.screens.map.MapViewModel

@ExperimentalAnimationApi
@Composable
internal fun FindClassroomBar(vm: MapViewModel) {
	FindTopBar(
		value = vm.classroom,
		placeholder = stringResource(R.string.classroom),
		onValueChange = { vm.classroom = it.trimStart().replace(' ', '-') },
		onImeAction = { vm.fetchMaps() },
		onClearAction = { vm.fetchMaps() }
	)
}
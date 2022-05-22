package ru.neexol.rtut.presentation.screens.map.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.PagerTopBar

@ExperimentalPagerApi
@Composable
internal fun FloorPagerBar(state: PagerState, items: List<String>, onFloorClick: () -> Unit) {
	PagerTopBar(
		state = state,
		title = stringResource(R.string.floor_letter),
		items = items,
		onTitleClick = onFloorClick,
		isLast = true
	)
}
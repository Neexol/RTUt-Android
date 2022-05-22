package ru.neexol.rtut.presentation.screens.schedule.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.PagerTopBar

@ExperimentalPagerApi
@Composable
internal fun DayPagerBar(state: PagerState, onTitleClick: () -> Unit) {
	PagerTopBar(
		state = state,
		title = stringResource(R.string.day_letter),
		items = stringArrayResource(R.array.days_cut).toList(),
		onTitleClick = onTitleClick,
		isLast = true
	)
}
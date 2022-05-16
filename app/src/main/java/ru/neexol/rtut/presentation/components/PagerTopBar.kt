package ru.neexol.rtut.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun PagerTopBar(
	state: PagerState,
	title: String,
	items: List<String>,
	isLast: Boolean = false,
	size: Dp = 64.dp
) {
	Column(Modifier.background(MaterialTheme.colors.surface)) {
		Row {
			TitleBox(size, title)
			Box {
				IndicatorBox(size, isLast)
				Pager(size, state, items)
				VanishingBox(size)
			}
		}
		if (isLast) Spacer(Modifier.height(8.dp))
	}
}

@Composable
private fun TitleBox(size: Dp, title: String) {
	Box(modifier = Modifier.size(size)) {
		Text(
			text = title,
			color = MaterialTheme.colors.primary,
			modifier = Modifier.align(Alignment.Center),
			style = MaterialTheme.typography.h4
		)
	}
}

@Composable
private fun IndicatorBox(size: Dp, isLast: Boolean) {
	Box(
		modifier = Modifier
			.zIndex(0f)
			.size(size)
			.background(
				MaterialTheme.colors.primaryVariant,
				if (isLast) {
					RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
				} else RectangleShape
			)
	)
}

@ExperimentalPagerApi
@Composable
private fun Pager(size: Dp, state: PagerState, items: List<String>) {
	val coroutineScope = rememberCoroutineScope()
	val pagerPadding = LocalConfiguration.current.screenWidthDp.dp - size * 2

	HorizontalPager(
		modifier = Modifier.zIndex(1f),
		count = items.size,
		state = state,
		contentPadding = PaddingValues(end = pagerPadding)
	) { page ->
		Box(
			Modifier
				.size(size)
				.clickable(
					interactionSource = remember { MutableInteractionSource() },
					indication = null
				) {
					coroutineScope.launch { state.animateScrollToPage(page) }
				}
		) {
			Text(
				text = items[page],
				modifier = Modifier.align(Alignment.Center),
				style = MaterialTheme.typography.h4
			)
		}
	}
}

@Composable
private fun VanishingBox(size: Dp) {
	val gradient = Brush.horizontalGradient(
		0f to Color.Transparent,
		1f to MaterialTheme.colors.surface
	)
	Box(
		modifier = Modifier
			.zIndex(2f)
			.background(gradient)
			.height(size)
			.fillMaxWidth()
	)
}
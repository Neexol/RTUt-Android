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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch
import ru.neexol.rtut.presentation.theme.bar

@ExperimentalPagerApi
@Composable
fun PagerTopBar(
	state: PagerState,
	title: String,
	items: List<String>,
	isLast: Boolean = false,
	size: Dp = 64.dp,
	horizontalPadding: Dp = 8.dp
) {
	Column(
		modifier = Modifier
			.background(MaterialTheme.colors.bar)
			.padding(horizontal = horizontalPadding)
	) {
		Row {
			TitleBox(size, title)
			Box {
				IndicatorBox(size, isLast)
				Pager(size, size + horizontalPadding, state, items)
			}
		}
		if (isLast) Spacer(Modifier.height(12.dp))
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
private fun Pager(size: Dp, extra: Dp, state: PagerState, items: List<String>) {
	val coroutineScope = rememberCoroutineScope()
	val endPadding = 20.dp
	val contentEndPadding = LocalConfiguration.current.screenWidthDp.dp - extra * 2 - endPadding

	HorizontalPager(
		modifier = Modifier
			.zIndex(1f)
			.padding(end = endPadding)
			.graphicsLayer { alpha = 0.99f }
			.drawWithContent {
				drawContent()
				drawRect(
					brush = Brush.horizontalGradient(listOf(Color.Black, Color.Transparent)),
					blendMode = BlendMode.DstIn
				)
			},
		count = items.size,
		state = state,
		contentPadding = PaddingValues(end = contentEndPadding)
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
package ru.neexol.rtut.presentation.grouplessons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.neexol.rtut.core.Resource
import java.math.BigDecimal

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GroupLessonsList(vm: GroupLessonsViewModel) {
	val coroutineScope = rememberCoroutineScope()
	val weekPagerState = rememberPagerState()
	val dayPagerState = rememberPagerState()
	val lessonsPagerState = rememberPagerState()

	val scrollingFollowingPair by remember {
		derivedStateOf {
			when {
				dayPagerState.isScrollInProgress -> dayPagerState to lessonsPagerState
				lessonsPagerState.isScrollInProgress -> lessonsPagerState to dayPagerState
				else -> null
			}
		}
	}

	LaunchedEffect(scrollingFollowingPair) {
		val (scrollingState, followingState) = scrollingFollowingPair ?: return@LaunchedEffect
		snapshotFlow { scrollingState.currentPage + scrollingState.currentPageOffset }
			.collect { pagePart ->
				val divideAndRemainder = BigDecimal.valueOf(pagePart.toDouble())
					.divideAndRemainder(BigDecimal.ONE)

				followingState.scrollToPage(
					divideAndRemainder[0].toInt(),
					divideAndRemainder[1].toFloat(),
				)
			}
	}

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
		Box {
			HorizontalPager(
				modifier = Modifier
					.height(96.dp)
					.zIndex(1f),
				state = dayPagerState,
				count = 6,
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
							coroutineScope.launch { dayPagerState.animateScrollToPage(page) }
						},
				) {
					Text(
						modifier = Modifier.align(Alignment.Center),
						text = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ")[page],
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
		HorizontalPager(
			state = lessonsPagerState,
			count = 6
		) { day ->
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(16.dp)
			) {
				val lessonsResource by vm.lessonsResource.collectAsState()
				val times by vm.times.collectAsState()
				(lessonsResource as? Resource.Success)?.let { res ->
					val lessons = res.data.filter {
						weekPagerState.currentPage + 1 in it.weeks && it.day == day
					}
					lessons.forEach {
						Row {
							Column {
								Text(times[it.number].begin)
								Text(times[it.number].end)
							}
							Text("${it.name} ${it.teacher}")
						}
						Divider()
					}
				}
			}
		}
	}
}
package ru.neexol.rtut.presentation.screens.map.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch
import ru.neexol.rtut.presentation.screens.map.MapViewModel

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun MapScreen(vm: MapViewModel = hiltViewModel()) {
	val coroutineScope = rememberCoroutineScope()

	val uiState = vm.uiState
	val floorsPager = rememberSaveable(
		uiState.classroom,
		saver = PagerState.Saver
	) { PagerState(vm.uiState.floor) }

	Column {
		val floors = (0..(uiState.maps?.lastIndex ?: 4)).map(Int::toString)
		FloorPagerBar(floorsPager, floors) {
			coroutineScope.launch {
				floorsPager.animateScrollToPage(uiState.floor)
			}
		}
		FindClassroomBar(vm)
		when {
			uiState.maps != null -> {
				MapBrowser(uiState, floorsPager.currentPage, vm::clearMessage)
			}
			uiState.isMapsLoading -> {
				Box(Modifier.fillMaxSize()) {
					CircularProgressIndicator(Modifier.align(Alignment.Center))
				}
			}
		}
	}
}
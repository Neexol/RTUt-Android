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
	if (!uiState.maps.isNullOrEmpty()) {
		val mapsPager = rememberSaveable(
			uiState.classroom,
			saver = PagerState.Saver
		) { PagerState(vm.uiState.floor) }

		Column {
			FloorPagerBar(mapsPager, uiState.maps.indices.map(Int::toString)) {
				coroutineScope.launch {
					mapsPager.animateScrollToPage(uiState.floor)
				}
			}
			FindClassroomBar(vm)
			MapBrowser(uiState.maps[mapsPager.currentPage], uiState.classroom)
		}
	} else if (uiState.isMapsLoading) {
		Box(Modifier.fillMaxSize()) {
			CircularProgressIndicator(Modifier.align(Alignment.Center))
		}
	}
}
package ru.neexol.rtut.presentation.screens.map

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.components.FindTopBar
import ru.neexol.rtut.presentation.components.PagerTopBar

@ExperimentalPagerApi
@Composable
fun MapScreen(vm: MapViewModel) {
	val uiState = vm.uiState
	if (!uiState.maps.isNullOrEmpty()) {
		val mapsPager = rememberSaveable(
			uiState.classroom,
			saver = PagerState.Saver
		) { PagerState(vm.uiState.floor) }

		Column {
			FloorPagerBar(mapsPager, uiState.maps.indices.map(Int::toString))
			FindClassroomBar(vm)
			ZoomableMap(uiState.maps[mapsPager.currentPage], uiState.classroom)
		}
	}
}

@ExperimentalPagerApi
@Composable
private fun FloorPagerBar(state: PagerState, items: List<String>) {
	PagerTopBar(
		state = state,
		title = stringResource(R.string.floor_letter),
		items = items,
		isLast = true
	)
}

@Composable
private fun FindClassroomBar(vm: MapViewModel) {
	FindTopBar(
		value = vm.classroom,
		placeholder = stringResource(R.string.classroom),
		onValueChange = { vm.classroom = it.trimStart().uppercase() },
		onImeAction = { vm.fetchMaps() }
	)
}

@Composable
private fun ZoomableMap(map: Bitmap, classroom: String) {
	var offset by rememberSaveable(
		classroom,
		saver = Saver(
			save = { it.value.x to it.value.y },
			restore = { mutableStateOf(Offset(it.first, it.second)) }
		)
	) { mutableStateOf(Offset(0f, 0f)) }
	var zoom by rememberSaveable(classroom) { mutableStateOf(1f) }

	Image(
		modifier = Modifier
			.clip(RectangleShape)
			.fillMaxSize()
			.pointerInput(classroom) {
				detectTransformGestures { centroid, pan, gestureZoom, _ ->
					val oldScale = zoom
					val newScale = (zoom * gestureZoom).coerceIn(1f, 15f)
					offset = (offset + centroid / oldScale) - (centroid / newScale + pan / oldScale)
					zoom = newScale
				}
			}
			.graphicsLayer {
				translationX = -offset.x * zoom
				translationY = -offset.y * zoom
				scaleX = zoom
				scaleY = zoom
				transformOrigin = TransformOrigin(0f, 0f)
			},
		bitmap = map.asImageBitmap(),
		contentDescription = null
	)
}
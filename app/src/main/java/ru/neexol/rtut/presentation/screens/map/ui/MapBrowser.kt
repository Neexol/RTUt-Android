package ru.neexol.rtut.presentation.screens.map.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.neexol.rtut.R
import ru.neexol.rtut.presentation.screens.map.MapUiState

@Composable
internal fun MapBrowser(uiState: MapUiState, floor: Int, clearMessage: () -> Unit) {
	val coroutineScope = rememberCoroutineScope()
	val snackbarHostState = remember { SnackbarHostState() }
	LaunchedEffect(uiState.message) {
		uiState.message?.let {
			coroutineScope.launch {
				snackbarHostState.currentSnackbarData?.dismiss()
				snackbarHostState.showSnackbar(it)
				clearMessage()
			}
		}
	}

	var offset by rememberSaveable(
		uiState.classroom,
		saver = Saver(
			save = { it.value.x to it.value.y },
			restore = { mutableStateOf(Offset(it.first, it.second)) }
		)
	) { mutableStateOf(Offset.Zero) }
	var zoom by rememberSaveable(uiState.classroom) { mutableStateOf(1f) }

	Box {
		Image(
			modifier = Modifier
				.clip(RectangleShape)
				.fillMaxSize()
				.pointerInput(uiState.classroom) {
					detectTransformGestures { centroid, pan, gestureZoom, _ ->
						val oldScale = zoom
						val newScale = (zoom * gestureZoom).coerceIn(1f, 15f)
						offset =
							(offset + centroid / oldScale) - (centroid / newScale + pan / oldScale)
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
			bitmap = uiState.maps!![floor].asImageBitmap(),
			contentDescription = null
		)
		Column(
			modifier = Modifier.align(Alignment.BottomEnd),
			horizontalAlignment = Alignment.End
		) {
			FitButton {
				offset = Offset.Zero
				zoom = 1f
			}
			SnackbarHost(
				hostState = snackbarHostState,
				modifier = Modifier.animateContentSize()
			)
		}
	}
}

@Composable
private fun FitButton(onClick: () -> Unit) {
	FloatingActionButton(
		modifier = Modifier.padding(20.dp),
		shape = RoundedCornerShape(16.dp),
		elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
		onClick = onClick
	) {
		Icon(
			painter = painterResource(R.drawable.ic_fit_map_24),
			contentDescription = null
		)
	}
}
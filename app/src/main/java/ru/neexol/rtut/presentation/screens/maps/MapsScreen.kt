package ru.neexol.rtut.presentation.screens.maps

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

@Composable
fun MapsScreen(vm: MapsViewModel) {
	vm.uiState.maps?.let { maps ->
		var selected by rememberSaveable(vm.uiState.classroom) { mutableStateOf(vm.uiState.floor) }
		Column {
			Row {
				repeat(maps.size) {
					RadioButton(selected = it == selected, onClick = { selected = it })
				}
			}
			Divider()
			FindField(
				value = vm.classroom,
				onValueChange = { vm.classroom = it.trimStart().uppercase() },
				onImeAction = { vm.fetchMaps() }
			)
			Divider()
			ZoomableMap(maps[selected], vm.uiState.classroom)
		}
	}
}

@Composable
fun FindField(value: String, onValueChange: (String) -> Unit, onImeAction: () -> Unit) {
	val focusManager = LocalFocusManager.current
	TextField(
		value = value,
		onValueChange = onValueChange,
		label = { Text("Classroom") },
		singleLine = true,
		keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
		keyboardActions = KeyboardActions {
			onImeAction()
			focusManager.clearFocus()
		}
	)
}

@Composable
fun ZoomableMap(map: Bitmap, classroom: String) {
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
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
	var scale by rememberSaveable(classroom) { mutableStateOf(1f) }
	var offsetX by rememberSaveable(classroom) { mutableStateOf(0f) }
	var offsetY by rememberSaveable(classroom) { mutableStateOf(0f) }

	Image(
		modifier = Modifier
			.fillMaxSize()
			.pointerInput(classroom) {
				detectTransformGestures { _, pan, zoom, _ ->
					scale = (scale * zoom).coerceIn(1f, 15f)
					offsetX += pan.x
					offsetY += pan.y
				}
			}
			.graphicsLayer(
				scaleX = scale,
				scaleY = scale,
				translationX = offsetX,
				translationY = offsetY
			),
		bitmap = map.asImageBitmap(),
		contentDescription = null
	)
}
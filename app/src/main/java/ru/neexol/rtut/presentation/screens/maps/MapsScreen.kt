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
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

@Composable
fun MapsScreen(vm: MapsViewModel) {
	val uiState = vm.uiState
	val maps = uiState.maps
	if (maps != null) {
		var selected by remember(uiState.floor) { mutableStateOf(uiState.floor) }
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
			ZoomableMap(
				mapProvider = { maps[selected] }
			)
		}
	}
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FindField(value: String, onValueChange: (String) -> Unit, onImeAction: () -> Unit) {
	val keyboardController = LocalSoftwareKeyboardController.current
	TextField(
		value = value,
		onValueChange = onValueChange,
		label = { Text("Classroom") },
		singleLine = true,
		keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
		keyboardActions = KeyboardActions {
			onImeAction()
			keyboardController?.hide()
		}
	)
}

@Composable
fun ZoomableMap(mapProvider: () -> Bitmap) {
	var scale by remember { mutableStateOf(1f) }
	var translate by remember { mutableStateOf(Offset(0f, 0f)) }
	Image(
		modifier = Modifier
			.fillMaxSize()
			.pointerInput(Unit) {
				detectTransformGestures { _, pan, zoom, _ ->
					scale = (scale * zoom).coerceIn(1f, 15f)
					translate += pan
				}
			}
			.graphicsLayer(
				scaleX = scale,
				scaleY = scale,
				translationX = translate.x,
				translationY = translate.y
			),
		bitmap = mapProvider().asImageBitmap(),
		contentDescription = null
	)
}
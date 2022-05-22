package ru.neexol.rtut.presentation.screens.map.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import ru.neexol.rtut.R

@Composable
internal fun MapBrowser(map: Bitmap, classroom: String) {
	var offset by rememberSaveable(
		classroom,
		saver = Saver(
			save = { it.value.x to it.value.y },
			restore = { mutableStateOf(Offset(it.first, it.second)) }
		)
	) { mutableStateOf(Offset.Zero) }
	var zoom by rememberSaveable(classroom) { mutableStateOf(1f) }

	Box {
		Image(
			modifier = Modifier
				.clip(RectangleShape)
				.fillMaxSize()
				.pointerInput(classroom) {
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
			bitmap = map.asImageBitmap(),
			contentDescription = null
		)
		FitButton {
			offset = Offset.Zero
			zoom = 1f
		}
	}
}

@Composable
private fun BoxScope.FitButton(onClick: () -> Unit) {
	FloatingActionButton(
		modifier = Modifier
			.align(Alignment.BottomEnd)
			.padding(20.dp),
		shape = RoundedCornerShape(16.dp),
		elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
		onClick = onClick
	) {
		Icon(painter = painterResource(R.drawable.ic_fit_map_24), contentDescription = null)
	}
}
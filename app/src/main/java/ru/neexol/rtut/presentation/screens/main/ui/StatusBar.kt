package ru.neexol.rtut.presentation.screens.main.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.neexol.rtut.presentation.theme.backgroundVariant

@Composable
internal fun StatusBar(isScrim: Boolean) {
	Box {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.background(MaterialTheme.colors.backgroundVariant)
		) {
			Box(
				modifier = Modifier
					.offset(x = 64.dp + 8.dp)
					.background(MaterialTheme.colors.primaryVariant)
					.padding(WindowInsets.statusBars.asPaddingValues())
					.width(64.dp)
			)
		}
		AnimatedVisibility(
			visible = isScrim,
			enter = fadeIn(),
			exit = fadeOut()
		) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.background(Color.Black.copy(alpha = 0.3f))
					.padding(WindowInsets.statusBars.asPaddingValues())
			)
		}
	}
}
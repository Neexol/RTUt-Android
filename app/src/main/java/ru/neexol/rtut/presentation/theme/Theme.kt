package ru.neexol.rtut.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun RTUtTheme(content: @Composable () -> Unit) = MaterialTheme(
	colors = RTUtPalette,
	typography = Typography,
	shapes = Shapes,
	content = content
)
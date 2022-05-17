package ru.neexol.rtut.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFFB9F2FF)
val PrimaryVariant = Color(0xFF475255)
val Background = Color(0xFF222222)
val Bar = Color(0xFF303030)
val Surface = Color(0xFF3D3D3D)

val RTUtPalette = darkColors(
	primary = Primary,
	primaryVariant = PrimaryVariant,
	secondary = Primary,
	secondaryVariant = PrimaryVariant,
	background = Background,
	surface = Surface
)

val Colors.bar: Color get() = if (isLight) Bar else Bar
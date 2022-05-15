package ru.neexol.rtut.presentation.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFFB9F2FF)
val PrimaryVariant = Color(0xFF475255)
val Background = Color(0xFF222222)
val Surface = Color(0xFF3D3D3D)

val RTUtPalette = darkColors(
	primary = Primary,
	primaryVariant = PrimaryVariant,
	secondary = Primary,
	secondaryVariant = PrimaryVariant,
	background = Background,
	surface = Surface

/* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)
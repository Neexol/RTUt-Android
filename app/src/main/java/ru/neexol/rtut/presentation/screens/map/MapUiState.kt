package ru.neexol.rtut.presentation.screens.map

import android.graphics.Bitmap

data class MapUiState(
	val maps: List<Bitmap>? = null,
	val isMapsLoading: Boolean = false,
	val floor: Int = 0,
	val classroom: String = "",
	val message: String? = null
)
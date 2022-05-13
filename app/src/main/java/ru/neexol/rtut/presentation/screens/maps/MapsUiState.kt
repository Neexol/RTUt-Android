package ru.neexol.rtut.presentation.screens.maps

import android.graphics.Bitmap

data class MapsUiState(
	val maps: List<Bitmap>? = null,
	val isMapsLoading: Boolean = false,
	val floor: Int = 0,
	val classroom: String = "",
	val message: String? = null
)
package ru.neexol.rtut.presentation.screens.maps

data class MapsUiState(
	val maps: List<String>? = null,
	val isMapsLoading: Boolean = false,
	val message: String? = null
)
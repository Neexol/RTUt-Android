package ru.neexol.rtut.presentation.screens.settings

data class SettingsAuthorUiState(
	val author: String? = null,
	val isAuthorLoading: Boolean = false,
	val message: String? = null
)
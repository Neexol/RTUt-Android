package ru.neexol.rtut.presentation.screens.settings

data class SettingsGroupUiState(
	val group: String? = null,
	val isGroupLoading: Boolean = false,
	val message: String? = null
)
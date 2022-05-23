package ru.neexol.rtut.presentation.screens.notes

import ru.neexol.rtut.data.notes.models.Note

data class NotesUiState(
	val notes: Pair<List<Note>, List<Note>>? = null,
	val isNotesLoading: Boolean = false,
	val message: String? = null
)
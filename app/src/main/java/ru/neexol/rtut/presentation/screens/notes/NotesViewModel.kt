package ru.neexol.rtut.presentation.screens.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.notes.NotesRepository
import ru.neexol.rtut.data.notes.models.NoteType
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
	private val repo: NotesRepository
) : ViewModel() {
	var lesson: Lesson? = null
	private var week = ""

	fun setLesson(newLesson: Lesson?, newWeek: String) {
		newLesson ?: return
		lesson = newLesson
		week = newWeek
		uiState = NotesUiState()
		fetchAuthor()
		fetchNotes()
	}

	var uiState by mutableStateOf(NotesUiState())
		private set

	private var fetchNotesJob: Job? = null
	private fun fetchNotes() {
		fetchNotesJob?.cancel()
		fetchNotesJob = viewModelScope.launch {
			repo.getNotes(lesson!!.id, week).collect { notes ->
				uiState = notes.to(
					onSuccess = { NotesUiState(notes = it) },
					onFailure = { uiState.copy(isNotesLoading = false, message = it.toString()) },
					onLoading = { uiState.copy(isNotesLoading = true) },
				)
			}
		}
	}

	private var createNoteJob: Job? = null
	fun createNote(type: NoteType) {
		createNoteJob?.cancel()
		createNoteJob = viewModelScope.launch {
			repo.putNote(null, "Simple text", lesson!!.id, week, type).collect {
				println(it)
			}
		}
	}

	var author by mutableStateOf("")
	private var fetchAuthorJob: Job? = null
	private fun fetchAuthor() {
		fetchAuthorJob?.cancel()
		fetchAuthorJob = viewModelScope.launch {
			repo.getAuthor().collect { authorRes ->
				author = authorRes.to(
					onSuccess = { it },
					onFailure = { "" },
					onLoading = { "" },
				)
			}
		}
	}
}
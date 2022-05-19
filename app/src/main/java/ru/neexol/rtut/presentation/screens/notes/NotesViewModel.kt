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
import ru.neexol.rtut.data.notes.models.Note
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
		clearState()
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

	fun setNote(note: Note?) {
		println("setNote")
		note ?: return
		noteId = note.id
		noteText = note.text
		typeToggled = note.type == NoteType.PUBLIC
		weekToggled = note.weeks.contains(' ')
	}

	private var noteId: String? = null

	var noteText by mutableStateOf("")

	var typeToggled by mutableStateOf(false)
	fun toggleType() { typeToggled = !typeToggled }

	var weekToggled by mutableStateOf(false)
	fun toggleWeek() { weekToggled = !weekToggled }

	private var putNoteJob: Job? = null
	fun putNote() {
		putNoteJob?.cancel()
		putNoteJob = viewModelScope.launch {
			repo.putNote(
				noteId,
				noteText,
				lesson!!.id,
				if (weekToggled) lesson!!.weeks.joinToString(" ") else week,
				if (typeToggled) NoteType.PUBLIC else NoteType.PRIVATE
			).collect {
				fetchNotes()
			}
		}
	}

	private var deleteNoteJob: Job? = null
	fun deleteNote() {
		deleteNoteJob?.cancel()
		deleteNoteJob = viewModelScope.launch {
			repo.deleteNote(noteId!!).collect {
				clearState()
				fetchNotes()
			}
		}
	}

	fun clearState() {
		println("Clear")
		uiState = NotesUiState()
		noteId = null
		noteText = ""
		typeToggled = false
		weekToggled = false
	}
}
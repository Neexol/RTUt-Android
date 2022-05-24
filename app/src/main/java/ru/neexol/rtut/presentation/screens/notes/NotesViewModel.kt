package ru.neexol.rtut.presentation.screens.notes

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.neexol.rtut.R
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.data.notes.NotesRepository
import ru.neexol.rtut.data.notes.models.Note
import ru.neexol.rtut.data.notes.models.NoteType
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
	private val app: Application,
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
					onFailure = {
						uiState.copy(
							isNotesLoading = false,
							message = app.getString(R.string.connection_error)
						)
					},
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
		noteId = null
		noteText = ""
		note ?: return
		noteId = note.id
		noteText = note.text
		isPublicType = note.type == NoteType.PUBLIC
		isAllWeeks = note.weeks.contains(' ')
	}

	private var noteId: String? = null

	var noteText by mutableStateOf("")

	var isPublicType by mutableStateOf(false)
	fun toggleType() { isPublicType = !isPublicType }

	var isAllWeeks by mutableStateOf(false)
	fun toggleWeek() { isAllWeeks = !isAllWeeks }

	private var putNoteJob: Job? = null
	fun putNote() {
		putNoteJob?.cancel()
		putNoteJob = viewModelScope.launch {
			repo.putNote(
				noteId,
				noteText,
				lesson!!.id,
				if (isAllWeeks) lesson!!.weeks.joinToString(" ") else week,
				if (isPublicType) NoteType.PUBLIC else NoteType.PRIVATE
			).collect { resource ->
//				clearState()
				fetchNotes()
				if (resource is Resource.Failure) {
					uiState = uiState.copy(
						message = app.getString(
							if (resource.cause is HttpException && resource.cause.code() == 409) {
								R.string.conflict_note_error
							} else {
								R.string.connection_error
							}
						)
					)
				}
			}
		}
	}

	private var deleteNoteJob: Job? = null
	fun deleteNote() {
		deleteNoteJob?.cancel()
		deleteNoteJob = viewModelScope.launch {
			repo.deleteNote(noteId!!).collect { resource ->
//				clearState()
				fetchNotes()
				if (resource is Resource.Failure) {
					uiState = uiState.copy(
						message = app.getString(
							if (resource.cause is HttpException && resource.cause.code() == 409) {
								R.string.conflict_note_error
							} else {
								R.string.connection_error
							}
						)
					)
				}
			}
		}
	}

	fun clearState() {
		uiState = NotesUiState()
		noteId = null
		noteText = ""
	}

	fun clearMessage() {
		uiState = uiState.copy(message = null)
	}
}
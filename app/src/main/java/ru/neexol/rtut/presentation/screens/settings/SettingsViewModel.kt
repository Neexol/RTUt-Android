package ru.neexol.rtut.presentation.screens.settings

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
import ru.neexol.rtut.data.lessons.LessonsRepository
import ru.neexol.rtut.data.notes.NotesRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val app: Application,
	private val lessonsRepo: LessonsRepository,
	private val notesRepo: NotesRepository
) : ViewModel() {
	var groupUiState by mutableStateOf(SettingsGroupUiState())
		private set

	private var fetchGroupJob: Job? = null
	init { fetchGroup() }
	private fun fetchGroup() {
		fetchGroupJob?.cancel()
		fetchGroupJob = viewModelScope.launch {
			lessonsRepo.getGroup().collect {
				groupUiState = SettingsGroupUiState(group = it)
			}
		}
	}

	private var editGroupJob: Job? = null
	fun editGroup(newGroup: String) {
		editGroupJob?.cancel()
		editGroupJob = viewModelScope.launch {
			if (newGroup matches "[А-ЯЁ]{4}\\d{4}".toRegex()) {
				lessonsRepo.editGroup(
					StringBuilder(newGroup)
						.insert(4, '-')
						.insert(7, '-')
						.toString()
				).collect { group ->
					groupUiState = group.to(
						onSuccess = {
							SettingsGroupUiState(
								group = it,
								message = app.getString(R.string.group_changed)
							)
						},
						onFailure = {
							val msg = app.applicationContext.getString(
								if (it is HttpException && it.code() == 404) {
									R.string.group_not_found
								} else {
									R.string.connection_error
								}
							)
							groupUiState.copy(isGroupLoading = false, message = msg)
						},
						onLoading = { groupUiState.copy(isGroupLoading = true) }
					)
				}
			} else {
				groupUiState = groupUiState.copy(message = app.getString(R.string.incorrect_group))
			}
		}
	}

	fun clearGroupMessage() {
		groupUiState = groupUiState.copy(message = null)
	}

	var authorUiState by mutableStateOf(SettingsAuthorUiState())
		private set

	private var fetchAuthorJob: Job? = null
	init { fetchAuthor() }
	private fun fetchAuthor() {
		fetchAuthorJob?.cancel()
		fetchAuthorJob = viewModelScope.launch {
			notesRepo.getAuthor().collect { author ->
				authorUiState = author.to(
					onSuccess = { SettingsAuthorUiState(author = it) },
					onFailure = {
						authorUiState.copy(
							isAuthorLoading = false,
							message = app.getString(R.string.connection_error)
						)
					},
					onLoading = { authorUiState.copy(isAuthorLoading = true) }
				)
			}
		}
	}

	private var editAuthorJob: Job? = null
	fun editAuthor(newAuthor: String?) {
		editAuthorJob?.cancel()
		if (newAuthor != null) {
			editAuthorJob = viewModelScope.launch {
				notesRepo.editAuthor(newAuthor).collect { author ->
					authorUiState = author.to(
						onSuccess = {
							SettingsAuthorUiState(
								author = it,
								message = app.getString(R.string.identifier_changed)
							)
						},
						onFailure = {
							val msg = app.getString(
								if (it is HttpException && it.code() == 404) {
									R.string.identifier_not_found
								} else {
									R.string.connection_error
								}
							)
							authorUiState.copy(isAuthorLoading = false, message = msg)
						},
						onLoading = { authorUiState.copy(isAuthorLoading = true) }
					)
				}
			}
		} else {
			authorUiState = authorUiState.copy(message = app.getString(R.string.clipboard_empty))
		}
	}

	fun clearAuthorMessage() {
		authorUiState = authorUiState.copy(message = null)
	}
}
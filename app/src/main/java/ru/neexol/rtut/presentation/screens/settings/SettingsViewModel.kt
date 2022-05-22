package ru.neexol.rtut.presentation.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.neexol.rtut.data.lessons.LessonsRepository
import ru.neexol.rtut.data.notes.NotesRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
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

//	var newGroup by mutableStateOf("")
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
//							newGroup = ""
							SettingsGroupUiState(group = it)
						},
						onFailure = { groupUiState.copy(isGroupLoading = false, message = it.toString()) },
						onLoading = { groupUiState.copy(isGroupLoading = true) }
					)
				}
			}
		}
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
					onFailure = { authorUiState.copy(isAuthorLoading = false, message = it.toString()) },
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
						onSuccess = { SettingsAuthorUiState(author = it) },
						onFailure = { authorUiState.copy(isAuthorLoading = false, message = it.toString()) },
						onLoading = { authorUiState.copy(isAuthorLoading = true) }
					)
				}
			}
		} else {
			authorUiState = authorUiState.copy(message = "Ваш буфер обмена пуст")
		}
	}
}
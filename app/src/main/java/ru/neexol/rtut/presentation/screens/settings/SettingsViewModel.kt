package ru.neexol.rtut.presentation.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.neexol.rtut.domain.lessons.usecases.EditGroupUseCase
import ru.neexol.rtut.domain.lessons.usecases.GetGroupUseCase
import ru.neexol.rtut.domain.notes.usecases.EditAuthorUseCase
import ru.neexol.rtut.domain.notes.usecases.GetAuthorUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val getGroupUseCase: GetGroupUseCase,
	private val editGroupUseCase: EditGroupUseCase,
	private val getAuthorUseCase: GetAuthorUseCase,
	private val editAuthorUseCase: EditAuthorUseCase
) : ViewModel() {
	var groupUiState by mutableStateOf(SettingsGroupUiState())
		private set

	private var fetchGroupJob: Job? = null
	init { fetchGroup() }
	fun fetchGroup() {
		fetchGroupJob?.cancel()
		fetchGroupJob = viewModelScope.launch {
			getGroupUseCase().collect {
				groupUiState = SettingsGroupUiState(group = it)
			}
		}
	}

	var newGroup by mutableStateOf("")
	private var editGroupJob: Job? = null
	fun editGroup() {
		editGroupJob?.cancel()
		editGroupJob = viewModelScope.launch {
			if (newGroup matches "[А-ЯЁ]{4}\\d{4}".toRegex()) {
				editGroupUseCase(
					StringBuilder(newGroup)
						.insert(4, '-')
						.insert(7, '-')
						.toString()
				).collect { group ->
					groupUiState = group.to(
						onSuccess = { SettingsGroupUiState(group = it) },
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
	fun fetchAuthor() {
		fetchAuthorJob?.cancel()
		fetchAuthorJob = viewModelScope.launch {
			getAuthorUseCase().collect { author ->
				authorUiState = author.to(
					onSuccess = { SettingsAuthorUiState(author = it) },
					onFailure = { authorUiState.copy(isAuthorLoading = false, message = it.toString()) },
					onLoading = { authorUiState.copy(isAuthorLoading = true) }
				)
			}
		}
	}

	var newAuthor by mutableStateOf("")
	private var editAuthorJob: Job? = null
	fun editAuthor() {
		editAuthorJob?.cancel()
		editAuthorJob = viewModelScope.launch {
			editAuthorUseCase(newAuthor).collect { author ->
				authorUiState = author.to(
					onSuccess = { SettingsAuthorUiState(author = it) },
					onFailure = { authorUiState.copy(isAuthorLoading = false, message = it.toString()) },
					onLoading = { authorUiState.copy(isAuthorLoading = true) }
				)
			}
		}
	}
}
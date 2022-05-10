package ru.neexol.rtut.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.lessons.usecases.EditGroupUseCase
import ru.neexol.rtut.domain.lessons.usecases.GetGroupUseCase
import ru.neexol.rtut.domain.notes.usecases.EditAuthorUseCase
import ru.neexol.rtut.domain.notes.usecases.GetAuthorUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	getGroupUseCase: GetGroupUseCase,
	private val editGroupUseCase: EditGroupUseCase,
	getAuthorUseCase: GetAuthorUseCase,
	private val editAuthorUseCase: EditAuthorUseCase
) : ViewModel() {
	val groupFlow = getGroupUseCase.resultFlow.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		""
	)

	val editGroupResourceFlow = editGroupUseCase.resultFlow.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		null
	)

	val authorResourceFlow = getAuthorUseCase.resultFlow.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		Resource.Loading
	)

	val editAuthorResourceFlow = editAuthorUseCase.resultFlow.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		null
	)

	var newGroupState by mutableStateOf("")
	fun editGroup() {
		if (newGroupState matches "[А-ЯЁ]{4}\\d{4}".toRegex()) {
			val newGroup = StringBuilder(newGroupState)
				.insert(4, '-')
				.insert(7, '-')
				.toString()
			editGroupUseCase.launch { group = newGroup }
		}
	}

	var newAuthorState by mutableStateOf("")
	fun editAuthor() = editAuthorUseCase.launch { author = newAuthorState }
}
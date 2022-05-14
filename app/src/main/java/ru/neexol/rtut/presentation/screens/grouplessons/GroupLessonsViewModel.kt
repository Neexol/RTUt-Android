package ru.neexol.rtut.presentation.screens.grouplessons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.neexol.rtut.domain.lessons.usecases.GetGroupLessonsUseCase
import ru.neexol.rtut.domain.lessons.usecases.GetGroupUseCase
import ru.neexol.rtut.domain.lessons.usecases.GetTimesUseCase
import javax.inject.Inject

@HiltViewModel
class GroupLessonsViewModel @Inject constructor(
	private val getGroupLessonsUseCase: GetGroupLessonsUseCase,
	private val getTimesUseCase: GetTimesUseCase,
	private val getGroupUseCase: GetGroupUseCase
) : ViewModel() {
	var uiState by mutableStateOf(GroupLessonsUiState())
		private set

	private var group: String? = null
	private var fetchGroupJob: Job? = null
	fun fetchGroup() {
		fetchGroupJob?.cancel()
		fetchGroupJob = viewModelScope.launch {
			getGroupUseCase().collect {
				if (it != group) {
					group = it
					fetchLessons()
				}
			}
		}
	}

	private var fetchLessonsJob: Job? = null
	private fun fetchLessons() {
		fetchLessonsJob?.cancel()
		fetchLessonsJob = viewModelScope.launch {
			combine(getGroupLessonsUseCase(), getTimesUseCase()) { lessons, times ->
				lessons.to(
					onSuccess = { GroupLessonsUiState(lessons = it, times = times) },
					onFailure = { uiState.copy(isLessonsLoading = false, message = it.toString()) },
					onLoading = { uiState.copy(isLessonsLoading = true) }
				)
			}.collect {
				uiState = it
			}
		}
	}
}
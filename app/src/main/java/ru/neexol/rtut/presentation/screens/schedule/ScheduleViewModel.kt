package ru.neexol.rtut.presentation.screens.schedule

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.neexol.rtut.core.Utils
import ru.neexol.rtut.data.lessons.LessonsRepository
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
	private val repo: LessonsRepository
) : ViewModel() {
	val dayWeek = Utils.getDayAndWeek()

	var uiState by mutableStateOf(ScheduleUiState())
		private set

	private var group: String? = null
	private var fetchGroupJob: Job? = null
	fun fetchGroup() {
		fetchGroupJob?.cancel()
		fetchGroupJob = viewModelScope.launch {
			repo.getGroup().collect {
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
			combine(repo.getGroupLessons(), repo.getTimes()) { lessons, times ->
				lessons.to(
					onSuccess = { ScheduleUiState(lessons = it, times = times) },
					onFailure = { uiState.copy(isLessonsLoading = false, message = it.toString()) },
					onLoading = { uiState.copy(isLessonsLoading = true) }
				)
			}.collect {
				uiState = it
			}
		}
	}
}
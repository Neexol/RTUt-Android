package ru.neexol.rtut.presentation.screens.teacher

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.neexol.rtut.R
import ru.neexol.rtut.core.Utils
import ru.neexol.rtut.data.lessons.LessonsRepository
import javax.inject.Inject

@HiltViewModel
class TeacherViewModel @Inject constructor(
	private val app: Application,
	private val repo: LessonsRepository
) : ViewModel() {
	val dayWeek = Utils.getDayAndWeek()

	var uiState by mutableStateOf(TeacherUiState())
		private set

	var teacher by mutableStateOf("")
	private var fetchJob: Job? = null
	fun fetchLessons() {
		fetchJob?.cancel()
		if (teacher.isNotEmpty()) {
			fetchJob = viewModelScope.launch {
				combine(repo.getTeacherLessons(teacher), repo.getTimes()) { lessons, times ->
					lessons.to(
						onSuccess = { TeacherUiState(lessons = it, times = times) },
						onFailure = {
							TeacherUiState(
								message = app.getString(R.string.connection_error)
							)
						},
						onLoading = { TeacherUiState(isLessonsLoading = true) }
					)
				}.collect {
					uiState = it
				}
			}
		} else {
			uiState = TeacherUiState()
		}
	}

	fun clearMessage() {
		uiState = uiState.copy(message = null)
	}
}
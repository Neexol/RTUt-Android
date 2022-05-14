package ru.neexol.rtut.presentation.screens.teacherlessons

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
import ru.neexol.rtut.data.lessons.LessonsRepository
import javax.inject.Inject

@HiltViewModel
class TeacherLessonsViewModel @Inject constructor(
	private val repo: LessonsRepository
) : ViewModel() {
	var uiState by mutableStateOf(TeacherLessonsUiState())
		private set

	var teacher by mutableStateOf("")
	private var fetchJob: Job? = null
	fun fetchLessons() {
		fetchJob?.cancel()
		fetchJob = viewModelScope.launch {
			combine(repo.getTeacherLessons(teacher), repo.getTimes()) { lessons, times ->
				lessons.to(
					onSuccess = { TeacherLessonsUiState(lessons = it, times = times,) },
					onFailure = { TeacherLessonsUiState(message = it.toString()) },
					onLoading = { TeacherLessonsUiState(isLessonsLoading = true) }
				)
			}.collect {
				uiState = it
			}
		}
	}
}
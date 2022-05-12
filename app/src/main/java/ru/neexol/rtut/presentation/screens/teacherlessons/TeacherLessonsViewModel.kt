package ru.neexol.rtut.presentation.screens.teacherlessons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ru.neexol.rtut.domain.lessons.usecases.GetTeacherLessonsUseCase
import ru.neexol.rtut.domain.lessons.usecases.GetTimesUseCase
import javax.inject.Inject

@HiltViewModel
class TeacherLessonsViewModel @Inject constructor(
	private val getTeacherLessonsUseCase: GetTeacherLessonsUseCase,
	getTimesUseCase: GetTimesUseCase
) : ViewModel() {
	val uiStateFlow = combine(
		getTeacherLessonsUseCase.resultFlow,
		getTimesUseCase.resultFlow
	) { lessonsResource, times ->
		lessonsResource?.to(
			onSuccess = { TeacherLessonsUiState(times = times, lessons = it) },
			onFailure = { TeacherLessonsUiState(times = times, message = "Не удалось загрузить расписание") },
			onLoading = { TeacherLessonsUiState(times = times, isLessonsLoading = true) }
		) ?: TeacherLessonsUiState(times = times)
	}.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		TeacherLessonsUiState()
	)

	var teacherState by mutableStateOf("")

	fun loadLessons() = getTeacherLessonsUseCase.launch {
		this.teacher = teacherState
	}
}
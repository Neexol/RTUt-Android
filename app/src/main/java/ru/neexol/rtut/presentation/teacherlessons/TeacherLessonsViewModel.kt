package ru.neexol.rtut.presentation.teacherlessons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.lessons.models.DEFAULT_TIMES
import ru.neexol.rtut.domain.lessons.usecases.GetTeacherLessonsUseCase
import ru.neexol.rtut.domain.lessons.usecases.GetTimesUseCase
import javax.inject.Inject

@HiltViewModel
class TeacherLessonsViewModel @Inject constructor(
	private val getTeacherLessonsUseCase: GetTeacherLessonsUseCase,
	getTimesUseCase: GetTimesUseCase
) : ViewModel() {
	val lessonsResourceFlow = getTeacherLessonsUseCase.resultFlow.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		Resource.Success(emptyList())
	)

	val timesFlow = getTimesUseCase.resultFlow.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		DEFAULT_TIMES
	)

	var teacherState by mutableStateOf("")

	fun loadLessons() = getTeacherLessonsUseCase.launch {
		this.teacher = teacherState
	}
}
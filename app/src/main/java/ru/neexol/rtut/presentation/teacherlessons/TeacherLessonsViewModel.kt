package ru.neexol.rtut.presentation.teacherlessons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.neexol.rtut.core.Constants
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.models.Lesson
import ru.neexol.rtut.domain.usecases.GetTeacherLessons
import ru.neexol.rtut.domain.usecases.GetTimes
import javax.inject.Inject

@HiltViewModel
class TeacherLessonsViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle,
	private val getTeacherLessonsUseCase: GetTeacherLessons,
	private val getTimesUseCase: GetTimes
) : ViewModel() {
	init {
		viewModelScope.launch(Dispatchers.IO) {
			_times.emit(getTimesUseCase())
		}
	}

	private val _lessonsResource = MutableStateFlow<Resource<List<Lesson>>>(Resource.Success(emptyList()))
	val lessonsResource = _lessonsResource.asStateFlow()

	private val _times = MutableStateFlow(Constants.DEFAULT_TIMES)
	val times = _times.asStateFlow()

	var teacher by mutableStateOf("")

	fun loadLessons() {
		viewModelScope.launch(Dispatchers.IO) {
			_lessonsResource.emit(getTeacherLessonsUseCase(teacher))
		}
	}
}
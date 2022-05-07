package ru.neexol.rtut.presentation.grouplessons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.lessons.models.DEFAULT_TIMES
import ru.neexol.rtut.data.lessons.models.Lesson
import ru.neexol.rtut.domain.lessons.usecases.GetGroupLessons
import ru.neexol.rtut.domain.lessons.usecases.GetTimes
import javax.inject.Inject

@HiltViewModel
class GroupLessonsViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle,
	private val getGroupLessonsUseCase: GetGroupLessons,
	private val getTimesUseCase: GetTimes
) : ViewModel() {
	init {
		loadLessons()
	}

	private val _lessonsResource = MutableStateFlow<Resource<List<Lesson>>>(Resource.Success(emptyList()))
	val lessonsResource = _lessonsResource.asStateFlow()

	private val _times = MutableStateFlow(DEFAULT_TIMES)
	val times = _times.asStateFlow()

	fun loadLessons() {
		viewModelScope.launch(Dispatchers.IO) {
			getGroupLessonsUseCase().collect {
				_lessonsResource.emit(it)
			}
			_times.emit(getTimesUseCase())
		}
	}
}
package ru.neexol.rtut.presentation.grouplessons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.models.LessonsWithTimes
import ru.neexol.rtut.domain.usecases.GetGroupLessons
import javax.inject.Inject

@HiltViewModel
class GroupLessonsViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle,
	private val getGroupLessonsUseCase: GetGroupLessons
) : ViewModel() {
	init {
		loadLessons()
	}

	private val _lessonsResource = MutableStateFlow<Resource<LessonsWithTimes>>(Resource.Loading)
	val lessonsResource = _lessonsResource.asStateFlow()

	fun loadLessons() {
		viewModelScope.launch(Dispatchers.IO) {
			getGroupLessonsUseCase().collect {
				_lessonsResource.emit(it)
			}
		}
	}
}
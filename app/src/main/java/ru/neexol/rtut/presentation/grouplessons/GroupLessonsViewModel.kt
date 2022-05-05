package ru.neexol.rtut.presentation.grouplessons

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.models.Lesson
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

	private val _groupLessonsResource = MutableStateFlow<Resource<List<Lesson>>>(Resource.Loading)
	val groupLessonsResource = _groupLessonsResource.asStateFlow()

	fun loadLessons() {
		viewModelScope.launch(Dispatchers.IO) {
			getGroupLessonsUseCase().collect {
				_groupLessonsResource.emit(it)
			}
		}
	}
}
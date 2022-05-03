package ru.neexol.rtut.presentation.grouplessons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
	var lessonsResource by mutableStateOf<Resource<List<Lesson>>>(Resource.Loading)
		private set

	fun getGroupLessons() {
		viewModelScope.launch(Dispatchers.IO) {
			lessonsResource = getGroupLessonsUseCase()
		}
	}
}
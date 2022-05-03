package ru.neexol.rtut.presentation.lessons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.neexol.rtut.domain.models.Lesson
import ru.neexol.rtut.domain.usecases.GetGroupLessons
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
	private val savedStateHandle: SavedStateHandle,
	private val getGroupLessonsUseCase: GetGroupLessons
) : ViewModel() {
	var lessons by mutableStateOf<List<Lesson>>(emptyList())
		private set

	fun getGroupLessons() {
		viewModelScope.launch {
			lessons = getGroupLessonsUseCase()
		}
	}
}
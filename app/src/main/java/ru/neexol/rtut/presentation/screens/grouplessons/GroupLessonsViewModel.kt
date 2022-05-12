package ru.neexol.rtut.presentation.screens.grouplessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ru.neexol.rtut.domain.lessons.usecases.GetGroupLessonsUseCase
import ru.neexol.rtut.domain.lessons.usecases.GetTimesUseCase
import javax.inject.Inject

@HiltViewModel
class GroupLessonsViewModel @Inject constructor(
	getGroupLessonsUseCase: GetGroupLessonsUseCase,
	getTimesUseCase: GetTimesUseCase
) : ViewModel() {
	val uiStateFlow = combine(
		getGroupLessonsUseCase.resultFlow,
		getTimesUseCase.resultFlow
	) { lessonsResource, times ->
		lessonsResource.to(
			onSuccess = { GroupLessonsUiState(times = times, lessons = it) },
			onFailure = { GroupLessonsUiState(times = times, message = "Не удалось синхронизировать расписание") },
			onLoading = { GroupLessonsUiState(times = times, isLessonsLoading = true) }
		)
	}.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		GroupLessonsUiState()
	)
}
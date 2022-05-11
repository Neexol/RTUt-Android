package ru.neexol.rtut.presentation.screens.grouplessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.data.lessons.models.DEFAULT_TIMES
import ru.neexol.rtut.domain.lessons.usecases.GetGroupLessonsUseCase
import ru.neexol.rtut.domain.lessons.usecases.GetTimesUseCase
import javax.inject.Inject

@HiltViewModel
class GroupLessonsViewModel @Inject constructor(
	getGroupLessonsUseCase: GetGroupLessonsUseCase,
	getTimesUseCase: GetTimesUseCase
) : ViewModel() {
	val lessonsResourceFlow = getGroupLessonsUseCase.resultFlow.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		Resource.Loading
	)

	val timesFlow = getTimesUseCase.resultFlow.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		DEFAULT_TIMES
	)
}
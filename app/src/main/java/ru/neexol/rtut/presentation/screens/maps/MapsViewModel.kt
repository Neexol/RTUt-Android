package ru.neexol.rtut.presentation.screens.maps

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.neexol.rtut.domain.maps.usecases.FindClassroomUseCase
import ru.neexol.rtut.domain.maps.usecases.GetMapsUseCase
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
	getMapsUseCase: GetMapsUseCase,
	private val findClassroomUseCase: FindClassroomUseCase
) : ViewModel() {
	val uiStateFlow = combine(
		getMapsUseCase.resultFlow,
		findClassroomUseCase.resultFlow
	) { getMapsResource, findClassroomResource ->
		var state = getMapsResource.to(
			onSuccess = { MapsUiState(maps = it) },
			onFailure = { MapsUiState(message = "Не удалось загрузить карты") },
			onLoading = { MapsUiState(isMapsLoading = true) }
		)
		findClassroomResource?.to(
			onSuccess = { state = state.copy(floor = it.floor, classroom = it.classroom, maps = it.maps) },
			onFailure = { state = state.copy(message = "Не удалось найти аудиторию") },
			onLoading = { state = state.copy(isMapsLoading = true) }
		)
		state
	}.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		MapsUiState()
	)

	var classroomState by mutableStateOf("")

	fun findClassroom() {
		MutableStateFlow(false).update {
			!it
		}
		findClassroomUseCase.launch {
			this.classroom = classroomState
		}
	}
}
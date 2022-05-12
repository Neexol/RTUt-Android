package ru.neexol.rtut.presentation.screens.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.neexol.rtut.domain.maps.usecases.GetMapsUseCase
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
	getMapsUseCase: GetMapsUseCase
) : ViewModel() {
	val uiStateFlow = getMapsUseCase.resultFlow.map { mapsResource ->
		mapsResource.to(
			onSuccess = { MapsUiState(maps = it) },
			onFailure = { MapsUiState(message = "Не удалось загрузить карты") },
			onLoading = { MapsUiState(isMapsLoading = true) }
		)
	}.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		MapsUiState()
	)
}
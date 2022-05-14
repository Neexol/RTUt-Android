package ru.neexol.rtut.presentation.screens.maps

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.neexol.rtut.domain.maps.usecases.GetMapsUseCase
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
	private val getMapsUseCase: GetMapsUseCase
) : ViewModel() {
	var uiState by mutableStateOf(MapsUiState())
		private set

	var classroom by mutableStateOf("")
	private var fetchJob: Job? = null
	init { fetchMaps() }
	fun fetchMaps() {
		fetchJob?.cancel()
		fetchJob = viewModelScope.launch {
			getMapsUseCase(classroom).collect { maps ->
				uiState = maps.to(
					onSuccess = { MapsUiState(maps = it.maps, floor = it.floor, classroom = it.classroom) },
					onFailure = { uiState.copy(isMapsLoading = false, message = it.toString()) },
					onLoading = { uiState.copy(isMapsLoading = true) }
				)
			}
		}
	}
}
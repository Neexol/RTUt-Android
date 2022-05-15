package ru.neexol.rtut.presentation.screens.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.neexol.rtut.data.maps.MapsRepository
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
	private val repo: MapsRepository
) : ViewModel() {
	var uiState by mutableStateOf(MapUiState())
		private set

	var classroom by mutableStateOf("")
	private var fetchJob: Job? = null
	init { fetchMaps() }
	fun fetchMaps() {
		fetchJob?.cancel()
		fetchJob = viewModelScope.launch {
			repo.getMaps(classroom).collect { maps ->
				uiState = maps.to(
					onSuccess = { MapUiState(maps = it.maps, floor = it.floor, classroom = it.classroom) },
					onFailure = { uiState.copy(isMapsLoading = false, message = it.toString()) },
					onLoading = { uiState.copy(isMapsLoading = true) }
				)
			}
		}
	}
}
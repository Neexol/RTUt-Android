package ru.neexol.rtut.presentation.screens.map

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.neexol.rtut.R
import ru.neexol.rtut.data.maps.MapsRepository
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
	private val app: Application,
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
					onFailure = {
						val msg = app.getString(
							if (it.message == "Not found") {
								R.string.classroom_not_found
							} else {
								R.string.connection_error
							}
						)
						uiState.copy(isMapsLoading = false, message = msg)
					},
					onLoading = { uiState.copy(isMapsLoading = true) }
				)
			}
		}
	}

	fun clearMessage() {
		uiState = uiState.copy(message = null)
	}
}
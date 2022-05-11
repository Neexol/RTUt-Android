package ru.neexol.rtut.presentation.screens.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.maps.usecases.GetMapsUseCase
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
	getMapsUseCase: GetMapsUseCase
) : ViewModel() {
	val mapsResourceFlow = getMapsUseCase.resultFlow.stateIn(
		viewModelScope,
		SharingStarted.Eagerly,
		Resource.Loading
	)
}
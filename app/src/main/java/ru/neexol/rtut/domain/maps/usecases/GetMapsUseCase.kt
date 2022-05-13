package ru.neexol.rtut.domain.maps.usecases

import android.graphics.Bitmap
import ru.neexol.rtut.core.FlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.maps.MapsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMapsUseCase @Inject constructor(
	private val repository: MapsRepository
) : FlowUseCase<Resource<List<Bitmap>>>() {
	override fun performAction() = repository.getMaps()
}
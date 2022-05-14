package ru.neexol.rtut.domain.maps.usecases

import ru.neexol.rtut.domain.maps.MapsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMapsUseCase @Inject constructor(
	private val repository: MapsRepository
) { operator fun invoke(classroom: String) = repository.getMaps(classroom) }
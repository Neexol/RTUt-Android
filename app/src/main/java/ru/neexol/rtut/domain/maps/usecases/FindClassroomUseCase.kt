package ru.neexol.rtut.domain.maps.usecases

import ru.neexol.rtut.core.DataFlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.maps.MapsRepository
import javax.inject.Inject

class FindClassroomUseCase @Inject constructor(
	private val repository: MapsRepository
) : DataFlowUseCase<Resource<MapsRepository.FindMapResult>, FindClassroomUseCase.FindClassroomParams>() {
	class FindClassroomParams {
		var classroom = ""
	}

	override fun performAction(init: FindClassroomParams.() -> Unit) = FindClassroomParams()
		.apply(init)
		.run {
			repository.findClassroom(classroom)
		}
}
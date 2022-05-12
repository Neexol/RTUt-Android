package ru.neexol.rtut.domain.lessons.usecases

import kotlinx.coroutines.flow.onCompletion
import ru.neexol.rtut.core.DataFlowUseCase
import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.lessons.LessonsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditGroupUseCase @Inject constructor(
	private val repository: LessonsRepository,
	private val getGroupUseCase: GetGroupUseCase,
	private val getGroupLessonsUseCase: GetGroupLessonsUseCase
): DataFlowUseCase<Resource<String>, EditGroupUseCase.EditGroupParams>() {
	class EditGroupParams {
		var group = ""
	}

	override fun performAction(init: EditGroupParams.() -> Unit) = EditGroupParams()
		.apply(init)
		.run {
			repository.editGroup(group).onCompletion { cause ->
				cause ?: run {
					getGroupUseCase.launch()
					getGroupLessonsUseCase.launch()
				}
			}
		}
}
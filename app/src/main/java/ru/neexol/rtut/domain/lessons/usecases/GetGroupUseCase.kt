package ru.neexol.rtut.domain.lessons.usecases

import kotlinx.coroutines.flow.flow
import ru.neexol.rtut.core.FlowUseCase
import ru.neexol.rtut.data.lessons.local.LessonsLocalDataSource
import javax.inject.Inject

class GetGroupUseCase @Inject constructor(
	private val dataSource: LessonsLocalDataSource
) : FlowUseCase<String>(true) {
	override fun performAction() = flow { emit(dataSource.getGroup()) }
}
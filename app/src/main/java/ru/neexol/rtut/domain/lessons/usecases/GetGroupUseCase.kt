package ru.neexol.rtut.domain.lessons.usecases

import kotlinx.coroutines.flow.flow
import ru.neexol.rtut.core.FlowUseCase
import ru.neexol.rtut.data.lessons.local.LessonsLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGroupUseCase @Inject constructor(
	private val dataSource: LessonsLocalDataSource
) : FlowUseCase<String>() {
	override fun performAction() = flow { emit(dataSource.getGroup()) }
}
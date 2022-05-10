package ru.neexol.rtut.domain.lessons.usecases

import kotlinx.coroutines.flow.flow
import ru.neexol.rtut.core.FlowUseCase
import ru.neexol.rtut.data.lessons.local.LessonsLocalDataSource
import ru.neexol.rtut.data.lessons.models.LessonTime
import javax.inject.Inject

class GetTimesUseCase @Inject constructor(
	private val dataSource: LessonsLocalDataSource
) : FlowUseCase<List<LessonTime>>(true) {
	override fun performAction() = flow { emit(dataSource.getTimes()) }
}
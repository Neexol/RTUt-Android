package ru.neexol.rtut.domain.lessons.usecases

import kotlinx.coroutines.flow.flow
import ru.neexol.rtut.core.FlowUseCase
import ru.neexol.rtut.data.lessons.local.LessonsLocalDataSource
import ru.neexol.rtut.data.lessons.models.LessonTime
import javax.inject.Inject

class GetTimesUseCase @Inject constructor(
	private val localDataSource: LessonsLocalDataSource
) : FlowUseCase<List<LessonTime>>() {
	init {
		launch()
	}
	override fun performAction() = flow { emit(localDataSource.getTimes()) }
}
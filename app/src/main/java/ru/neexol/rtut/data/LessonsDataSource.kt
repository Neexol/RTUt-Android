package ru.neexol.rtut.data

import ru.neexol.rtut.core.Resource
import ru.neexol.rtut.domain.models.GroupLessons

interface LessonsDataSource {
	suspend fun getGroupLessons(group: String): Resource<GroupLessons>
}
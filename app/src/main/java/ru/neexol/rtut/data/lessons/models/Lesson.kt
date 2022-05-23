package ru.neexol.rtut.data.lessons.models

@kotlinx.serialization.Serializable
data class Lesson(
	val id: String,
	val name: String,
	val type: String,
	val teacher: String,
	val classroom: String,
	val day: Int,
	val number: Int,
	val weeks: List<Int>
) {
	private fun formatName() = name.trim().lowercase().let {
		if (it == "военная" || it == "подготовка") "Военная подготовка" else name
	}
	val lessonWithType = formatName() + (if (type.isNotBlank()) ", ${type.uppercase()}" else "")
}
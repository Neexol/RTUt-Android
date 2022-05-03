package ru.neexol.rtut.domain.models

data class Lesson(
	val id: String,
	val name: String,
	val type: String,
	val teacher: String,
	val classroom: String,
	val day: Int,
	val number: Int,
	val weeks: List<Int>
)
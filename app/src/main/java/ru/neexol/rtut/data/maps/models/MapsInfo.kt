package ru.neexol.rtut.data.maps.models

@kotlinx.serialization.Serializable
data class MapsInfo(
	val date: Int,
	val maps: List<Map>
)
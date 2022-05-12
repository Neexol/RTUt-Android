package ru.neexol.rtut.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

abstract class FlowUseCase<T> {
	private val trigger = MutableStateFlow(true)
	fun launch() { trigger.tryEmit(!trigger.value) }

	val resultFlow = trigger.flatMapLatest { performAction() }
	protected abstract fun performAction() : Flow<T>
}
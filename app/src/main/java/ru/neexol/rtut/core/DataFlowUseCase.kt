package ru.neexol.rtut.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

abstract class DataFlowUseCase<T, P> {
	private val trigger = MutableStateFlow<(P.() -> Unit)?>(null)
	fun launch(init: P.() -> Unit) { trigger.tryEmit(init) }

	val resultFlow: Flow<T?> = trigger.flatMapLatest {
		it?.let { performAction(it) } ?: flowOf(null)
	}
	protected abstract fun performAction(init: P.() -> Unit) : Flow<T>
}
package ru.neexol.rtut.core

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow

abstract class DataFlowUseCase<T, P> {
	private val trigger = Channel<P.() -> Unit>(Channel.CONFLATED)
	fun launch(init: P.() -> Unit) { trigger.trySend(init) }

	val resultFlow: Flow<T> = trigger.receiveAsFlow().flatMapLatest { performAction(it) }
	protected abstract fun performAction(init: P.() -> Unit) : Flow<T>
}
package ru.neexol.rtut.core

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow

abstract class FlowUseCase<T>(launchOnInit: Boolean = false) {
	private val trigger = Channel<Unit>(Channel.CONFLATED)
	fun launch() { trigger.trySend(Unit) }

	val resultFlow: Flow<T> = trigger.receiveAsFlow().flatMapLatest { performAction() }
	protected abstract fun performAction() : Flow<T>

	init { if (launchOnInit) launch() }
}
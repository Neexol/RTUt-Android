package ru.neexol.rtut.core

sealed class Resource<out T> {
	data class Success<T>(val data: T) : Resource<T>()
	data class Failure(val cause: Throwable) : Resource<Nothing>()
	object Loading : Resource<Nothing>()

	inline fun <R> map(transform: (T) -> R) = when (this) {
		is Success -> Success(transform(data))
		is Failure -> Failure(cause)
		Loading    -> Loading
	}

	inline fun <reified R> to(
		onSuccess: (T) -> R,
		onFailure: (Throwable) -> R,
		onLoading: () -> R,
	) = when(this) {
		is Success -> onSuccess(data)
		is Failure -> onFailure(cause)
		Loading    -> onLoading()
	}
}
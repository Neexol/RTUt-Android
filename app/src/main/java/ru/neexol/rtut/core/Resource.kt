package ru.neexol.rtut.core

sealed class Resource<out T> {
	data class Success<T>(val data: T) : Resource<T>()
	data class Error(val cause: Throwable) : Resource<Nothing>()
	object Loading : Resource<Nothing>()

	inline operator fun invoke(
		onLoading: () -> Unit = {},
		onError: (Throwable) -> Unit = {},
		onSuccess: (T) -> Unit = {},
	) = when(this) {
		is Success -> onSuccess(data)
		is Error -> onError(cause)
		Loading -> onLoading()
	}
}
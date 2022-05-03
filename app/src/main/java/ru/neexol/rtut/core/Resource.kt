package ru.neexol.rtut.core

sealed class Resource<out T> {
	data class Success<T>(val data: T) : Resource<T>()
	data class Error(val exception: Throwable) : Resource<Nothing>()
	object Loading : Resource<Nothing>()

	companion object {
		suspend fun <T> from(block: suspend () -> T) = try {
			Success(block())
		} catch (t: Throwable) {
			Error(t)
		}
	}

	inline operator fun invoke(
		onLoading: () -> Unit = {},
		onError: (Throwable) -> Unit = {},
		onSuccess: (T) -> Unit = {},
	) = when(this) {
		is Success -> onSuccess(data)
		is Error -> onError(exception)
		Loading -> onLoading()
	}
}
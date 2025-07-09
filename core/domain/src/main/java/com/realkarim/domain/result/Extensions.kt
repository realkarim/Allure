package com.realkarim.domain.result

import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

suspend fun <T> OutCome<T>.doOnSuccess(onSuccess: suspend (T) -> Unit): OutCome<T> {
  // Handle the case when view is destroyed but viewmodel still active
  if (this is OutCome.Success<T> && coroutineContext.isActive) {
    onSuccess(this.data)
  }
  return this
}

suspend fun <T> OutCome<T>.doOnEmpty(onEmpty: suspend () -> Unit): OutCome<T> {
  if (this is OutCome.Empty && coroutineContext.isActive) {
    onEmpty()
  }
  return this
}

suspend fun <T> OutCome<T>.doOnError(onError: () -> Unit): OutCome<T> {
  if (!this.isSuccess() && coroutineContext.isActive) {
    onError()
  }
  return this
}

suspend fun <T, R> OutCome<T>.map(mapper: suspend (T) -> R): OutCome<R> {
  return when (this) {
    is OutCome.Success<T> -> {
      OutCome.success(mapper(this.data))
    }

    is OutCome.Error<T> -> {
      OutCome.error(this.errorMessage())
    }

    is OutCome.Empty<T> -> {
      OutCome.empty()
    }
  }
}

// Merges the current OutCome with another OutCome produced by a lazy function, using a merger function.
suspend fun <F, S, R> OutCome<F>.merge(
  lazy: suspend () -> OutCome<S>,
  merger: (F?, S?) -> R,
): OutCome<R> {
  return when (this) {
    is OutCome.Success<F> -> {
      when (val second = lazy()) {
        is OutCome.Success<S> -> {
          OutCome.success(merger(this.data, second.data))
        }

        is OutCome.Empty<S> -> {
          OutCome.success(merger(this.data, null))
        }

        is OutCome.Error<S> -> {
          OutCome.error(second.errorMessage())
        }
      }
    }

    is OutCome.Error<F> -> {
      OutCome.error(this.errorMessage())
    }

    is OutCome.Empty<F> -> {
      when (val second = lazy()) {
        is OutCome.Success<S> -> {
          OutCome.success(merger(null, second.data))
        }

        is OutCome.Empty<S> -> {
          OutCome.empty()
        }

        is OutCome.Error<S> -> {
          OutCome.error(second.errorMessage())
        }
      }
    }
  }
}

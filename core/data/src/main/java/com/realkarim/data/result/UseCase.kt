package com.realkarim.data.result

import com.realkarim.data.model.ErrorMessage

interface UseCase<R> {

  suspend fun onSuccess(success: OutCome.Success<R>)

  suspend fun onEmpty()

  suspend fun onError(errorMessage: ErrorMessage)
}

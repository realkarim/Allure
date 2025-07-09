package com.realkarim.domain.usecase

import com.realkarim.domain.model.ErrorMessage
import com.realkarim.domain.result.OutCome

interface UseCase<R> {

  suspend fun onSuccess(success: OutCome.Success<R>)

  suspend fun onEmpty()

  suspend fun onError(errorMessage: ErrorMessage)
}

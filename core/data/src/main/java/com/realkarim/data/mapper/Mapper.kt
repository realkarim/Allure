package com.realkarim.data.mapper

import com.realkarim.data.response.ErrorResponse
import com.realkarim.domain.model.ErrorMessage

// mapping errorResponse to ErrorMessage model
fun ErrorResponse.toDomain(code: Int): ErrorMessage {
  return ErrorMessage(
    code = code,
    message = errorMessage.orEmpty(),
    errorFieldList = errorFieldList ?: emptyList(),
  )
}

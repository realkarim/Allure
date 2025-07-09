package com.realkarim.data.error

import com.google.gson.Gson
import com.realkarim.data.response.ErrorResponse

// Return a default error response in case we didn't get a valid response
fun getDefaultErrorResponse() = ErrorResponse("", "", emptyList())

// Return an ErrorResponse from the error body
fun getErrorResponse(gson: Gson, errorBodyString: String): ErrorResponse =
  try {
    gson.fromJson(errorBodyString, ErrorResponse::class.java)
  } catch (_: Exception) {
    getDefaultErrorResponse()
  }

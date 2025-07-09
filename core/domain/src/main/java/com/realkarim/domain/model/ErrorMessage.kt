package com.realkarim.domain.model

// Used to map error messages from the API to a domain format
data class ErrorMessage(
  val code: Int,
  val message: String,
  val errorFieldList: List<String>,
)

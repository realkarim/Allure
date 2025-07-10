package com.realkarim.login.data.service

import com.realkarim.login.data.request.LoginRequestBody
import com.realkarim.login.data.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "https://allure.com"
const val EMAIL = "email"

// todo: Consider removing the deferred return type
interface LoginService {
  @POST("/Auth/Login")
  fun login(
    @Body loginRequestBody: LoginRequestBody,
  ): Deferred<Response<LoginResponse>>

  @POST("/Auth/ForgetPassword")
  fun forgetPassword(
    @Query(EMAIL) email: String,
  ): Deferred<Response<Unit>>
}

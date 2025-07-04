package com.realkarim.login.data.service

import com.realkarim.login.data.request.LoginRequestBody
import com.realkarim.login.data.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

// todo: Replace with BuildConfig
const val BASE_URL = "https://allure.com"
const val EMAIL = "email"

// todo: Consider removing the deferred return type
interface LoginService {
    @POST("$BASE_URL/Auth/Login")
    fun login(
        @Body loginRequestBody: LoginRequestBody,
    ): Deferred<Response<LoginResponse>>

    @POST("$BASE_URL/Auth/ForgetPassword")
    fun forgetPassword(
        @Query(EMAIL) email: String,
    ): Deferred<Response<Unit>>
}

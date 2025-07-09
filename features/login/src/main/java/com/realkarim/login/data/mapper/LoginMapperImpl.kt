package com.realkarim.login.data.mapper

import com.realkarim.login.data.response.LoginResponse
import com.realkarim.login.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoginMapperImpl(private val defaultDispatcher: CoroutineDispatcher) : LoginMapper {
  override suspend fun toDomain(userResponse: LoginResponse): User {
    // Applying the defaultDispatcher is optional here, in case the mapping is a heavy operation
    // It's not in this case
    return withContext(defaultDispatcher) {
      User(
        id = userResponse.id.orEmpty(),
        fullName = userResponse.fullName.orEmpty(),
        email = userResponse.email.orEmpty(),
        photo = userResponse.photo.orEmpty(),
      )
    }
  }
}

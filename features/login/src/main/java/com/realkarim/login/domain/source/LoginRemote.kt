package com.realkarim.login.domain.source

import com.realkarim.domain.result.OutCome
import com.realkarim.login.domain.model.User

interface LoginRemote {
    suspend fun login(username: String, password: String): OutCome<User>
}
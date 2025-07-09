package com.realkarim.protodatastore.manager.preference

import kotlinx.coroutines.flow.Flow

interface PreferencesDataStore {
  // Setters

  suspend fun setLanguage(language: String)
  suspend fun setIsAppLockEnabled(isAppLockEnabled: Boolean)
  suspend fun setNotificationCount(notificationCount: Int)
  suspend fun setMoneyBalance(moneyBalance: Long)

  // Getters

  suspend fun getLanguage(): String
  fun getLanguageFlow(): Flow<String>

  suspend fun isAppLockEnabled(): Boolean
  fun isAppLockEnabledFlow(): Flow<Boolean>

  suspend fun getNotificationCount(): Int
  fun getNotificationCountFlow(): Flow<Int>

  suspend fun getMoneyBalance(): Long
  fun getMoneyBalanceFlow(): Flow<Long>
}

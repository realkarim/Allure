package com.realkarim.navigator.viewmodel

import androidx.lifecycle.ViewModel
import com.realkarim.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

// Better testability, decouple navigation from UI, lifecycle aware
@HiltViewModel
class AppNavigatorViewModel @Inject constructor(
  private val appNavigator: AppNavigator,
) : ViewModel(), AppNavigator by appNavigator

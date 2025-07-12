package com.realkarim.navigator.di

import com.realkarim.navigator.AppNavigator
import com.realkarim.navigator.AppNavigatorImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {
    abstract fun navigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator
}
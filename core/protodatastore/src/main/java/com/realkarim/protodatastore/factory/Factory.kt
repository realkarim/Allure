package com.realkarim.protodatastore.factory

import PreferencesSerializer
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.realkarim.proto.Preferences
import com.realkarim.proto.Session
import com.realkarim.protodatastore.SessionSerializer

val Context.sessionDataStore: DataStore<Session> by dataStore(
  fileName = "session.pb",
  serializer = SessionSerializer,
)

val Context.preferencesDataStore: DataStore<Preferences> by dataStore(
  fileName = "preferences.pb",
  serializer = PreferencesSerializer,
)

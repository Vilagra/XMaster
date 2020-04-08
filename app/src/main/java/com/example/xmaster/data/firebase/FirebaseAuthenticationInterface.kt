package com.example.xmaster.data.firebase

import kotlinx.coroutines.flow.Flow

interface FirebaseAuthenticationInterface {

  fun login(email: String, password: String) : Flow<Boolean>

  fun register(email: String, password: String, userName: String) : Flow<Boolean>

  fun getUserId(): String

  fun getUserName(): String

  fun logOut()
}
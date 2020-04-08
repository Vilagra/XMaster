package com.example.xmaster.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseAuthenticationManager @Inject constructor(
    private val authentication: FirebaseAuth
) : FirebaseAuthenticationInterface {

    override fun login(email: String, password: String): Flow<Boolean>  = callbackFlow {
        authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            offer(it.isComplete && it.isSuccessful)
        }
    }

    override fun register(
        email: String,
        password: String,
        userName: String
    ): Flow<Boolean>  = callbackFlow {
        authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isComplete && it.isSuccessful) {
                authentication.currentUser?.updateProfile(
                    UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(userName)
                    .build())
                offer(true)
            } else {
                offer(false)
            }
        }
    }

    override fun getUserId(): String = authentication.currentUser?.uid ?: ""
    override fun getUserName(): String = authentication.currentUser?.displayName ?: ""

    override fun logOut() {
        authentication.signOut()
    }

}

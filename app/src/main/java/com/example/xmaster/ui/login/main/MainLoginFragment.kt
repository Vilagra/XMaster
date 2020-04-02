package com.example.xmaster.ui.login.main

import android.content.Intent
import android.util.Log
import com.example.xmaster.R
import com.example.xmaster.databinding.LoginFragmentBinding
import com.example.xmaster.ui.BaseFragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


private const val RC_SIGN_IN = 1

class MainLoginFragment : BaseFragment<LoginFragmentBinding>() {

    val callbackManager = CallbackManager.Factory.create()
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override val contentLayoutId: Int
        get() = R.layout.login_fragment

    override fun setupBinding() {
        configureFacebookLoginButton()
        configureGoogleLogin()
    }

    private fun configureFacebookLoginButton(){
        binding.facebookButton.run {
            setPermissions("email")
            setFragment(this@MainLoginFragment)
            registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {}
                override fun onCancel() {}
                override fun onError(exception: FacebookException) {}
            })
        }
    }

    private fun configureGoogleLogin(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        binding.googleButton.run {
            setOnClickListener { view ->
                signIn()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        //TODO if account isn't null that means user is already logged in, write
        // logic which directs user to next screen
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignWithGoogleInResult(task)
            }
            else -> callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignWithGoogleInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            Log.d("handle_sign_in", account?.email ?: "null")
        } catch (e: ApiException) {
            Log.d("handle_sign_in", "signInResult:failed code=" + e.statusCode)
        }
    }
}
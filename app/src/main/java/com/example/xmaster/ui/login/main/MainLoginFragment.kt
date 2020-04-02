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
import com.facebook.login.widget.LoginButton


class MainLoginFragment : BaseFragment<LoginFragmentBinding>() {

    val callbackManager = CallbackManager.Factory.create()

    override val contentLayoutId: Int
        get() = R.layout.login_fragment

    override fun setupBinding() {
        val loginButton = binding.facebookButton
        loginButton.setPermissions("email")
        loginButton.setFragment(this)

        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) { // App code
                Log.d("facebook_login", loginResult?.accessToken?.token ?: "")

            }

            override fun onCancel() { // App code
                Log.d("facebook_login", "cancel")
            }

            override fun onError(exception: FacebookException) {
                Log.d("facebook_login", exception.toString())// App code
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
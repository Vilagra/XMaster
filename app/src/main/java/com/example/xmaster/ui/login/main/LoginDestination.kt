package com.example.xmaster.ui.login.main

import com.example.xmaster.ui.vm.Destination

sealed class LoginDestination : Destination
object MainScreen : LoginDestination()
object SignUp : LoginDestination()
object SignIn : LoginDestination()
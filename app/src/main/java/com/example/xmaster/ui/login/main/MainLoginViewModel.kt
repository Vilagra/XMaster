package com.example.xmaster.ui.login.main

import androidx.lifecycle.ViewModel
import com.example.xmaster.ui.vm.Navigationable
import com.example.xmaster.ui.vm.NavigationableImpl
import javax.inject.Inject

class MainLoginViewModel @Inject constructor(
    navigationableImpl: NavigationableImpl<LoginDestination>
) : ViewModel(), Navigationable<LoginDestination> by navigationableImpl {


}
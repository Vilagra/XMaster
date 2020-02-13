package com.example.xmaster

import android.os.Bundle
import dagger.android.DaggerActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : DaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //bottom_navigation.setupWithNavController(navHostFragment.findNavController())
    }

    /*override fun onSupportNavigateUp() =
        findNavController(this, R.id.navHostFragment).navigateUp()*/

}



fun main() = runBlocking<Unit> {
    var res = 1
    val jobs = List(100_000){  GlobalScope.launch {
        Thread.sleep(1000)
        println(res++)
    }}
    jobs.forEach{it.join()}

}
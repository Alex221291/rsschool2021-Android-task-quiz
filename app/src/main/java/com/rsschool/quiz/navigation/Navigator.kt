package com.rsschool.quiz.navigation

interface Navigator {
    fun onNext(numberAnswer : Int, textAnswer : String)

    fun onBack()

    fun onRefresh()

    fun onExit()

    fun onBackPressed()
}
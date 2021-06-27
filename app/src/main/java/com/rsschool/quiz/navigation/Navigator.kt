package com.rsschool.quiz.navigation

interface Navigator {
    fun onNext(numberAnswer : Int, answers : String)

    fun onBack()

    fun onRefresh()

    fun onExit()

    fun onBackPressed()
}
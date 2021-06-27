package com.rsschool.quiz.quiestion

data class Question(val questionText:String,
                    val optionOne:String,
                    val optionTwo:String,
                    val optionThree:String,
                    val optionFour:String,
                    val optionFive:String,
                    val correctAnswer:Int)

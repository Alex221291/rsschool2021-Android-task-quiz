package com.rsschool.quiz.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rsschool.quiz.*
import com.rsschool.quiz.quiestion.QuestionList

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    private var result = ""
    private var message = ""

    override fun getItemCount(): Int {
        return QuestionList.questions.size + 1
    }

    override fun createFragment(position: Int): Fragment {
        val fragmentQuiz = QuizFragment()
        val fragmentResultMenu = ResultMenuFragment()
        if(position <= itemCount-2) {
            fragmentQuiz.arguments = Bundle().apply {
                putInt(ARG_OBJECT, position + 1)
                putString(ARG_QUESTION_TEXT, QuestionList.questions[position].questionText)
                putString(ARG_OPTION_ONE, QuestionList.questions[position].optionOne)
                putString(ARG_OPTION_TWO, QuestionList.questions[position].optionTwo)
                putString(ARG_OPTION_THREE, QuestionList.questions[position].optionThree)
                putString(ARG_OPTION_FOUR, QuestionList.questions[position].optionFour)
                putString(ARG_OPTION_FIVE, QuestionList.questions[position].optionFive)
            }
        }
        else {
            fragmentResultMenu.arguments = Bundle().apply {
                putString(ARG_RESULT, result)
                putString(ARG_MESSAGE, message)
            }
        }

        return if(position != itemCount-1) fragmentQuiz else fragmentResultMenu

    }

    fun setData(res:String, msg:String) {
        result = res
        message = msg
    }
}
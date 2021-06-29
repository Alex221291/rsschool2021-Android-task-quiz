package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.navigation.Navigator
import com.rsschool.quiz.navigation.ViewPagerAdapter
import com.rsschool.quiz.quiestion.QuestionList
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    private lateinit var binding: ActivityMainBinding

    private var numberAnswers = arrayOf(-1, -1, -1, -1, -1)
    private var textAnswers = arrayOf("", "", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ViewPagerAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
    }

    override fun onNext(numberAnswer : Int, textAnswer : String) {

        numberAnswers[viewPager.currentItem] = numberAnswer
        textAnswers[viewPager.currentItem] = textAnswer

        viewPager.currentItem += 1

        getStatusBar(viewPager.currentItem)

        if (viewPager.currentItem == numberAnswers.size) submit()
    }

    override fun onBack() {
        viewPager.currentItem -= 1
        getStatusBar(viewPager.currentItem)
    }

    override fun onRefresh() {
        viewPager.currentItem = 0
        finish()
        this.startActivity(Intent(this, this.javaClass))
    }

    override fun onExit() {
        dialogTheme()
        val exitDialog = AlertDialog.Builder(this)
            .setMessage("Out from quiz?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                finish()
                exitProcess(0)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
            }
        exitDialog.show()
    }

    private fun getStatusBar(number : Int) {

        when (number) {
            0,5 -> window.statusBarColor = ContextCompat.getColor(this, R.color.deep_orange_100_dark)
            1 -> window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_100_dark)
            2 -> window.statusBarColor = ContextCompat.getColor(this, R.color.light_green_100_dark)
            3 -> window.statusBarColor = ContextCompat.getColor(this, R.color.cyan_100_dark)
            4 -> window.statusBarColor = ContextCompat.getColor(this, R.color.deep_purple_100_dark)
        }
    }

    private fun submit(){
        var numberCorrectAnswers = 0
        var msg = ""
        for (i in numberAnswers.indices) {
            if (QuestionList.questions[i].correctAnswer == numberAnswers[i]) numberCorrectAnswers++
            msg += "${i + 1}. ${QuestionList.questions[i].questionText}\nВаш ответ: ${textAnswers[i]}\n\n"
        }
        val res = "Ваш результат: ${numberCorrectAnswers * 100 / numberAnswers.size}%"
        msg = "$res\n\n$msg"
        adapter.setData(res, msg)
        viewPager.adapter = adapter
    }

    override fun onBackPressed() {
        dialogTheme()
        val exitDialog = AlertDialog.Builder(this)
            .setMessage("Do your want out from quiz?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                finish()
                exitProcess(0)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
            }
        when (viewPager.currentItem) {
            0, QuestionList.questions.size -> exitDialog.show()
            else -> onBack()
        }
    }
    private fun dialogTheme() {
        when (viewPager.currentItem) {
            0,5 -> setTheme(R.style.Theme_Quiz_First)
        }
    }
}
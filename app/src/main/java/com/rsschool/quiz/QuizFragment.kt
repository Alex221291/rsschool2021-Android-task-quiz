package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuiz2Binding
import com.rsschool.quiz.navigation.Navigator


const val ARG_OBJECT = "object"
const val ARG_QUESTION_TEXT = "questionText"
const val ARG_OPTION_ONE = "optionOne"
const val ARG_OPTION_TWO = "optionTwo"
const val ARG_OPTION_THREE = "optionThree"
const val ARG_OPTION_FOUR = "optionFour"
const val ARG_OPTION_FIVE = "optionFive"

class QuizFragment : Fragment() {

    private var _binding: FragmentQuiz2Binding? = null
    private val binding get() = _binding!!

    private var navigator: Navigator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            newTheme(getInt(ARG_OBJECT))
        }
        _binding = FragmentQuiz2Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {

            binding.question.text = getString(ARG_QUESTION_TEXT)
            binding.optionOne.text = getString(ARG_OPTION_ONE)
            binding.optionTwo.text = getString(ARG_OPTION_TWO)
            binding.optionThree.text = getString(ARG_OPTION_THREE)
            binding.optionFour.text = getString(ARG_OPTION_FOUR)
            binding.optionFive.text = getString(ARG_OPTION_FIVE)

            binding.toolbar.title = "Question ${getInt(ARG_OBJECT)}"

            if (getInt(ARG_OBJECT) == 5) binding.nextButton.text = getString(R.string.submit)

            if(getInt(ARG_OBJECT) == 1) {
                binding.previousButton.isEnabled = false
                binding.toolbar.navigationIcon = null
            }

            binding.nextButton.setOnClickListener {
                val rbID = binding.radioGroup.checkedRadioButtonId
                val rbInd = binding.radioGroup.indexOfChild(binding.radioGroup.findViewById(rbID))
                val rb = binding.radioGroup.getChildAt(rbInd) as RadioButton
                val answerText = rb.text.toString()
                navigator?.onNext(rbInd, answerText) }
            
            binding.previousButton.setOnClickListener { navigator?.onBack() }

            binding.radioGroup.setOnCheckedChangeListener { _, _ ->  binding.nextButton.isEnabled = true}

            binding.toolbar.setNavigationOnClickListener { navigator?.onBack()}
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = if (context is Navigator) {
            context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnSecondFragmentDataListener"
            )
        }
    }

    private fun newTheme(point : Int) {
        when (point) {
            1,6 -> context?.setTheme(R.style.Theme_Quiz_First)
            2 -> context?.setTheme(R.style.Theme_Quiz_Second)
            3 -> context?.setTheme(R.style.Theme_Quiz_Third)
            4 -> context?.setTheme(R.style.Theme_Quiz_Fourth)
            5 -> context?.setTheme(R.style.Theme_Quiz_Fifth)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
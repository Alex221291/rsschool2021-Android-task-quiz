package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultMenuBinding
import com.rsschool.quiz.navigation.Navigator


const val ARG_RESULT = "result"
const val ARG_MESSAGE = "message"

class ResultMenuFragment : Fragment() {

    private var _binding: FragmentResultMenuBinding? = null
    private val binding get() = _binding!!

    private var navigator: Navigator? = null
    private var animationSize : Animation? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultMenuBinding.inflate(inflater, container, false)
        animationSize = AnimationUtils.loadAnimation(
            context, R.anim.sizer)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_RESULT) }?.apply {
            binding.result.text = getString(ARG_RESULT)
            binding.exit.setOnClickListener {
                binding.exit.startAnimation(animationSize)
                navigator?.onExit()
            }
            binding.refresh.setOnClickListener {
                binding.refresh.startAnimation(animationSize)
                navigator?.onRefresh()
            }
            binding.share.setOnClickListener {
                binding.share.startAnimation(animationSize)
                shareResult("${getString(ARG_MESSAGE)}")
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun shareResult(result : String){
        view
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            result
        )
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
}


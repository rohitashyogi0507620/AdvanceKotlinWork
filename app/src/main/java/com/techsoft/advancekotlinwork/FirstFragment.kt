package com.techsoft.advancekotlinwork

import android.animation.*
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.techsoft.advancekotlinwork.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rotateButton.setOnClickListener {
            rotater()
        }

        binding.translateButton.setOnClickListener {
            translater()
        }

        binding.scaleButton.setOnClickListener {
            scaler()
        }

        binding.fadeButton.setOnClickListener {
            fader()
        }

        binding.colorizeButton.setOnClickListener {
            colorizer()
        }

        binding.showerButton.setOnClickListener {
            shower()
        }


    }

    private fun fader() {
        val animator = ObjectAnimator.ofFloat(binding.dialView, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        disableViewDuringAnimation(binding.fadeButton, animator)
        animator.start()
    }

    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.dialView, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        disableViewDuringAnimation(binding.scaleButton, animator)
        animator.start()
    }

    private fun translater() {
        val animator = ObjectAnimator.ofFloat(binding.dialView, View.TRANSLATION_X, 200f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        disableViewDuringAnimation(binding.translateButton, animator)
        animator.start()

    }

    private fun colorizer() {
        //Color Integer
//        var animator = ObjectAnimator.ofInt(binding.dialView.parent, "backgroundColor", Color.BLACK, Color.RED)

        var animator = ObjectAnimator.ofArgb(binding.dialView.parent, "backgroundColor", Color.YELLOW, Color.RED)
        disableViewDuringAnimation(binding.colorizeButton, animator)
        animator.setDuration(1000)
        animator.repeatCount = 2
        animator.repeatMode = ValueAnimator.REVERSE
        animator.start()
    }

    private fun shower() {

        val container = binding.star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW: Float = binding.star.width.toFloat()
        var starH: Float = binding.star.height.toFloat()

        val newStar = AppCompatImageView(requireContext())
        newStar.setImageResource(R.drawable.ic_baseline_star_24)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT)
        container.addView(newStar)

        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        newStar.translationX = Math.random().toFloat() * containerW - starW / 2

        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)
        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION, (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()

        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 5000 + 500).toLong()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })
        set.start()

    }

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(binding.dialView, View.ROTATION, -360f, 0f)
        animator.duration = 1000
        disableViewDuringAnimation(binding.rotateButton, animator)
        animator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun disableViewDuringAnimation(view: View, animator: ObjectAnimator) {
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }
}
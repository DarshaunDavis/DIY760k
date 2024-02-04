package com.lislal.diy760

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import android.widget.ImageView
import com.lislal.diy760.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the ImageView by its ID
        val arrowLeft = view.findViewById<ImageView>(R.id.arrow_left)
        val arrowRight = view.findViewById<ImageView>(R.id.arrow_right)

        // Load the animations
        val swipeLeftAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_swipe_left)
        val swipeRightAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_swipe_right)

        // Start the animations
        arrowLeft?.startAnimation(swipeLeftAnimation)
        arrowRight?.startAnimation(swipeRightAnimation)
    }
}

package com.lislal.diy760

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

class LeftFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_left, container, false)

        val button1 = view.findViewById<Button>(R.id.button1)
        val button2 = view.findViewById<Button>(R.id.button2)
        val button3 = view.findViewById<Button>(R.id.button3)
        val button4 = view.findViewById<Button>(R.id.button4)
        val customContainer = view.findViewById<FrameLayout>(R.id.customContainer)

        button1.setOnClickListener {
            button1.isEnabled = false
            button2.visibility = View.VISIBLE
            button3.visibility = View.VISIBLE
            button2.isEnabled = true
        }

        button2.setOnClickListener {
            // Load sagestream_letter_layout into the container
            customContainer.removeAllViews() // Clear any previous views
            val inflater = LayoutInflater.from(requireContext())
            inflater.inflate(R.layout.sagestream_letter_layout, customContainer, true)
        }

        button3.setOnClickListener {
            button3.isEnabled = false
            button2.visibility = View.GONE
            button3.visibility = View.GONE
            button4.visibility = View.VISIBLE
            button4.isEnabled = true
        }

        button4.setOnClickListener {
            // Load custom_layout into the container
            customContainer.removeAllViews() // Clear any previous views
            val inflater = LayoutInflater.from(requireContext())
            inflater.inflate(R.layout.custom_layout, customContainer, true)
        }

        return view
    }
}

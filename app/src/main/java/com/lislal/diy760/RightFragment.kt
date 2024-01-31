package com.lislal.diy760

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class RightFragment : Fragment() {
    // ... existing code ...

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_right, container, false)

        // Initialize buttons and text views
        val button1 = view.findViewById<Button>(R.id.button1)
        val button2 = view.findViewById<Button>(R.id.button2)
        val button3 = view.findViewById<Button>(R.id.button3)
        val button4 = view.findViewById<Button>(R.id.button4)
        val button5 = view.findViewById<Button>(R.id.button5)
        val button6 = view.findViewById<Button>(R.id.button6)
        val button7 = view.findViewById<Button>(R.id.button7)
        val button8 = view.findViewById<Button>(R.id.button8)
        val button9 = view.findViewById<Button>(R.id.button9)
        val button10 = view.findViewById<Button>(R.id.button10)
        val button11 = view.findViewById<Button>(R.id.button11)
        val button12 = view.findViewById<Button>(R.id.button12)
        val button13 = view.findViewById<Button>(R.id.button13)
        val button14 = view.findViewById<Button>(R.id.button14)
        val button15 = view.findViewById<Button>(R.id.button15)
        val button16 = view.findViewById<Button>(R.id.button16)
        val button17 = view.findViewById<Button>(R.id.button17)
        val buttonAndTextContainer = view.findViewById<LinearLayout>(R.id.buttonAndTextContainer)
        val secondButtonAndTextContainer = view.findViewById<LinearLayout>(R.id.secondButtonAndTextContainer)
        val textViewExplanation1 = view.findViewById<TextView>(R.id.textViewExplanation1)
        val textViewExplanation2 = view.findViewById<TextView>(R.id.textViewExplanation2)
        val textViewExplanation3 = view.findViewById<TextView>(R.id.textViewExplanation3)
        val textViewExplanation4 = view.findViewById<TextView>(R.id.textViewExplanation4)

        // Set click listeners
        button1.setOnClickListener {
            button1.isEnabled = false
            textViewExplanation1.visibility = View.GONE
            buttonAndTextContainer.visibility = View.VISIBLE
            enableButton(view.findViewById<Button>(R.id.button2))
        }

        button2.setOnClickListener {
            button2.isEnabled = false
            button3.isEnabled = true
        }

        button3.setOnClickListener {
            button3.isEnabled = false
            button4.isEnabled = true
        }

        button4.setOnClickListener {
            button4.isEnabled = false
            button5.isEnabled = true
        }

        button5.setOnClickListener {
            button5.isEnabled = false
            button6.isEnabled = true
        }

        button6.setOnClickListener {
            button6.isEnabled = false
            button7.isEnabled = true
        }

        button7.setOnClickListener {
            button7.isEnabled = false
            button8.isEnabled = true
        }

        button8.setOnClickListener {

            button8.isEnabled = false
            button1.setText("Step 1 Complete")
            buttonAndTextContainer.visibility = View.GONE
            button9.visibility = View.VISIBLE
            textViewExplanation3.visibility = View.VISIBLE
            enableButton(button9)
        }

        button9.setOnClickListener {
            button9.isEnabled = false
            textViewExplanation3.visibility = View.GONE
            secondButtonAndTextContainer.visibility = View.VISIBLE
            enableButton(view.findViewById<Button>(R.id.button10))
        }

        button10.setOnClickListener {
            button10.isEnabled = false
            button11.isEnabled = true
        }

        button11.setOnClickListener {
            button11.isEnabled = false
            button12.isEnabled = true
        }

        button12.setOnClickListener {
            button12.isEnabled = false
            button13.isEnabled = true
        }

        button13.setOnClickListener {
            button13.isEnabled = false
            button14.isEnabled = true
        }

        button14.setOnClickListener {
            button14.isEnabled = false
            button15.isEnabled = true
        }

        button15.setOnClickListener {
            button15.isEnabled = false
            button16.isEnabled = true
        }

        button16.setOnClickListener {
            button16.isEnabled = false
            secondButtonAndTextContainer.visibility = View.GONE
            button17.visibility = View.VISIBLE
            textViewExplanation4.visibility = View.VISIBLE
            button9.setText("Step 2 - Complete")
            enableButton(button17)
        }

        button17.setOnClickListener {
            button17.visibility = View.GONE
            textViewExplanation4.visibility = View.GONE
            button1.isEnabled = true
            button1.setText("Step 1 - Freeze and Request Reports")
            button9.setText("Step 2 - Update Personal Information")
        }

        // ... existing code for enabling and disabling buttons ...

        return view
    }

    private fun enableButton(button: Button) {
        button.visibility = View.VISIBLE
        button.isEnabled = true
    }

    private fun disableAllButtons(vararg buttons: Button) {
        buttons.forEach { button ->
            button.visibility = View.GONE
            button.isEnabled = false
        }
    }
}

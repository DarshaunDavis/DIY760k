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
    // Assuming existing code...

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_right, container, false)

        // Dynamically find button IDs and initialize buttons
        val buttons = (1..17).map { id ->
            view.findViewById<Button>(resources.getIdentifier("button$id", "id", context?.packageName)) }
        val textViewExplanation = listOf(
            view.findViewById<TextView>(R.id.textViewExplanation1),
            view.findViewById<TextView>(R.id.textViewExplanation2),
            view.findViewById<TextView>(R.id.textViewExplanation3),
            view.findViewById<TextView>(R.id.textViewExplanation4)
        )
        val containers = listOf(
            view.findViewById<LinearLayout>(R.id.buttonAndTextContainer),
            view.findViewById<LinearLayout>(R.id.secondButtonAndTextContainer)
        )

        // Set up a generic click listener for buttons that follow a similar pattern
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                // Generic handling based on button index
                handleClick(index, buttons, textViewExplanation, containers)
            }
        }

        // Special handling for button1 and button8 to adjust visibility and text
        setupSpecialButtons(buttons, textViewExplanation, containers)

        return view
    }

    private fun setupSpecialButtons(buttons: List<Button>, textViewExplanation: List<TextView>, containers: List<LinearLayout>) {
        buttons[0].setOnClickListener {
            // Special logic for button1
            it.isEnabled = false
            textViewExplanation[0].visibility = View.GONE
            containers[0].visibility = View.VISIBLE
            buttons[1].isEnabled = true
        }

        buttons[7].setOnClickListener {
            // Special logic for button8
            it.isEnabled = false
            buttons[0].text = "Step 1 Complete"
            containers[0].visibility = View.GONE
            buttons[8].visibility = View.VISIBLE
            textViewExplanation[2].visibility = View.VISIBLE
            buttons[8].isEnabled = true
        }

        buttons[8].setOnClickListener {
            // Special logic for button9
            it.isEnabled = false
            textViewExplanation[2].visibility = View.GONE
            containers[1].visibility = View.VISIBLE // Assuming this is the secondButtonAndTextContainer
            buttons[9].isEnabled = true // Ensuring button10 is enabled
        }

        buttons[15].setOnClickListener {
            // Special logic for button16
            it.isEnabled = false
            containers[1].visibility = View.GONE // Assuming this is the secondButtonAndTextContainer
            buttons[16].visibility = View.VISIBLE
            buttons[16].isEnabled = true
            textViewExplanation[3].visibility = View.VISIBLE // Assuming this is textViewExplanation4
            buttons[8].text = "Step 2 - Complete" // Assuming buttons[8] is button9
        }

        buttons[16].setOnClickListener {
            // Special logic for button17
            it.visibility = View.GONE
            textViewExplanation[3].visibility = View.GONE // Assuming this is textViewExplanation4
            buttons[0].isEnabled = true
            buttons[0].text = "Step 1 - Freeze and Request Reports"
            buttons[8].text = "Step 2 - Update Personal Information" // Assuming buttons[8] is button9
        }
    }

    private fun handleClick(index: Int, buttons: List<Button>, textViewExplanation: List<TextView>, containers: List<LinearLayout>) {
        buttons[index].isEnabled = false
        if (index + 1 < buttons.size) buttons[index + 1].isEnabled = true
        // Additional logic based on index if needed for special cases not covered in setupSpecialButtons
    }
}


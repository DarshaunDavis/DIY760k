package com.lislal.diy760

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class RightFragment : Fragment() {

    data class ButtonAction(
        val layoutResId: Int,
        val nextButtonIndex: Int? // Use null for no subsequent button
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_right, container, false)

        val customContainer = view.findViewById<FrameLayout>(R.id.customContainer)

        // Dynamically find button IDs and initialize buttons
        val buttons = (1..17).map { id ->
            view.findViewById<Button>(resources.getIdentifier("button$id", "id", context?.packageName))
        }
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

            val buttonActions = mapOf(
                1 to ButtonAction(R.layout.experian_step_1_letter_layout, 2),
                2 to ButtonAction(R.layout.equifax_step_1_letter_layout, 3),
                3 to ButtonAction(R.layout.transunion_step_1_letter_layout, 4),
                4 to ButtonAction(R.layout.sagestream_step_1_letter_layout, 5),
                5 to ButtonAction(R.layout.corelogic_step_1_letter_layout, 6),
                6 to ButtonAction(R.layout.innovis_step_1_letter_layout, 7),
                7 to ButtonAction(R.layout.lexisnexis_step_1_letter_layout, 8),
                9 to ButtonAction(R.layout.experian_step_2_letter_layout, 10),
                10 to ButtonAction(R.layout.equifax_step_2_letter_layout, 11),
                11 to ButtonAction(R.layout.transunion_step_2_letter_layout, 12),
                12 to ButtonAction(R.layout.sagestream_step_2_letter_layout, 13),
                13 to ButtonAction(R.layout.corelogic_step_2_letter_layout, 14),
                14 to ButtonAction(R.layout.innovis_step_2_letter_layout, 15),
                15 to ButtonAction(R.layout.lexisnexis_step_2_letter_layout, 16)
            )

        // Setup button actions using the new approach
        setupButtonActions(buttonActions, buttons, customContainer)

        // Handle special button actions
        setupSpecialButtons(buttons, textViewExplanation, containers)

        return view
    }

    private fun setupButtonActions(
        buttonActions: Map<Int, ButtonAction>,
        buttons: List<Button>,
        customContainer: FrameLayout
    ) {
        buttonActions.forEach { (index, action) ->
            buttons[index].setOnClickListener {
                // Ensure container is visible before attempting to inflate and add content
                customContainer.visibility = View.VISIBLE

                customContainer.removeAllViews()
                val customView = LayoutInflater.from(requireContext()).inflate(action.layoutResId, customContainer, false)
                customContainer.addView(customView)

                val generateButton = customView.findViewById<Button>(R.id.generateButton)
                generateButton?.setOnClickListener {
                    customContainer.visibility = View.GONE
                    action.nextButtonIndex?.let { nextIndex ->
                        buttons.getOrNull(nextIndex)?.isEnabled = true
                    }
                    buttons[index].isEnabled = false
                }
            }
        }
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
}
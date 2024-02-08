package com.lislal.diy760

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import java.util.Calendar

class RightFragment : Fragment() {
    private lateinit var customContainer: FrameLayout // Declare as class property
    private val radioSelections = mutableMapOf<Int, String>()

    data class ButtonAction(
        val layoutResId: Int,
        val nextButtonIndex: Int? // Use null for no subsequent button
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_right, container, false)

        // Initialization and setup calls...
        setupRadioButtons(view) // Setup radio buttons and track selections
        val customContainer = view.findViewById<FrameLayout>(R.id.customContainer)

        // Dynamically find button IDs and initialize buttons
        val buttons = (1..19).map { id ->
            view.findViewById<Button>(resources.getIdentifier("button$id", "id", context?.packageName))
        }
        val textViewExplanation = listOf(
            view.findViewById<TextView>(R.id.textViewExplanation1),
            view.findViewById<TextView>(R.id.textViewExplanation2),
            view.findViewById<TextView>(R.id.textViewExplanation3),
            view.findViewById<TextView>(R.id.textViewExplanation4),
            view.findViewById<TextView>(R.id.textViewExplanation5)
        )
        val containers = listOf(
            view.findViewById<LinearLayout>(R.id.buttonAndTextContainer),
            view.findViewById<LinearLayout>(R.id.secondButtonAndTextContainer)
        )

        val buttonActions = mapOf(
            1 to ButtonAction(R.layout.custom_layout, 2),
            2 to ButtonAction(R.layout.experian_step_1_letter_layout, null),
            3 to ButtonAction(R.layout.transunion_step_1_letter_layout, null),
            4 to ButtonAction(R.layout.sagestream_step_1_letter_layout, null),
            5 to ButtonAction(R.layout.corelogic_step_1_letter_layout, null),
            6 to ButtonAction(R.layout.innovis_step_1_letter_layout, null),
            7 to ButtonAction(R.layout.lexisnexis_step_1_letter_layout, null),
            10 to ButtonAction(R.layout.equifax_step_2_letter_layout, 11),
            11 to ButtonAction(R.layout.experian_step_2_letter_layout, null),
            12 to ButtonAction(R.layout.transunion_step_2_letter_layout, null),
            13 to ButtonAction(R.layout.sagestream_step_2_letter_layout, null),
            14 to ButtonAction(R.layout.corelogic_step_2_letter_layout, null),
            15 to ButtonAction(R.layout.innovis_step_2_letter_layout, null),
            16 to ButtonAction(R.layout.lexisnexis_step_2_letter_layout, null)
        )

        // Setup button actions using the new approach
        setupButtonActions(buttonActions, buttons, customContainer)

        // Handle special button actions
        setupSpecialButtons(buttons, textViewExplanation, containers, customContainer)

        return view
    }

    private fun setupButtonActions(
        buttonActions: Map<Int, ButtonAction>,
        buttons: List<Button>,
        customContainer: FrameLayout
    ) {
        val textGenerator = TextGenerator() // Create an instance of TextGenerator

        buttonActions.forEach { (index, action) ->
            buttons[index].setOnClickListener {
                customContainer.visibility = View.VISIBLE

                customContainer.removeAllViews()
                val customView = LayoutInflater.from(requireContext()).inflate(action.layoutResId, customContainer, false)
                customContainer.addView(customView)

                // Setup the birthDatePickerButton if it exists in the current customView
                setupDatePickerButton(customView)

                // Setup radio buttons and track selections
                setupRadioButtons(customView)

                val generateButton = customView.findViewById<Button>(R.id.generateButton)
                val letterTextView = customView.findViewById<TextView>(R.id.generatedContent)
                generateButton?.setOnClickListener {
                    if (areAllRadioGroupsValidated(customView)) {
                        // All RadioGroups have been properly selected, proceed with action
                        val displayText = textGenerator.generateTextFromLayout(customView, requireContext(), radioSelections)
                    letterTextView?.visibility = View.VISIBLE
                    letterTextView?.text = displayText
                    } else {
                        // Not all RadioGroups have a valid selection, alert the user
                        Toast.makeText(requireContext(), "Please make a selection for all questions.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupDatePickerButton(customView: View) {
        val birthDatePickerButton = customView.findViewById<Button>(R.id.birthDatePickerButton)
        birthDatePickerButton?.setOnClickListener {
            showDatePickerDialog(birthDatePickerButton)
        }
    }

    private fun setupRadioButtons(view: View) {
        val radioGroupIds = listOf(
            R.id.radioGroup1, R.id.radioGroup2, R.id.radioGroup3, R.id.radioGroup4,
            R.id.radioGroup5, R.id.radioGroup6, R.id.radioGroup7, R.id.radioGroup8, R.id.radioGroup9
            // Add other RadioGroup IDs as needed
        )

        val tableLayoutsIds = mapOf(
            R.id.radioGroup1 to R.id.name_form_table,
            R.id.radioGroup2 to R.id.address_form_table,
            R.id.radioGroup3 to R.id.accounts_form_table,
            R.id.radioGroup4 to R.id.inquiries_form_table,
            R.id.radioGroup5 to R.id.records_form_table
            // Map other RadioGroups to their corresponding TableLayouts, if any
        )

        radioGroupIds.forEach { radioGroupId ->
            val radioGroup = view.findViewById<RadioGroup>(radioGroupId)
            if (radioGroup == null) {
                Log.e("SetupRadioButtons", "RadioGroup with ID $radioGroupId not found.")
            } else {
                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    val radioButton = group.findViewById<RadioButton>(checkedId)
                    val selection = radioButton.text.toString()
                    radioSelections[group.id] = selection

                    // Update visibility for mapped TableLayouts, if any
                    tableLayoutsIds[radioGroupId]?.let { tableLayoutId ->
                        val tableLayout = view.findViewById<TableLayout>(tableLayoutId)
                        tableLayout?.visibility =
                            if (selection == "Yes") View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

    private fun showDatePickerDialog(button: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, dayOfMonth ->
            // Format the selected date and set it as the button text
            val selectedDate = "${selectedMonth + 1}/$dayOfMonth/$selectedYear"
            button.text = selectedDate // Update the button text instead of EditText
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun setupSpecialButtons(buttons: List<Button>, textViewExplanation: List<TextView>, containers: List<LinearLayout>, customContainer: FrameLayout) {
        buttons[0].setOnClickListener {
            // Special logic for button1
            it.isEnabled = false
            textViewExplanation[0].visibility = View.GONE
            containers[0].visibility = View.VISIBLE
            buttons.subList(1, 9).forEach { button -> button.isEnabled = true } // Enable buttons 2-9 when Step 1 is initiated
        }

        buttons[8].setOnClickListener {
            // Click logic for button9 (Complete Step 1)
            it.isEnabled = false
            buttons[0].text = "Step 1 Complete"
            containers[0].visibility = View.GONE
            customContainer.visibility = View.GONE // Hide or clear the custom layout container
            customContainer.removeAllViews() // Optionally remove all views from the custom container
            textViewExplanation[2].visibility = View.VISIBLE
            buttons[9].visibility = View.VISIBLE
            buttons[9].isEnabled = true
        }

        buttons[9].setOnClickListener {
            // Special logic for button10 (Step 2 initiation)
            it.isEnabled = false
            textViewExplanation[2].visibility = View.GONE
            textViewExplanation[3].visibility = View.VISIBLE
            containers[1].visibility = View.VISIBLE
            buttons.subList(10, 18).forEach { button -> button.isEnabled = true } // Enable buttons 11-18 when Step 2 is initiated
        }

        buttons[17].setOnClickListener {
            // Special logic for button18 (Complete Step 2)
            it.isEnabled = false // Assuming this button should be disabled after completion
            containers[1].visibility = View.GONE
            customContainer.visibility = View.GONE // Hide or clear the custom layout container
            customContainer.removeAllViews() // Optionally remove all views from the custom container
            textViewExplanation[3].visibility = View.GONE
            textViewExplanation[4].visibility = View.VISIBLE
            buttons[18].visibility = View.VISIBLE
            buttons[18].isEnabled = true // Enable Step 3 initiation
            buttons[9].text = "Step 2 Complete" // Update Step 2 button text
        }

        // Assuming you want similar functionality for button19 (Step 3 initiation) as previous steps
        // Adjust this logic based on your specific requirements for Step 3
        buttons[18].setOnClickListener {
            // Special logic for button19
            it.visibility = View.GONE
            textViewExplanation[4].visibility = View.GONE // Assuming this is textViewExplanation4
            textViewExplanation[0].visibility = View.VISIBLE
            buttons[0].isEnabled = true
            buttons[0].text = "Step 1 - Freeze and Request Reports"
            buttons[9].text = "Step 2 - Update Personal Information" // Assuming buttons[8] is button9
        }
    }

    private fun areAllRadioGroupsValidated(customView: View): Boolean {
        val radioGroupIds = listOf(
            R.id.radioGroup1, R.id.radioGroup2, R.id.radioGroup3,
            R.id.radioGroup4, R.id.radioGroup5, R.id.radioGroup6,
            R.id.radioGroup7, R.id.radioGroup8, R.id.radioGroup9
            // Add other RadioGroup IDs as needed
        )

        radioGroupIds.forEach { radioGroupId ->
            customView.findViewById<RadioGroup>(radioGroupId)?.let { radioGroup ->
                val selection = radioSelections[radioGroupId]
                if (selection == null || !(selection == "Yes" || selection == "No")) {
                    // Selection for a visible RadioGroup is missing or invalid
                    return false
                }
            }
        }
        return true
    }

}
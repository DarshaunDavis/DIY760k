package com.lislal.diy760

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.Calendar

class RightFragment : Fragment() {
    private lateinit var customContainer: FrameLayout // Declare as class property
    private val radioSelections = mutableMapOf<Int, String>()
    private lateinit var textGenerator: TextGenerator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment only
        return inflater.inflate(R.layout.fragment_right, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialization and setup calls...
        customContainer = view.findViewById<FrameLayout>(R.id.customContainer)
        setupRadioButtons(view) // Setup radio buttons and track selections
        textGenerator = TextGenerator()

        // Dynamically find button IDs and initialize buttons
        val buttons = (1..28).map { id ->
            view.findViewById<Button>(
                resources.getIdentifier(
                    "button$id",
                    "id",
                    context?.packageName
                )
            )
        }

        val containers = listOf(
            view.findViewById<LinearLayout>(R.id.buttonAndTextContainer),
            view.findViewById<LinearLayout>(R.id.secondButtonAndTextContainer),
            view.findViewById<LinearLayout>(R.id.thirdButtonAndTextContainer)
        )

        val textViewExplanation = listOf(
            view.findViewById<TextView>(R.id.textViewExplanation1),
            view.findViewById<TextView>(R.id.textViewExplanation2),
            view.findViewById<TextView>(R.id.textViewExplanation3),
            view.findViewById<TextView>(R.id.textViewExplanation4),
            view.findViewById<TextView>(R.id.textViewExplanation5),
            view.findViewById<TextView>(R.id.textViewExplanation6)
        )

        val buttonActions = defineButtonActions()
        setupButtonActions(buttonActions, buttons, customContainer)
        setupSpecialButtons(buttons, textViewExplanation, containers, customContainer)
    }

    private fun defineButtonActions(): Map<Int, ButtonAction> {
        return mapOf(
            1 to ButtonAction(R.layout.equifax_step_1_letter_layout, 2),
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
            16 to ButtonAction(R.layout.lexisnexis_step_2_letter_layout, null),
            19 to ButtonAction(R.layout.equifax_complete_letter_layout, 20),
            20 to ButtonAction(R.layout.experian_complete_letter_layout, null),
            21 to ButtonAction(R.layout.transunion_complete_letter_layout, null),
            22 to ButtonAction(R.layout.sagestream_complete_letter_layout, null),
            23 to ButtonAction(R.layout.corelogic_complete_letter_layout, null),
            24 to ButtonAction(R.layout.innovis_complete_letter_layout, null),
            25 to ButtonAction(R.layout.lexisnexis_complete_letter_layout, null)
        )
    }

    private fun setupButtonActions(
        buttonActions: Map<Int, ButtonAction>,
        buttons: List<Button>,
        customContainer: FrameLayout,
    ) {
        val textGenerator = TextGenerator() // Assuming TextGenerator is properly implemented

        buttonActions.forEach { (index, action) ->
            buttons[index].setOnClickListener { // Adjusted index to match the button list (if needed)
                customContainer.visibility = View.VISIBLE
                customContainer.removeAllViews()
                val customView = LayoutInflater.from(requireContext())
                    .inflate(action.layoutResId, customContainer, false)
                customContainer.addView(customView)

                // Immediate Initialization Logic Here
                // For example, setup DatePicker, RadioButtons, and default values
                // Now use 'rootView' instead of 'view' for setupStateSpinner
                if (customView.findViewById<Spinner>(R.id.stateSpinner) != null) {
                    // Correct context and view passed here
                    textGenerator.setupStateSpinner(customView, requireContext())
                }
                setupDatePickerButton(customView)
                setupRadioButtons(customView)
                initializeCustomView(customView) // Implement this based on your custom view's needs

                // Apply the dynamic EditText listener setup to the loaded customView
                setupDynamicEditTextListener(customView)

                // Call setupNameDisputeSpinner here for the name dispute section
                if (customView.findViewById<Spinner>(R.id.name_spinner) != null) {
                    textGenerator.setupNameDisputeSpinner(customView, requireContext())
                }

                // Call setupDisputeResultsSpinner here for the name dispute section
                if (customView.findViewById<Spinner>(R.id.name_result_spinner) != null) {
                    textGenerator.setupDisputeResultsSpinner(customView, requireContext())
                }

                // Setup generateButton and letterTextView logic
                val generateButton = customView.findViewById<Button>(R.id.generateButton)
                val letterTextView = customView.findViewById<TextView>(R.id.generatedContent)
                generateButton?.setOnClickListener {
                    val personalInfo = collectPersonalInfo()
                    val nameDisputeEntries = collectNameDisputeEntries()
                    //val letterGenerator = LetterGenerator(requireContext(), personalInfo, radioSelections, nameDisputeEntries)
                    val letterText = textGenerator.generateTextFromLayout(
                        customView,
                        requireContext(),
                        radioSelections,
                        personalInfo,
                        nameDisputeEntries)
                    letterTextView?.text = letterText
                }
            }
        }
    }

    private fun initializeCustomView(customView: View) {
        // Example of setting default values or performing additional setup
        // This should be customized based on what each custom layout needs
        // For example, setting up additional listeners, default data, etc.
        val textGenerator = TextGenerator() // Assuming TextGenerator is properly implemented

        // Example: If your custom layout has specific fields that need default values

        // More initialization logic as needed for each custom layout

        // Find the 'Add' button and set its click listener
        val addButton = customView.findViewById<Button>(R.id.name_add_button)
        addButton?.setOnClickListener {
            // Call the function to add a new row to the table
            addDuplicateSectionWithRemoveButton(customView, textGenerator)
        }
    }

    private fun setupDatePickerButton(customView: View) {
        val birthDatePickerButton = customView.findViewById<Button>(R.id.birthDatePickerButton)
        birthDatePickerButton?.setOnClickListener {
            // Hide the keyboard before showing the DatePickerDialog
            hideKeyboard(requireContext(), birthDatePickerButton)

            // Show the DatePickerDialog
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

    private fun setActiveStepContainer(activeContainer: LinearLayout, containers: List<LinearLayout>) {
        // Hide all containers first
        containers.forEach { container ->
            container.visibility = View.GONE
        }
        // Make only the active container visible
        activeContainer.visibility = View.VISIBLE
    }

    private fun setupSpecialButtons(
        buttons: List<Button>,
        textViewExplanation: List<TextView>,
        containers: List<LinearLayout>,
        customContainer: FrameLayout
    ) {
        buttons[0].setOnClickListener {
            // Special logic for button1
            it.isEnabled = false
            textViewExplanation[0].visibility = View.GONE
            containers[0].visibility = View.VISIBLE
            setActiveStepContainer(containers[0], containers)
        }

        buttons[8].setOnClickListener {
            // Click logic for button9 (Complete Step 1)
            it.isEnabled = false
            buttons[0].text = getString(R.string.right_fragment_complete_step_1_text)
            buttons[0].isEnabled = true
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
            setActiveStepContainer(containers[1], containers)
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
            buttons[9].text =
                getString(R.string.right_fragment_complete_step_2_text) // Update Step 2 button text
            buttons[9].isEnabled = true
        }

        buttons[18].setOnClickListener {
            it.isEnabled = false
            textViewExplanation[4].visibility = View.GONE
            textViewExplanation[5].visibility = View.VISIBLE
            containers[2].visibility = View.VISIBLE
            setActiveStepContainer(containers[2], containers)
        }

        buttons[26].setOnClickListener {
            it.isEnabled = true
            buttons[18].text = getString(R.string.right_fragment_complete_step_3_text) // Update Step 3 button text
            containers[2].visibility = View.GONE
            customContainer.visibility = View.GONE // Hide or clear the custom layout container
            customContainer.removeAllViews() // Optionally remove all views from the custom container
            textViewExplanation[5].visibility = View.GONE
            buttons[18].isEnabled = true
            /**it.isEnabled = false // Assuming this button should be disabled after completion
            textViewExplanation[6].visibility = View.VISIBLE
            buttons[27].visibility = View.VISIBLE
            buttons[27].isEnabled = true // Enable Step 3 initiation
            **/
        }
    }

    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showDatePickerDialog(button: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, dayOfMonth ->
                // Format the selected date and set it as the button text
                val selectedDate = "${selectedMonth + 1}/$dayOfMonth/$selectedYear"
                button.text = selectedDate // Update the button text instead of EditText
            }, year, month, day)

        datePickerDialog.show()
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

    private fun setupDynamicEditTextListener(customView: View) {
        // Directly find the cityEditText in the dynamically loaded customView
        val cityEditText = customView.findViewById<EditText>(R.id.cityEditText)
        val stateSpinner = customView.findViewById<Spinner>(R.id.stateSpinner)
        val zipEditText = customView.findViewById<EditText>(R.id.zipEditText)
        val socialEditText = customView.findViewById<EditText>(R.id.socialEditText)
        val birthDatePickerButton = customView.findViewById<Button>(R.id.birthDatePickerButton)

        // Check if the views are not null to avoid any null pointer exception
        if (cityEditText != null && stateSpinner != null && zipEditText != null) {
            // Set the listener on cityEditText
            cityEditText.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    // Focus the stateSpinner programmatically to show dropdown
                    stateSpinner.performClick()
                    true // Indicate that the action was handled
                } else {
                    false // Indicate that the action was not handled
                }
            }

            // Set up the OnItemSelectedListener for stateSpinner
            stateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Move focus to zipEditText after a selection has been made
                    zipEditText.requestFocus()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Optionally handle the case where nothing is selected
                }
            }

            if (socialEditText != null && birthDatePickerButton != null) {
                socialEditText.setOnEditorActionListener { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        // Simulate a click on the birthDatePickerButton
                        birthDatePickerButton.performClick()
                        true // Indicate that the action was handled
                    } else {
                        false // Indicate that the action was not handled
                    }
                }
            }
        }
    }

    private fun addDuplicateSectionWithRemoveButton(customView: View, textGenerator: TextGenerator) {
        val tableLayout = customView.findViewById<TableLayout>(R.id.name_form_table)

        // Create a copy of the EditText row
        val editTextRow = TableRow(requireContext()).apply {
            layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        }
        val editText = EditText(requireContext()).apply {
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            hint = getString(R.string.inaccurate_name)
            inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        }
        editTextRow.addView(editText)

        // Create a copy of the first Spinner row
        val spinnerRow1 = TableRow(requireContext())
        val spinner1 = Spinner(requireContext()).apply {
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4f)
        }
        textGenerator.setupNameDisputeSpinnerDirectly(spinner1, requireContext())
        spinnerRow1.addView(spinner1)

        // Create a copy of the second Spinner row, but with a Remove button instead of the Add button
        val spinnerRow2 = TableRow(requireContext())
        val spinner2 = Spinner(requireContext()).apply {
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4f)
        }
        textGenerator.setupDisputeResultsSpinnerDirectly(spinner2, requireContext())
        spinnerRow2.addView(spinner2)

        val removeButton = Button(requireContext()).apply {
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            text = getString(R.string.remove_button)
            setOnClickListener {
                // Remove this section from the TableLayout
                tableLayout.removeView(editTextRow)
                tableLayout.removeView(spinnerRow1)
                tableLayout.removeView(spinnerRow2)
            }
        }
        spinnerRow2.addView(removeButton)

        // Add the new rows to the TableLayout
        tableLayout.addView(editTextRow)
        tableLayout.addView(spinnerRow1)
        tableLayout.addView(spinnerRow2)
    }

    private fun collectPersonalInfo(): PersonalInfoEntry {
        val firstName = view?.findViewById<EditText>(R.id.firstNameEditText)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.lastNameEditText)?.text.toString()
        val address = view?.findViewById<EditText>(R.id.addressEditText)?.text.toString()
        val apartment = view?.findViewById<EditText>(R.id.apartmentEditText)?.text.toString()
        val city = view?.findViewById<EditText>(R.id.cityEditText)?.text.toString()
        val state = view?.findViewById<Spinner>(R.id.stateSpinner)?.selectedItem.toString()
        val zipCode = view?.findViewById<EditText>(R.id.zipEditText)?.text.toString()
        val social = view?.findViewById<EditText>(R.id.socialEditText)?.text.toString()
        val birthDate = view?.findViewById<Button>(R.id.birthDatePickerButton)?.text.toString()

        return PersonalInfoEntry(firstName, lastName, address, apartment, city, state, zipCode, social, birthDate)
    }

    private fun collectNameDisputeEntries(): List<NameDisputeEntry> {
        val entries = mutableListOf<NameDisputeEntry>()
        val tableLayout = view?.findViewById<TableLayout>(R.id.name_form_table)
        tableLayout?.let {
            // Assuming each section consists of 3 rows: EditText, Spinner, Spinner + Button
            val sectionCount = it.childCount / 3
            for (i in 0 until sectionCount) {
                val editTextRow = it.getChildAt(i * 3) as TableRow
                val inaccurateNameEditText = editTextRow.getChildAt(0) as EditText

                val spinnerRow1 = it.getChildAt(i * 3 + 1) as TableRow
                val disputeReasonSpinner = spinnerRow1.getChildAt(0) as Spinner

                val spinnerRow2 = it.getChildAt(i * 3 + 2) as TableRow
                val disputeResultSpinner = spinnerRow2.getChildAt(0) as Spinner

                val inaccurateName = inaccurateNameEditText.text.toString()
                val disputeReason = disputeReasonSpinner.selectedItem.toString()
                val disputeResult = disputeResultSpinner.selectedItem.toString()

                entries.add(NameDisputeEntry(inaccurateName, disputeReason, disputeResult))
            }
        }
        return entries
    }

    data class ButtonAction(
        val layoutResId: Int,
        val nextButtonIndex: Int? // Use null for no subsequent button
    )
}
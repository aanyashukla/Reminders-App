package com.aanyashukla.reminders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.aanyashukla.reminders.databinding.DialogEditReminderBinding
import com.aanyashukla.reminders.databinding.FragmentGeneralInfoBinding
import com.aanyashukla.reminders.databinding.FragmentPasswordsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GeneralInfoFragment: Fragment() {

    private lateinit var binding: FragmentGeneralInfoBinding
    private val preferences by lazy { requireActivity().getSharedPreferences("general_info", Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneralInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValues()
        binding.cardViewDoctor.setOnClickListener { showEditDialog(PREF_DOCTOR) }
        binding.cardViewExamDate.setOnClickListener { showEditDialog(PREF_EXAM_DATE) }
        binding.cardViewBirthday.setOnClickListener { showEditDialog(PREF_BIRTHDAY) }
    }

    private fun displayValues() {
        binding.textViewDoctorValue.text = preferences.getString(PREF_DOCTOR, null)
        binding.textViewExamDateValue.text = preferences.getString(PREF_EXAM_DATE, null)
        binding.textViewBirthdayValue.text = preferences.getString(PREF_BIRTHDAY, null)
    }

    private fun showEditDialog(preferenceKey: String) {
        val dialogBinding = DialogEditReminderBinding.inflate(requireActivity().layoutInflater)
        dialogBinding.editTextValue.setText(preferences.getString(preferenceKey, null))
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Update Value")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                preferences.edit { putString(preferenceKey, dialogBinding.editTextValue.text?.toString()) }
                displayValues()
            }
            .setNegativeButton("Cancel") { _, _ ->
            }
            .show()
    }

    companion object{
        const val PREF_DOCTOR = "pref_doctor"
        const val PREF_EXAM_DATE = "pref_exam_date"
        const val PREF_BIRTHDAY = "pref_birthday"
    }
}
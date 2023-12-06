package com.example.copdapp

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MedicalAdherenceFragment : Fragment() {

    private lateinit var medicationNameEditText: EditText
    private lateinit var dosageEditText: EditText
    private lateinit var timeButton: Button
    private lateinit var addButton: Button
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_medical_adherence, container, false)

        medicationNameEditText = view.findViewById(R.id.etMedicationName)
        dosageEditText = view.findViewById(R.id.etDosage)
        timeButton = view.findViewById(R.id.timePickerMedication)
        addButton = view.findViewById(R.id.btnAddMedication)

        timeButton.setOnClickListener {
            showTimePickerDialog()
        }

        addButton.setOnClickListener {
            addMedication()
        }

        return view
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            selectedTime = String.format("%02d:%02d", hour, minute)
            timeButton.text = selectedTime
        }
        TimePickerDialog(context, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }

    private fun addMedication() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val name = medicationNameEditText.text.toString()
        val dosage = dosageEditText.text.toString()

        if (userId != null && name.isNotBlank() && dosage.isNotBlank() && selectedTime.isNotBlank()) {
            val medicationData = hashMapOf(
                "name" to name,
                "dosage" to dosage,
                "time" to selectedTime
            )
            FirebaseFirestore.getInstance().collection("users").document(userId)
                .collection("medications").add(medicationData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Medication added successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error adding medication: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
        //implement alarms and notifications : VERY IMPORTANT
    }
}

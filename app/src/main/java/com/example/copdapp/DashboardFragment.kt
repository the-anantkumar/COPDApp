package com.example.copdapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardFragment : Fragment() {

    private lateinit var textViewRole: TextView
    private lateinit var textViewScore: TextView
    private lateinit var textViewMedications: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //toast for testing
        Toast.makeText(context, "DashboardFragment", Toast.LENGTH_SHORT).show()
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        textViewRole = view.findViewById(R.id.textViewRole)
        textViewScore = view.findViewById(R.id.textViewScore)
        textViewMedications = view.findViewById(R.id.textViewMedications)
        loadUserData()

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(it)

            userRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val role = document.getString("role") ?: "No role available"
                        val score = document.getLong("questionnaireScore")?.toInt() ?: 0

                        textViewRole.text = "Role: $role"
                        textViewScore.text = "Questionnaire Score: $score"
                        loadMedications(it)
                    } else {
                        textViewRole.text = "No role available"
                        textViewScore.text = "No score available"
                    }
                }
                .addOnFailureListener { e ->
                    textViewRole.text = "Error loading role: ${e.message}"
                    textViewScore.text = "Error loading score: ${e.message}"
                }
        }
    }

    private fun loadMedications(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId).collection("medications").get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val medicationsStringBuilder = StringBuilder()
                    for (document in documents) {
                        val name = document.getString("name") ?: "Unknown"
                        val dosage = document.getString("dosage") ?: "Unknown"
                        val time = document.getString("time") ?: "Unknown"

                        medicationsStringBuilder.append("$name - Dosage: $dosage at $time\n")
                    }
                    textViewMedications.text = medicationsStringBuilder.toString()
                } else {
                    textViewMedications.text = "No medications"
                }
            }
            .addOnFailureListener { e ->
                textViewMedications.text = "Error loading medications: ${e.message}"
            }
    }

}

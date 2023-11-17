package com.example.copdapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardFragment : Fragment() {

    private lateinit var textViewRole: TextView
    private lateinit var textViewScore: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        textViewRole = view.findViewById(R.id.textViewRole)
        textViewScore = view.findViewById(R.id.textViewScore)

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
}

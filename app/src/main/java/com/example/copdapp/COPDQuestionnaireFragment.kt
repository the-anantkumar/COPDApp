package com.example.copdapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class COPDQuestionnaireFragment : Fragment() {

    private lateinit var radioGroupQuestion1: RadioGroup
    private lateinit var radioGroupQuestion2: RadioGroup
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_copd_questionnaire, container, false)

        radioGroupQuestion1 = view.findViewById(R.id.radioGroupQuestion1)
        radioGroupQuestion2 = view.findViewById(R.id.radioGroupQuestion2)
        submitButton = view.findViewById(R.id.btnSubmit)

        submitButton.setOnClickListener {
            val score = calculateScore()
            storeScoreInFirestore(score)
        }

        return view
    }

    private fun calculateScore(): Int {
        val score1 = radioGroupQuestion1.checkedRadioButtonId.let { view?.findViewById<RadioButton>(it)?.tag as? Int ?: 0 }
        val score2 = radioGroupQuestion2.checkedRadioButtonId.let { view?.findViewById<RadioButton>(it)?.tag as? Int ?: 0 }
        return score1 + score2
    }

    private fun storeScoreInFirestore(score: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {uid->
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(uid)
            //print score to console
            println("Score: $score")
            userRef.update("questionnaireScore", score)
                .addOnSuccessListener {
                    Toast.makeText(context, "Score saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error saving score: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}


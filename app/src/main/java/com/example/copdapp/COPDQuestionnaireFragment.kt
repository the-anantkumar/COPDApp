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

    private lateinit var radioGroupCough: RadioGroup
    private lateinit var radioGroupPhlegm: RadioGroup
    private lateinit var radioGroupShortBreath: RadioGroup
    private lateinit var radioGroupWheeze: RadioGroup
    private lateinit var radioGroupColds: RadioGroup
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_copd_questionnaire, container, false)

        radioGroupCough = view.findViewById(R.id.radioGroupQ1)
        radioGroupPhlegm = view.findViewById(R.id.radioGroupQ2)
        radioGroupShortBreath = view.findViewById(R.id.radioGroupQ3)
        radioGroupWheeze = view.findViewById(R.id.radioGroupQ4)
        radioGroupColds = view.findViewById(R.id.radioGroupQ5)
        submitButton = view.findViewById(R.id.btnSubmitQ)

        submitButton.setOnClickListener {
            val score = calculateScore()
            storeScoreInFirestore(score)
        }

        return view
    }

    private fun calculateScore(): Int {
        val score1 = radioGroupCough.checkedRadioButtonId.let { id ->
            view?.findViewById<RadioButton>(id)?.tag.toString().toIntOrNull() ?: 0
        }
        val score2 = radioGroupPhlegm.checkedRadioButtonId.let { id ->
            view?.findViewById<RadioButton>(id)?.tag.toString().toIntOrNull() ?: 0
        }
        val score3 = radioGroupShortBreath.checkedRadioButtonId.let { id ->
            view?.findViewById<RadioButton>(id)?.tag.toString().toIntOrNull() ?: 0
        }
        val score4 = radioGroupWheeze.checkedRadioButtonId.let { id ->
            view?.findViewById<RadioButton>(id)?.tag.toString().toIntOrNull() ?: 0
        }
        val score5 = radioGroupColds.checkedRadioButtonId.let { id ->
            view?.findViewById<RadioButton>(id)?.tag.toString().toIntOrNull() ?: 0
        }
        println("Score 1: $score1")
        println("Score 2: $score2")
        return score1 + score2 + score3 + score4 + score5
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


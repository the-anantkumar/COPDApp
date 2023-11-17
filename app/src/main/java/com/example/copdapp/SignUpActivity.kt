package com.example.copdapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val emailEditText = findViewById<EditText>(R.id.emailSignUpEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordSignUpEditText)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val roleRadioGroup = findViewById<RadioGroup>(R.id.roleRadioGroup)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val role = when (roleRadioGroup.checkedRadioButtonId) {
                R.id.radio_patient -> "Patient"
                R.id.radio_caregiver -> "Caregiver"
                R.id.radio_seeker -> "Seeker"
                else -> ""
            }

            signUpUser(email, password, role)
        }
    }

    private fun signUpUser(email: String, password: String, role: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-up success, store additional user details
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    storeUserDetails(userId, role)

                    Toast.makeText(baseContext, "Sign up successful.",
                        Toast.LENGTH_SHORT).show()
                    // Navigate to next screen or update UI
                } else {
                    // If sign-up fails, display a message to the user
                    Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun storeUserDetails(userId: String, role: String) {
        val db = Firebase.firestore
        val userDetails = hashMapOf(
            "role" to role
        )

        db.collection("users").document(userId).set(userDetails)
            .addOnSuccessListener {
                Toast.makeText(baseContext, "User details stored successfully.",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(baseContext, "Failed to store user details: ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}
package com.example.copdapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.button.MaterialButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize UI components
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        signUpTextView = findViewById(R.id.signUpTextView)

        // Set up button click listeners
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (validateInputs(email, password)) {
                signIn(email, password)
            }
        }

        signUpTextView.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        // You can add more sophisticated validation if needed
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    navigateToHome()
                } else {
                    displayErrorMessage()
                }
            }
    }

    private fun navigateToHome() {
        Toast.makeText(baseContext, "Login Successful", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // Close the current activity
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun displayErrorMessage() {
        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
    }
}

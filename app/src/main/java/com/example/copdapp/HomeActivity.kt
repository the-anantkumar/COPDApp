
package com.example.copdapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Toast.makeText(this, "Welcome to COPD App!", Toast.LENGTH_SHORT).show()

        findViewById<Button>(R.id.btnDashboard).setOnClickListener {
            //switch to the dashboard fragment
            navigateToFragment(DashboardFragment())
        }

        findViewById<Button>(R.id.btnMedicalAdherence).setOnClickListener {
            // Navigate to Medical Adherence
           navigateToFragment(MedicationAdherenceFragment())
        }

        findViewById<Button>(R.id.btnCOPDAwareness).setOnClickListener {
            // Navigate to COPD Awareness Activity or show COPD Awareness content
            navigateToFragment(COPDAwarenessFragment())
        }

        findViewById<Button>(R.id.btnCOPDQuestionnaire).setOnClickListener {
            // Navigate to COPD Questionnaire Activity or show COPD Questionnaire content
            navigateToFragment(COPDQuestionnaireFragment())
        }

        findViewById<Button>(R.id.btnWalkTest).setOnClickListener {
            // Navigate to Walk Test Game Activity or show Walk Test Game content
        }

        findViewById<Button>(R.id.btnCommunityChat).setOnClickListener {
            // Navigate to Community Chat Activity or show Community Chat content
        }
    }
    private fun navigateToFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}


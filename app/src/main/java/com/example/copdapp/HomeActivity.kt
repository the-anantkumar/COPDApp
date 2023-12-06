package com.example.copdapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar) // Make sure you have a Toolbar with this ID in your layout
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        // Set up the ActionBarDrawerToggle, which calls the Toolbar
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Sync the state of the drawer indicator/affordance with the linked DrawerLayout
        toggle.syncState()

        // Set up default fragment
        if (savedInstanceState == null) {
            // Set up the default fragment
            //make a toast of success message
            Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show()
           // navigateToFragment(DashboardFragment(), "Dashboard");
           // navigateToFragment(MedicalAdherenceFragment(), "Med Adherence");
           // navigateToFragment(COPDQuestionnaireFragment(), "COPD Questionnaire");
           // navigateToFragment(COPDAwarenessFragment(), "COPD Awareness");
             navigateToFragment(WalkTestFragment(), "Walk Test")
        }

        //set up navigation item selected listener
        navigationView.setNavigationItemSelectedListener { menuItem ->
            //menuItem.isChecked = true
//            drawerLayout.closeDrawer(GravityCompat.START)

            when (menuItem.itemId) {
                R.id.nav_dashboard -> {
                    Toast.makeText(this, "pressed dashboard", Toast.LENGTH_SHORT).show()
                    navigateToFragment(DashboardFragment(), "Dashboard")
                }
                R.id.nav_medical_adherence -> {
                    Toast.makeText(this, "pressed med", Toast.LENGTH_SHORT).show()
                    navigateToFragment(MedicalAdherenceFragment(), "Med Adherence")
                }
                R.id.nav_copd_awareness -> {
                    // Handle COPD Awareness navigation
                    Toast.makeText(this, "pressed copd", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_copd_questionnaire -> {
                    // Handle COPD Questionnaire navigation
                    Toast.makeText(this, "pressed questionnaire", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_walk_test -> {
                    // Handle Walk Test Game navigation
                    Toast.makeText(this, "pressed walk test", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_community_chat -> {
                    // Handle Community Chat navigation
                    Toast.makeText(this, "pressed community chat", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true // This line ensures that true is returned for any case
        }
    }

    private fun navigateToFragment(fragment: Fragment, title: String) {
        Toast.makeText(this, "Navigating to $title", Toast.LENGTH_SHORT).show()
        Log.d("NavigationDrawer", "Navigating to $title")
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit()
        // Set the toolbar title
        supportActionBar?.title = title
    }


    override fun onSupportNavigateUp(): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

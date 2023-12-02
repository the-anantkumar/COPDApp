package com.example.copdapp

import android.os.Bundle
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
            navigateToFragment(DashboardFragment(), "Dashboard")
        }

        // Set up navigation item selection
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawer(GravityCompat.START)
            when (menuItem.itemId) {
                R.id.nav_dashboard -> navigateToFragment(DashboardFragment(), menuItem.title.toString())
                R.id.nav_medical_adherence -> navigateToFragment(MedicationAdherenceFragment(), menuItem.title.toString())
                // Add cases for other menu items
            }
            true
        }
    }

    private fun navigateToFragment(fragment: Fragment, title: String) {
        Toast.makeText(this, "Navigating to $title", Toast.LENGTH_SHORT).show()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.commit()

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

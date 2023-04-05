package com.msardevelopment.rawggameapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.msardevelopment.rawggameapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editSharedPref: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.mobile_navigation)

        sharedPref = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        editSharedPref = sharedPref.edit()

        val onboardingDone = sharedPref.getBoolean(Constants.ONBOARDING_DONE, false)

        if(onboardingDone) {
            graph.setStartDestination(R.id.discoverGamesFragment)
        }
        else {
            graph.setStartDestination(R.id.onboardingFragment)
        }

        navHostFragment.navController.graph = graph

        val navView: BottomNavigationView = binding.navView

        navView.itemIconTintList = null

        //google recommended way of hiding bottom nav menu on certain fragments
        //it can cause flickering when opening additional fragments
        //so added condition "|| destination.id == R.id.genreDetailsBottomSheetFragment"
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.onboardingFragment || destination.id == R.id.genreDetailsBottomSheetFragment) {
                navView.visibility = View.INVISIBLE
            } else {
                navView.visibility = View.VISIBLE
            }
        }

        navView.setupWithNavController(navHostFragment.navController)
    }
}
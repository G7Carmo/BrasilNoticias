package com.gds.brasilnoticias.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gds.brasilnoticias.R
import com.gds.brasilnoticias.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navhostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding) {
        val navController = setupNavHost()
        setupBottomNavigation(binding, navController)
    }

    private fun setupNavHost(): NavController {
        navhostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navhostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            title = destination.label
        }
        return navController
    }

    private fun setupBottomNavigation(binding: ActivityMainBinding,navController: NavController) {
        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnNavigationItemReselectedListener { }
        }
    }

}
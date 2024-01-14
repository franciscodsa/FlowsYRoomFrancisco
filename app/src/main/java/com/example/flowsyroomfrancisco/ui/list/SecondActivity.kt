package com.example.flowsyroomfrancisco.ui.list

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.flowsyroomfrancisco.R

import com.example.flowsyroomfrancisco.databinding.ActivitySecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySecondBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa el DrawerLayout
        drawerLayout = binding.drawerLayout

        // Configura el NavController y AppBarConfiguration
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.secondFragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Set up the ActionBar and DrawerLayout
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.blogsFragment, R.id.postFragment),
            drawerLayout
        )

        // Configura el NavigationUI con el NavController y el DrawerLayout
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Configura el NavigationView
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_blogs -> {
                    navController.navigate(R.id.blogsFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_posts -> {
                    navController.navigate(R.id.postFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        // Set up the ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

       /* initUI()*/
    }

    // Agrega el siguiente método para manejar el evento Up (botón de navegación hacia atrás)
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initUI() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.secondFragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
    }
}
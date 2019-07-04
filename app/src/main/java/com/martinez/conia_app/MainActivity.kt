package com.martinez.conia_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Creojsdakjd
        Log.d("Entro al if", "creo un main actity")
        //verifyUserLoggedIn()
        val navController = Navigation.findNavController(this, R.id.fragment)
        setUpBottomNavMenu(navController)
        setUpActionBar(navController)
        Persistencia.database
    }


    private fun setUpBottomNavMenu(navController: NavController){
        bottom_nav_view?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setUpActionBar(navController: NavController){
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.fragment), null)
    }
}
package com.example.shakshya.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.shakshya.R
import com.example.shakshya.fragment.AboutUsFragment
import com.example.shakshya.fragment.ContactUsFragment
import com.example.shakshya.fragment.DashboardFragment
import com.example.shakshya.fragment.TeamFragment
import com.example.shakshya.util.ConnectionManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var frameLayout: FrameLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var previousMenuItem: MenuItem? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frameLayout=findViewById(R.id.frameLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        navigationView=findViewById(R.id.navigationView)
        drawerLayout=findViewById(R.id.drawerLayout)
        toolbar=findViewById(R.id.toolBar)
        setUpToolbar()
        openDashboard()
        val actionBarDrawerToggle= ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if (previousMenuItem!=null)
                previousMenuItem?.isChecked=false
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId){
                R.id.dashboard -> {
                    openDashboard()
                    drawerLayout.closeDrawers()
                }
                R.id.about_app -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, AboutUsFragment())
                            .commit()
                    supportActionBar?.title="About Us"
                    drawerLayout.closeDrawers()
                }
                R.id.team -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, TeamFragment())
                            .commit()
                    supportActionBar?.title="Our Team"
                    drawerLayout.closeDrawers()
                }
                R.id.contact -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, ContactUsFragment())
                            .commit()
                    supportActionBar?.title="Contact Us"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
        if(ConnectionManager().checkConnectivity(this)){
            //Internet available
            Toast.makeText(this,"Internet Connected.", Toast.LENGTH_SHORT).show()
        }else{
            //Internet not available
            val dialog= AlertDialog.Builder(this)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Not Connected..!!")
            dialog.setPositiveButton("Open Settings"){text,listener ->
                val settingsIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }
            dialog.setNegativeButton("Exit"){text,listener ->
                ActivityCompat.finishAffinity(this)
            }
            dialog.create()
            dialog.show()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if (id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title ="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        val flag=supportFragmentManager.findFragmentById(R.id.frameLayout)
        when(flag){
            !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }
        drawerLayout.closeDrawers()
    }
    fun openDashboard(){
        val transaction=supportFragmentManager.beginTransaction()
        val fragment=DashboardFragment()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
        supportActionBar?.title="Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}
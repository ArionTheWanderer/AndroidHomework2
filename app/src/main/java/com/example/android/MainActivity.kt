package com.example.android

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.android.R.id.nav_host_fragment
import com.example.android.ui.home.HomeFragment
import com.example.android.ui.prikoli.PrikoliFragment
import com.example.android.ui.receive.ReceiveFragment
import com.example.android.ui.recept.ReceptFragment
import com.example.android.ui.report.ReportFragment
import com.example.android.ui.send.SendFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), ReportFragment.OnButtonClick, SendFragment.onButtonClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        nav_view.setNavigationItemSelectedListener(this::onNavigationItemSelected)
        supportFragmentManager
            .beginTransaction()
            .add(nav_host_fragment, HomeFragment())
            .addToBackStack(null)
            .commit()
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onReportButtonClick() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(nav_host_fragment, SendFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onSendButtonClick(report: String, author: String, email: String) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(nav_host_fragment, ReceiveFragment().apply {
                arguments = Bundle().apply {
                    putString("report", report)
                    putString("author", author)
                    putString("email", email)
                }
            })
            .addToBackStack(null)
            .commit()
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment = when (item.itemId) {
            R.id.nav_home -> HomeFragment()
            R.id.nav_prikoli -> PrikoliFragment()
            R.id.nav_recept -> ReceptFragment()
            R.id.nav_report -> ReportFragment()
            else -> error("Item doesn't exist")
        }
        supportFragmentManager
            .beginTransaction()
            .replace(nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
        title = item.title
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

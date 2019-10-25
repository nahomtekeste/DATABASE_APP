package com.example.database_app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.widget.ArrayAdapter as ArrayAdapter1

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mDbAdapter: MyDBAdapter? = null
    private val mAllFaculties = arrayOf("Engineering", "Business", "Arts")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        initializeDatabase()
        loadList()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initializeViews() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        facultie_spinner.adapter = ArrayAdapter1(this@MainActivity,
            android.R.layout.simple_list_item_1,
            mAllFaculties)
        add_student.setOnClickListener {
            mDbAdapter?.insertStudent(student_name.text.toString(),facultie_spinner.selectedItemPosition + 1)
            loadList()
        }
        delet_engineers.setOnClickListener() {
            mDbAdapter?.deleteAllEngineers()
            loadList()
        }
    }


    private fun initializeDatabase() {
        mDbAdapter = MyDBAdapter(this@MainActivity)
        mDbAdapter?.open()
    }

    private fun loadList() {
        val allStudents: List<String>? = mDbAdapter?.selectAllStudents()
        val adapter = ArrayAdapter1<String?>(this@MainActivity,
            android.R.layout.simple_list_item_1,
            allStudents!!
        )
        student_list.adapter = adapter
    }


}

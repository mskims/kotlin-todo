package com.donnfelker.kotlinmix

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = "KotlinRealmAndAnko"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val editFragment = EditFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content_main, editFragment, editFragment.javaClass.simpleName)
                    .addToBackStack(editFragment.javaClass.simpleName)
                    .commit()
        }

        val fragment = TodosFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_main, fragment, fragment.javaClass.simpleName)
                .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.login) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

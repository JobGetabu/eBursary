package com.job.ebursary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_data.*


class EnterDataActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, EnterDataActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_done, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === android.R.id.home) {
            finish()
        } else {
            Toast.makeText(applicationContext, "Entry added", Toast.LENGTH_SHORT).show()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

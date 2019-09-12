package com.job.ebursary

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_data.*


class EnterDataActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, EnterDataActivity::class.java)
    }

    private var auth = FirebaseAuth.getInstance()
    private var firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        setSupportActionBar(toolbar)


        val names = auth.currentUser!!.displayName!!


        fullname.editText!!.setText(names)

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

    private fun submitInfo(){
        val name = fullname.editText!!.text.toString()
        val adminNo = admin_number.editText!!.text.toString()
        val id = idnumber.editText!!.text.toString()
        val county = county.editText!!.text.toString()
        val number = phone.editText!!.text.toString()


        val progressBar = ProgressDialog(this)
        progressBar.setMessage("Please wait...")
        progressBar.setTitle("Applying new bursary")
        progressBar.show()

        val dataMap = mapOf<String, Any>()
        dataMap.plus(Pair("name",name))
        dataMap.plus(Pair("Admission",adminNo))
        dataMap.plus(Pair("idnumber",id))
        dataMap.plus(Pair("county",county))
        dataMap.plus(Pair("phone",number))

        firestore.collection("applications")
            .document(auth.currentUser!!.uid).set(dataMap)
            .addOnSuccessListener {
                progressBar.dismiss()
                finish()
                Toast.makeText(this@EnterDataActivity,"Success",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this@EnterDataActivity,"Failed to apply",Toast.LENGTH_SHORT).show()
            }

    }
}

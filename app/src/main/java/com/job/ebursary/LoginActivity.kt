package com.job.ebursary


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.job.ebursary.commoners.Tools

class LoginActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, LoginActivity::class.java)
    }

    private var parent_view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        parent_view = findViewById(android.R.id.content)

        Tools.setSystemBarColor(this, android.R.color.white)
        Tools.setSystemBarLight(this)

        (findViewById(R.id.forgot_password) as View).setOnClickListener {
            Snackbar.make(
                parent_view!!,
                "Sorry mate! create a new account.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        (findViewById(R.id.sign_up) as View).setOnClickListener {

            startActivity(RegisterActivity.newIntent(this@LoginActivity))
        }
    }

    fun toRegisterActivity(v:View){
        startActivity(RegisterActivity.newIntent(this))
    }

    fun toMainActivity(v:View){
        startActivity(MainActivity.newIntent(this))
    }
}

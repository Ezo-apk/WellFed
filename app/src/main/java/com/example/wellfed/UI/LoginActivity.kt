package com.example.wellfed.UI

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.wellfed.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val goToMain = Intent(this, MainActivity::class.java)
            startActivity(goToMain)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = Firebase.auth
        val database = Firebase.firestore

        val loginBtn = findViewById<Button>(R.id.loginbtn)
        val signupBtn = findViewById<Button>(R.id.signupbtn)
        val forgotpass = findViewById<TextView>(R.id.forgotPassText)
        val emailText = findViewById<EditText>(R.id.email)
        val passwordText = findViewById<EditText>(R.id.password)

        signupBtn.setOnClickListener {
            val goToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(goToRegister)
            this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
            finish()
        }

        forgotpass.setOnClickListener {
            Toast.makeText(this, "Damn, that sucks", Toast.LENGTH_SHORT).show()
        }


        loginBtn.setOnClickListener{
            if (TextUtils.isEmpty(emailText.text.toString())) {
                Toast.makeText(this, "Please insert email", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(passwordText.text.toString())) {
                Toast.makeText(this, "Please insert password", Toast.LENGTH_SHORT).show()
            } else {

                auth.signInWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val goToMain = Intent(this, MainActivity::class.java)
                            startActivity(goToMain)
                            this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                            finish()
                        } else {
                            Toast.makeText(baseContext, "Wrong credentials", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
    }
}
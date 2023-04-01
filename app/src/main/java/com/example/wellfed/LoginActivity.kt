package com.example.wellfed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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

        val loginBtn = findViewById<Button>(R.id.loginbtn)
        val signupBtn = findViewById<Button>(R.id.signupbtn)
        val forgotpass = findViewById<TextView>(R.id.forgotPassText)
        val emailText = findViewById<EditText>(R.id.email)
        val passwordText = findViewById<EditText>(R.id.password)

        signupBtn.setOnClickListener {
            val goToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(goToRegister)
            finish()
        }

        forgotpass.setOnClickListener {
            Toast.makeText(this, "Remember it then", Toast.LENGTH_SHORT).show()
//            val goToForgot = Intent(this, ForgotPasswordActivity::class.java)
//            startActivity(goToForgot)
//            finish()
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
                            val user = auth.currentUser
                            val goToMain = Intent(this, MainActivity::class.java)
                            startActivity(goToMain)
                            finish()
                        } else {
                            Toast.makeText(baseContext, "Wrong credentials", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
    }
}
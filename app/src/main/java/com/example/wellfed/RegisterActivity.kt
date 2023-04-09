package com.example.wellfed

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wellfed.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_register)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = Firebase.auth

        val emailText = findViewById<EditText>(R.id.email)
        val usernameText = findViewById<EditText>(R.id.username)
        val passwordText = findViewById<EditText>(R.id.password)
        val confirmText = findViewById<EditText>(R.id.confirm_password)
        val createBtn = findViewById<Button>(R.id.createAccbtn)
        val backBtn = findViewById<Button>(R.id.backToLoginbtn)

        backBtn.setOnClickListener {
            val goToLogin = Intent(this, LoginActivity::class.java)
            startActivity(goToLogin)
            finish()
        }

        createBtn.setOnClickListener{
            if (TextUtils.isEmpty(usernameText.text.toString())) {
                Toast.makeText(this, "Please choose a username", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(emailText.text.toString())) {
                Toast.makeText(this, "Please insert email", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(passwordText.text.toString())) {
                Toast.makeText(this, "Please choose a password", Toast.LENGTH_SHORT).show()
            } else if (passwordText.text.toString() != confirmText.text.toString()) {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "You did it fam.", Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser

                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
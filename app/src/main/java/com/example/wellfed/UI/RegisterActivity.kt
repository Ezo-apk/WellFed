package com.example.wellfed.UI

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wellfed.R
import com.example.wellfed.functionality.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

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
        database = Firebase.firestore

        val emailText = findViewById<EditText>(R.id.email)
        val usernameText = findViewById<EditText>(R.id.username)
        val passwordText = findViewById<EditText>(R.id.password)
        val confirmText = findViewById<EditText>(R.id.confirm_password)
        val createBtn = findViewById<Button>(R.id.createAccbtn)
        val backBtn = findViewById<Button>(R.id.backToLoginbtn)


        backBtn.setOnClickListener {
            val goToLogin = Intent(this, LoginActivity::class.java)
            startActivity(goToLogin)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            finish()
        }

        createBtn.setOnClickListener{
            if(checkCredentials(usernameText.text.toString(),
                                emailText.text.toString(),
                                passwordText.text.toString(),
                                confirmText.text.toString())) {
                auth.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "You did it fam.", Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser

                            database.collection("users").add(User(usernameText.text.toString(),
                                                                        emailText.text.toString(),
                                                                        user?.uid))



                            startActivity(Intent(this, MainActivity::class.java))
                            this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                            finish()
                        } else {
                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }

    private fun checkCredentials(username : String,
                                 email : String,
                                 password : String,
                                 passwordCheck : String) : Boolean {

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please choose a username", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please insert email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please choose a password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != passwordCheck) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            return false
        }

//        val emailQuery = database.collection("users").whereEqualTo("email", email)
//        var isReal = false

//        emailQuery.get().addOnCompleteListener { task ->
//            var flag = false
//            if (task.isSuccessful) {
//                for (document in task.result) {
//                    if (document.exists()) {
//                        Toast.makeText(this, "This Email is already in use", Toast.LENGTH_SHORT).show()
//                        flag = true
//                    }
//                }
//            }
//        }


        return true
    }
}
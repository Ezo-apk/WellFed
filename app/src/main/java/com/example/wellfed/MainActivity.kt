package com.example.wellfed

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.WindowManager
import androidx.navigation.ui.AppBarConfiguration
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.wellfed.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    lateinit var photoPath : Uri








    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)



        binding = ActivityMainBinding.inflate(layoutInflater)

        val friendsFragment = FriendsFragment()
        val homeFragment = HomeFragment()




        auth = Firebase.auth
        val user = auth.currentUser
        val logout = findViewById<Button>(R.id.LogOutbtn)

        if (user == null) {
            val goToLogin = Intent(this, LoginActivity::class.java)
            startActivity(goToLogin)
            finish()
        }

        logout.setOnClickListener {
            Firebase.auth.signOut()
            val goToLogin = Intent(this, LoginActivity::class.java)
            startActivity(goToLogin)
            finish()
        }







        setCurrentFragment(homeFragment)

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.camera -> beginCameraPain()
                R.id.friends -> setCurrentFragment(friendsFragment)
                R.id.home -> setCurrentFragment(homeFragment)
            }
            true
        }


    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragment, fragment)
            commit()
        }


    private fun beginCameraPain() {
        Toast.makeText(this, "Kill yourself", Toast.LENGTH_LONG).show()
//        val goToCamera = Intent(this, CameraActivity::class.java)
//        startActivity(goToCamera)
//        createPost()
    }

//    private fun getPhotoFile(fileName :String): File {
//        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile(fileName, ".jpg", storageDirectory)
//    }
//    private fun getPhoto() {
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        photoFile = getPhotoFile("Photo.jpg")
//
//
//        val fileProvider = FileProvider.getUriForFile(this, "com.example.fileprovider", photoFile)
//        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
//
//        if(takePictureIntent.resolveActivity(this.packageManager) != null) {
//            startActivityForResult(takePictureIntent, 123)
//        } else {
//            Toast.makeText(this, "Install a camera app pls!", Toast.LENGTH_SHORT).show()
//        }
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == 123 && resultCode == Activity.RESULT_OK) {
//            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
//            findViewById<ImageView>(R.id.ImageFromCamera).setImageBitmap(takenImage)
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }

//    private fun createPost(takenImage: Bitmap) {

//    private fun createPost() {
//        val aux = (intent.getStringExtra("Uri"))
//
//        println("\n\n\n\n\n\n")
//        println(aux)
//        println(aux is String)
//        println("\n\n\n\n\n\n")
//        photoPath = Uri.parse(aux)
//        if (!Uri.EMPTY.equals(photoPath)) {
//            println("\n\n\n\n")
//            println("NO EMPTY LESGOOOOOOO")
//            println("\n\n\n\n")
//        }

//    }
//
//    override fun onResume() {
//
//        super.onResume()
//        if(CameraActivity().isFinishing) {
//            createPost()
//        }
//    }

}
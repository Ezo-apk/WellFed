package com.example.wellfed.UI

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wellfed.R
import com.example.wellfed.databinding.ActivityMainBinding
import com.example.wellfed.functionality.ApiInterface
import com.example.wellfed.functionality.NutritionDataItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = ""
const val API_KEY = "A5XqIsUW3e3gzQgbBwc0hQ==u6Xh5cbGrdbkgrEx"
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    lateinit var photoPath : Uri






    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOut -> {
                Firebase.auth.signOut()
                val goToLogin = Intent(this, LoginActivity::class.java)
                startActivity(goToLogin)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        binding = ActivityMainBinding.inflate(layoutInflater)

        val friendsFragment = FriendsFragment()
        val homeFragment = HomeFragment()




        auth = Firebase.auth
        val user = auth.currentUser
        if (user == null) {
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


    private fun getFoodData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getFoodData()

        retrofitData.enqueue(object : Callback<List<NutritionDataItem>?> {
            override fun onResponse(
                        call: Call<List<NutritionDataItem>?>,
                        response: Response<List<NutritionDataItem>?>) {
                val responseBody = response.body()
            }

            override fun onFailure(call: Call<List<NutritionDataItem>?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun beginCameraPain() {
        Toast.makeText(this, "I simply refuse", Toast.LENGTH_LONG).show()
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
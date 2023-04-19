package com.example.wellfed.UI

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.wellfed.R
import com.example.wellfed.databinding.ActivityMainBinding
import com.example.wellfed.functionality.ApiInterface
import com.example.wellfed.functionality.NutritionDataItem
import com.example.wellfed.functionality.TestData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Math.abs
import kotlin.random.Random


//const val BASE_URL = "https://api.api-ninjas.com/"

//const val BASE_URL = "https://www.random.org/"
//const val BASE_URL = "http://www.randomnumberapi.com/"
const val BASE_URL = "https://jsonplaceholder.typicode.com/"
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
                this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
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


        // Checking if a user is already logged in
        auth = Firebase.auth
        val user = auth.currentUser
        if (user == null) {
            val goToLogin = Intent(this, LoginActivity::class.java)
            startActivity(goToLogin)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            finish()
        }


        //Changing fragments from the bottom menu
        setCurrentFragment(homeFragment)
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.camera ->  beginCameraPain()
                R.id.friends -> getFoodData()//setCurrentFragment(friendsFragment)
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


//    @OptIn(DelicateCoroutinesApi::class)
    private fun getFoodData() {
        val outputText = findViewById<TextView>(R.id.testOutputCall)

        val retrofitInstance = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        val apiInstance = retrofitInstance.create(ApiInterface::class.java)

        val retrofitData = apiInstance.getData()
        retrofitData.enqueue(object : Callback<List<TestData>?> {
            override fun onResponse(
                        call: Call<List<TestData>?>,
                        response: Response<List<TestData>?>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val numList = mutableListOf<Int>()
                    for (data in responseBody) {
                        numList.add(data.id)
                        var calorie = numList[Random.nextInt(numList.size)] * 10
                        calorie = kotlin.math.abs((calorie - 500) * 4 + 500)
                        outputText.text = calorie.toString()
                    }

                }
            }
            override fun onFailure(call: Call<List<TestData>?>, t: Throwable) {
                Log.d("MainActivity", "On Failure: " + t.message)
            }
        })
    }

    private fun beginCameraPain() {
        val goToCamera = Intent(this, CameraActivity::class.java)
        startActivityForResult(goToCamera, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.getStringExtra("imageUri")
            findViewById<TextView>(R.id.testOutputCall).text = imageUri
            val aux = findViewById<ImageView>(R.id.testView)
            aux.setImageURI(Uri.parse(imageUri))
        }
    }
}
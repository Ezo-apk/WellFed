package com.example.wellfed

import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wellfed.databinding.ActivityCameraBinding
import android.Manifest
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {

//    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        outputDirectory = getOutputDirectory()

        if (allPermissionsGranted()) {
                startCamera()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
            }

        binding.takePhotoBtn.setOnClickListener {
            takePhoto()
//            val goToHome = Intent(this, MainActivity::class.java)
//            startActivity(goToHome)
//            finish()
        }
    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let{mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(outputDirectory,
                             SimpleDateFormat("yy-MM-dd-HH-mm-ss",
                                              Locale.getDefault()).format(System.currentTimeMillis()) + ".jpg")
        val outputOption =ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputOption,
                                 ContextCompat.getMainExecutor(this),
                                 object: ImageCapture.OnImageSavedCallback {
                                     override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                         val savedUri = Uri.fromFile(photoFile)
                                         Toast.makeText(this@CameraActivity, "$savedUri", Toast.LENGTH_SHORT).show()
                                     }

                                     override fun onError(exception: ImageCaptureException) {
                                         Log.e("Camera", "onError: ${exception.message}", exception)
                                     }

                                 })
    }

    private fun startCamera() {
        val camera = ProcessCameraProvider.getInstance(this)
        val cameraProvider: ProcessCameraProvider = camera.get()
        camera.addListener({
            val preview = Preview.Builder().build().also { mPreview->
                mPreview.setSurfaceProvider(binding.cameraView.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {

            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 123) {
            if(allPermissionsGranted()) {

            } else {

            }
        }
    }

    private fun allPermissionsGranted() =
        arrayOf(Manifest.permission.CAMERA).all {
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
        }

}
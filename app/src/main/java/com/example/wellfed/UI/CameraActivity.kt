package com.example.wellfed.UI

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.wellfed.databinding.ActivityCameraBinding
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import com.example.wellfed.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var photoFile: File
    private lateinit var savedUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        outputDirectory = getOutputDirectory()

        photoFile = getPhotoFile("testPhoto.jpg")

        if (allPermissionsGranted()) {
                startCamera()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
            }

        binding.takePhotoBtn.setOnClickListener {
            takePhoto()
        }

    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let{ mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }


    private fun takePhoto() {
//        savedUri = Uri.EMPTY
        val imageCapture = imageCapture ?: return
        val photoFile = File(outputDirectory,
                             SimpleDateFormat("yy-MM-dd-HH-mm-ss",
                                              Locale.getDefault()).format(System.currentTimeMillis()) + ".jpg")
        val outputOption =ImageCapture.OutputFileOptions.Builder(photoFile).build()
        val goToMain = Intent(this, MainActivity::class.java)

        imageCapture.takePicture(outputOption,
                                 ContextCompat.getMainExecutor(this),
                                 object: ImageCapture.OnImageSavedCallback {
                                     override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                         savedUri = Uri.fromFile(photoFile)
                                         Toast.makeText(this@CameraActivity, "$savedUri", Toast.LENGTH_LONG).show()
//                                         goToMain.putExtra("Uri", savedUri.toString())
                                     }

                                     override fun onError(exception: ImageCaptureException) {
                                         Log.e("Camera", "onError: ${exception.message}", exception)
                                         return
                                     }

                                 })
        savedUri = Uri.fromFile(photoFile)
        goToMain.putExtra("Uri", savedUri.toString())
        Thread.sleep(2_000)
        startActivity(goToMain)
        finish()
        if(isFinishing) {
            return
        }
    }

    private fun getPhotoFile(fileName :String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
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
            } catch (_: Exception) {

            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

//        if(requestCode == 123) {
//            if(allPermissionsGranted()) {
//
//            } else {
//
//            }
//        }
    }

    private fun allPermissionsGranted() =
        arrayOf(Manifest.permission.CAMERA).all {
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
        }

    override fun onBackPressed() {
        val goToMain = Intent(this, MainActivity::class.java)
        startActivity(goToMain)
        finish()
    }

}
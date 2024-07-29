package com.example.a20mis0158

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.lifecycle.ViewModelProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    private val cameraViewModel: CameraViewModel by viewModels()
    private lateinit var previewView: PreviewView
    private lateinit var photoViewModel: PhotoViewModel

    private fun handlePermissionsGranted() {
        // Initialize the camera
        cameraViewModel.startCamera(this, previewView)

        // Optional: Show a toast or notification to the user that permissions are granted
        Toast.makeText(this, "Permissions granted, you can now use the camera and save files.", Toast.LENGTH_SHORT).show()

        // If you have a function to handle file creation or other operations, you can call it here
        // For example, creating a new file
        createFile(this)
    }

    private fun handlePermissionsDenied() {
        // Notify the user that permissions are needed
        Toast.makeText(this, "Permissions denied. The app needs camera and storage permissions to function correctly.", Toast.LENGTH_LONG).show()

        // Optionally, you can guide the user to the app settings to manually enable permissions
        // Provide a button or link to open app settings
        showPermissionSettingsDialog()
    }

    private fun showPermissionSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permissions Required")
            .setMessage("This app needs camera and storage permissions to work properly. Please enable them in the app settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                // Open app settings
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent) // Corrected function call
            }
            .setNegativeButton("Cancel", null)
            .show()
    }



    // Permission request launcher
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                handlePermissionsGranted()
            } else {
                handlePermissionsDenied()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previewView = findViewById(R.id.preview_view)
        val takePhotoButton: ImageButton = findViewById(R.id.take_photo_button)

        // Initialize PhotoViewModel
        val photoDao = AppDatabaseProvider.getDatabase(this).photoDao()
        photoViewModel = ViewModelProvider(this, PhotoViewModelFactory(photoDao)).get(PhotoViewModel::class.java)

        cameraViewModel.startCamera(this, previewView)

        // Request permissions
        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        takePhotoButton.setOnClickListener {
            val outputDirectory = getOutputDirectory()
            cameraViewModel.takePhoto(outputDirectory, this)
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return mediaDir ?: filesDir
    }

    private fun createFile(context: Context): Uri? {
        // Create the values for the file you want to create
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "my_image.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        // Create the file in MediaStore
        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }
}

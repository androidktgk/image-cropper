package com.example.imagecropper

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.model.AspectRatio
import java.io.File

class ImageCropperActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private val UCROP_REQUEST_CODE = 2

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_cropper)

        imageView = findViewById(R.id.imageView)

        // Start image picker
        pickImage()
    }

    // Open gallery to select an image
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Handle the result of picking an image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data

            if (selectedImageUri != null) {
                startCrop(selectedImageUri)
            }
        }

        if (requestCode == UCROP_REQUEST_CODE) {
            val resultUri = UCrop.getOutput(data!!)
            if (resultUri != null) {
                imageView.setImageURI(resultUri) // Show cropped image in ImageView
            }
        }
    }

    // Start UCrop activity to crop the image
    private fun startCrop(uri: android.net.Uri) {
        val destinationUri = android.net.Uri.fromFile(File(cacheDir, "sample_crop.jpg"))

        UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f) // You can adjust the aspect ratio as needed
            .withMaxResultSize(800, 800)
            .start(this)
    }
}

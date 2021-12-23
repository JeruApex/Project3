package com.example.project3

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

//
//import android.app.Activity
//import android.content.ActivityNotFoundException
//import android.content.Intent
//import android.graphics.Bitmap
//import android.hardware.camera2.CameraAccessException
//import android.hardware.camera2.CameraCharacteristics
//import android.hardware.camera2.CameraManager
//import android.net.Uri
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.os.Environment
//import android.provider.MediaStore
//import android.util.Log
//import android.util.SparseIntArray
//import android.view.Surface
//import android.widget.Button
//import android.widget.ImageView
//import androidx.annotation.RequiresApi
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.ImageProxy
//import androidx.core.content.FileProvider
//import com.google.mlkit.vision.common.InputImage
//import com.google.mlkit.vision.face.FaceDetection
//import com.google.mlkit.vision.face.FaceDetectorOptions
//import com.google.mlkit.vision.text.TextRecognition
//import com.google.mlkit.vision.text.latin.TextRecognizerOptions
//import java.io.File
//import java.io.IOException
//import java.text.SimpleDateFormat
//import java.util.*


class MainActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Build a button that launches a camera app
        findViewById<Button>(R.id.button).setOnClickListener {
            //TODO Launch Camera App

            //Create an intent that launches the camera and takes a picture
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

//            Grab Bitmap from image that was taken in camera
            val imageBitmap = data?.extras?.get("data") as Bitmap

//            Set bitmap as imageview image
            findViewById<ImageView>(R.id.imageView).setImageBitmap(imageBitmap)

//            Prepare bitmap
            val imageForMlKit = InputImage.fromBitmap(imageBitmap, 0)

//            Utilize image labeling API
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

            labeler.process(imageForMlKit)
                .addOnSuccessListener { labels ->
                    Log.i("Jason", "Successfully processed image")

                    for (label in labels) {
//                        What was detected in the image
                        val text = label.text
                        val confidence = label.confidence
                        Log.i("Jason", "detected:" + text + "with confidence:" + confidence)
                    }
                }
                .addOnFailureListener { e ->
                    Log.i("Jason", "Error processing image")
                }

        }
    }
}
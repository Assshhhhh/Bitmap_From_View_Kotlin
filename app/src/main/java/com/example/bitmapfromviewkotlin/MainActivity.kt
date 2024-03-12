package com.example.bitmapfromviewkotlin

import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.bitmapfromviewkotlin.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageUri = createImageUri()

        binding.takeSsButton.setOnClickListener {
            val bitmap = getBitmapFromView(binding.main)
            storeBitmap(bitmap)
            it.isVisible = false
        }

    }

    private fun storeBitmap(bitmap: Bitmap) {
        val outputStream = applicationContext.contentResolver.openOutputStream(imageUri)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream!!)
        outputStream.close()
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bg = view.background
        bg.draw(canvas)
        view.draw(canvas)
        return bitmap
    }

    private fun createImageUri(): Uri{
        val image = File(applicationContext.filesDir, "camera_photos.png")
        return FileProvider.getUriForFile(applicationContext, "com.example.bitmapfromviewkotlin.FileProvider", image)
    }

}
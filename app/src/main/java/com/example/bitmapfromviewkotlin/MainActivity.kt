package com.example.bitmapfromviewkotlin

import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.bitmapfromviewkotlin.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

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

       binding.takeSsButton.setOnClickListener {
           saveImage()
       }


        /*imageUri = createImageUri()
        binding.takeSsButton.setOnClickListener {
            val bitmap = getBitmapFromView(binding.main)
            storeBitmap(bitmap)
            it.isVisible = false
        }*/

    }

    private fun saveImage() {
        binding.main.isDrawingCacheEnabled = true
        binding.main.buildDrawingCache()
        binding.main.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        val bitmap = binding.main.drawingCache
        saveBitmap(bitmap)

    }

    private fun saveBitmap(bitmap: Bitmap?) {
        val root: String = Environment.getExternalStorageDirectory().absolutePath
        val file: File = File(root + "/Download")
        val file_name: String = "view_image.png"
        val myFile: File = File(file, file_name)

        if (myFile.exists()){
            myFile.delete()
        }

        try {

            val fileOutputStream: FileOutputStream = FileOutputStream(myFile)
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()

            binding.main.isDrawingCacheEnabled = false

        } catch (e: Exception){
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    /*private fun storeBitmap(bitmap: Bitmap) {
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
    }*/

}
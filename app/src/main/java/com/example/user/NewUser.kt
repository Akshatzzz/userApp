package com.example.user

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import java.io.IOException

class NewUser : AppCompatActivity() {
    val PICK_IMAGE = 1
    lateinit var myViewModel:viewModel

    lateinit var imageToBeLoaded: ImageView
    lateinit var saveButton: Button
    var imageUri: Uri? = null
    lateinit var bitmap: Bitmap
    lateinit var editTextName: EditText
    lateinit var editTextPhone: EditText
    lateinit var editTextEmail: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        myViewModel= viewModel(this)
        imageToBeLoaded=findViewById(R.id.imageToBeAdded)
        saveButton = findViewById(R.id.button)
        editTextName = findViewById(R.id.etName)
        editTextEmail = findViewById(R.id.etEmail)
        editTextPhone = findViewById(R.id.etPhone)

        imageToBeLoaded.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.setType("image/*")
                .action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(galleryIntent,"Pick Image"),PICK_IMAGE)
        }
        saveButton.setOnClickListener {
            val userFace =
                User(0,
                    editTextName.text.toString(),
                    editTextEmail.text.toString(),
                    editTextPhone.text.toString(),
                    bitmap)

            myViewModel.insertUser(userFace)

            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE && resultCode== RESULT_OK){
            data!!.data.also { imageUri = it }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                imageToBeLoaded.setImageBitmap(bitmap)
            }
            catch (e: IOException){
                e.printStackTrace()
            }
        }
        else if(resultCode== RESULT_CANCELED){
            Toast.makeText(applicationContext,"Cancelled",Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
    }
}
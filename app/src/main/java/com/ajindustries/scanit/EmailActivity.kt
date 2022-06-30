package com.ajindustries.scanit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.Exception

class EmailActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var edtxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        button= findViewById(R.id.btn)
        edtxt = findViewById(R.id.edtxt)

        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:")
            intent.type= "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(edtxt.text.toString()))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Registration to the Central DataBase")
            intent.putExtra(Intent.EXTRA_TEXT,"you need to register using this mail")
            try {
                startActivity(Intent.createChooser(intent,"choose your app"))
            }
            catch (e: Exception){
                Toast.makeText(this@EmailActivity, e.message, Toast.LENGTH_SHORT).show()
            }

        }

    }
}
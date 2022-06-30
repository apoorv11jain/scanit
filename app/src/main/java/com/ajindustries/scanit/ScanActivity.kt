package com.ajindustries.scanit


import android.content.Context
import android.content.Entity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Database
import androidx.room.Room
import com.budiyev.android.codescanner.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_scan.*

private const val CAMERA_REQUEST_CODE =101
var rollnum = "ATTENDENCE "

class ScanActivity : AppCompatActivity() {
    lateinit var button: Button
    private lateinit var codeScanner: CodeScanner
    private lateinit var scanner_view: CodeScannerView
    private lateinit var tv_textView: TextView
    lateinit var buttonsave : Button
    lateinit var edtxt : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        button = findViewById(R.id.button)
        tv_textView  = findViewById(R.id.tv_textView)
        scanner_view = findViewById(R.id.scanner_view)
        buttonsave = findViewById(R.id.buttonsave)
        edtxt = findViewById(R.id.editroll)



        button.setOnClickListener {

            val main = Intent(this@ScanActivity, ViewActivity::class.java)
            startActivity(main)

        }
        buttonsave.setOnClickListener {
            send_data(edtxt.text.toString())
           // Toast.makeText(this@ScanActivity,edtxt.text.toString(), Toast.LENGTH_SHORT).show()
        }

        setupPermissions()
        codeScanner()
    }



    class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
            AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {

            when (mode) {
                1 -> {
                    val book: BookEntity? =
                            db.bookDao().getBookById(bookEntity.enrollno)
                    db.close()
                    return book != null
                }

                2 -> {
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }


            }


            return false
        }

    }


    class DBA1syncTask(val context: Context, val rollEntity: RollEntity, val mode: Int) :
            AsyncTask<Void, Void, Boolean>() {

        val db1 = Room.databaseBuilder(context, BookDatabase::class.java, "rolls-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {

            when (mode) {
                1 -> {
                    val roll: RollEntity? =
                            db1.rollDao().getRollById(rollEntity.enrollno)
                    db1.close()
                    return roll != null
                }

                /*2 -> {
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }*/


            }


            return false
        }

    }


    private fun codeScanner() {
        codeScanner= CodeScanner(this, scanner_view)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode= AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false
            decodeCallback= DecodeCallback {
                //rollnum =it.text.toString()
                send_data(it.text.toString())
                runOnUiThread{

                    tv_textView.text=it.text
                    // send_data(no)
                    // send_data(it.text.toString())


                  //  Toast.makeText(this@MainActivity,it.text, Toast.LENGTH_LONG).show()

                }
            }

            errorCallback= ErrorCallback {
                runOnUiThread{
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }

        // return no
    }

    fun send_data( data: String ) {
        var bookEntity = BookEntity(data)
        val checkFav = DBAsyncTask(applicationContext, bookEntity, 1).execute()
        val isFav = checkFav.get()
        var rollEntity = RollEntity(data)
        if (DBA1syncTask(applicationContext,rollEntity, 1).execute().get()) {
            if (!DBAsyncTask(applicationContext, bookEntity, 1).execute().get()) {
                val async = DBAsyncTask(applicationContext, bookEntity, 2).execute()
                val result = async.get()
            }
        }
        else
        {
            // ADD THE REGISTRY PAGE HERE
           // val main = Intent(this@ScanActivity, ViewActivity::class.java)
           // startActivity(main)
            val intent = Intent(this@ScanActivity,EmailActivity::class.java)
            startActivity(intent)
          //  Toast.makeText(this@ScanActivity, "the user is not registered to the data base",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
    private fun setupPermissions(){
        val permission= ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        if (permission!= PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            this,
                            "You need the camera permission to be able to use",
                            Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //sucess
                }
            }
        }
    }
}




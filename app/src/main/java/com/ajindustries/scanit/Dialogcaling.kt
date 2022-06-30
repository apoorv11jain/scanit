package com.ajindustries.scanit
//this is the page where the data will be send to the central data base
// you still need to add api and check whether it works or not
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Dialogcaling : AppCompatActivity() {

 var event_name: String?=""
    var dbBookList = listOf<BookEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogcaling)
        if (intent != null)
        {
            event_name = intent.getStringExtra("event name")

        }
   //     var dialog = CustomDialogFragment()
      //  dialog.show(supportFragmentManager,"custom")
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialogbox,null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.setPositiveButton("continue",{ dialogInterface: DialogInterface, i: Int -> })
        dialog.setNegativeButton("cancel",{ dialogInterface: DialogInterface, i: Int -> })
        val customDialog = dialog.create()
        customDialog.show()
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

            dbBookList = ViewActivity.RetriveData(this@Dialogcaling as Context).execute().get()

            val queue = Volley.newRequestQueue(this@Dialogcaling)
            val url = "the api"
            val jsonParams = JSONObject()
            jsonParams.put("event", event_name)
            jsonParams.put("rollnumbers",  dbBookList)
            println("the rollnumbers are $dbBookList")
            println("the json params are roll $jsonParams")
            val jsonRequest = object :JsonObjectRequest(Request.Method.POST, url, jsonParams,Response.Listener {

            },Response.ErrorListener {

            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Content-type"]= "application/json"
                    return headers
                }
            }

            Toast.makeText(this@Dialogcaling,"submitted",Toast.LENGTH_SHORT).show()
            customDialog.dismiss()
        }
        customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            customDialog.dismiss()
        }



    }

    class RetriveData(val context: Context): AsyncTask<Void, Void, List<BookEntity>>(){
        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context,BookDatabase::class.java, "books-db").build()

            return db.bookDao().getAllBooks()
        }

    }

}
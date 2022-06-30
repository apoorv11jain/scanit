package com.ajindustries.scanit

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley





class ProfileFragment : Fragment() {

    lateinit var progressLayout : RelativeLayout
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var roll = arrayListOf("123456789012","1234567890","08615002719")
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        progressBar = view.findViewById(R.id.progressbar)
        progressLayout= view.findViewById(R.id.progresslayout)
        progressLayout.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://www.json-generator.com/api/json/get/cpyGVvzehu?indent=2"
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
            println("Response is $it")

            val data = it.getJSONArray("roll")
            println("data is $data")
            for (i in 0 until data.length()) {
                val bookJsonObject = data.getJSONObject(i)
                roll.add(bookJsonObject.getString("rollnumber"))
            }
                for(i in roll) {
                    println("i is $i")
                    var rollEntity = RollEntity(i)
                    if (!DBAsyncTask(activity as Context, rollEntity, 1).execute().get()) {
                        val async = DBAsyncTask(activity as Context, rollEntity, 2).execute()
                        val result = async.get()
                    }
                }

            progressLayout.visibility = View.GONE
            println("roll is $roll")
        },Response.ErrorListener{
            println("error is $it")
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers= HashMap<String,String>()
                headers["Content-type"] = "application/json"

                return headers
            }

        }









        queue.add(jsonObjectRequest)



       //
       // progressBar.visibility = View.GONE
        return view
    }
    class DBAsyncTask(val context: Context, val rollEntity: RollEntity, val mode: Int) :
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

                2 -> {
                    db1.rollDao().insertBook(rollEntity)
                    db1.close()
                    return true
                }


            }


            return false
        }
    }
}
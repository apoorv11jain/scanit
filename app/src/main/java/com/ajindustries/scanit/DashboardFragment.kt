package com.ajindustries.scanit

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.lang.reflect.Array


class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard : RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    var eventlist = arrayListOf<String>()
  //  lateinit var newevent = arrayListOf<String>()
    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard1)
        layoutManager = LinearLayoutManager(activity)



        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://www.json-generator.com/api/json/get/cbuoussgaG?indent=2"
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
           println("Response is $it")

            val data = it.getJSONArray("data")
            println("data is $data")
            for (i in 0 until data.length()){
                val bookJsonObject = data.getJSONObject(i)
                eventlist.add(bookJsonObject.getString("event"))
            }


            recyclerAdapter = DashboardRecyclerAdapter(activity as Context, eventlist)
            println("eventlist is $eventlist")
            recyclerDashboard.adapter = recyclerAdapter
            recyclerDashboard.layoutManager = layoutManager
            recyclerDashboard.addItemDecoration(
                    DividerItemDecoration(
                            recyclerDashboard.context,
                            (layoutManager as LinearLayoutManager).orientation
                    )
            )


                println("the event list data is $eventlist")
        },Response.ErrorListener{
        println("error is $it")
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers= HashMap<String,String>()
                headers["Content-type"] = "application/json"

                return headers
            }

        }

        println("eventlist in middle$eventlist")







        queue.add(jsonObjectRequest)





        return view
    }

}
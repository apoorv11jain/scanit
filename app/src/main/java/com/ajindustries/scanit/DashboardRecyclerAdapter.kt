package com.ajindustries.scanit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<String>) :RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row, parent,false)

        return DashboardViewHolder(view)
    }
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
       val prakEvent = itemList[position]
        holder.textView.text = prakEvent
        holder.llContent.setOnClickListener{
            val intent = Intent(context,Dialogcaling::class.java)
            intent.putExtra("event name",prakEvent)
            context.startActivity(intent)

           // Toast.makeText(context, "click worked",Toast.LENGTH_SHORT).show()
        }
    }
    class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view){
        val textView :TextView = view.findViewById(R.id.txtRecycleRowItem)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }
}
package com.ajindustries.scanit


import android.content.Intent
import android.net.Uri
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import java.lang.Exception

class AboutFragment : Fragment() {
lateinit var button: Button
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view =inflater.inflate(R.layout.fragment_about, container, false)

        button = view.findViewById(R.id.btn_contact)

        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:")
            intent.type= "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("ajjustcode@gmail.com"))
           try {
               startActivity(Intent.createChooser(intent,"choose your app"))
           }
           catch (e: Exception){
               Toast.makeText(activity, e.message,Toast.LENGTH_SHORT).show()
           }

        }
        return view
    }


}
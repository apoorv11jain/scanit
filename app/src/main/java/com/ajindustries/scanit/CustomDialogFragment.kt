package com.ajindustries.scanit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class CustomDialogFragment : DialogFragment() {
lateinit var cancelButton: Button
    lateinit var submitButton: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_custom_dialog,container,false)
        cancelButton= view.findViewById(R.id.cancel_button)
        submitButton = view.findViewById(R.id.submit_button)


        cancelButton.setOnClickListener{
         dismiss()
        }

        submitButton.setOnClickListener {

            // all things related to sending the post request
            Toast.makeText(activity,"sending data to the server",Toast.LENGTH_SHORT).show()
            dismiss()
        }

        return view
    }

}
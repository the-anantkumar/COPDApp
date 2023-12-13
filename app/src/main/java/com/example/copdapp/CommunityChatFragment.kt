package com.example.copdapp

//code for the community chat fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class CommunityChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //toast for testing
        //Toast.makeText(context, "CommunityChatFragment", Toast.LENGTH_SHORT).show()
        val view = inflater.inflate(R.layout.fragment_community_chat, container, false)

        return view

        //send message button
        val sendMessageButton = view.findViewById<Button>(R.id.send)
        sendMessageButton.setOnClickListener {
            Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show()
        }

    }
}
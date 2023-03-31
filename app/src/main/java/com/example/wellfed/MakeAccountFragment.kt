package com.example.wellfed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


class MakeAccountFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_make_account, container, false)
        val backbtn = view.findViewById<Button>(R.id.backToLoginbtn)
        val fwrdbtn = view.findViewById<Button>(R.id.goToMainbtn)

        backbtn.setOnClickListener {
            findNavController().navigate(R.id.action_makeAccountFragment_to_loginFragment)
        }
//        fwrdbtn.setOnClickListener {
//            findNavController().navigate(R.id.action_makeAccountFragment_to_mainActivity)
//        }

        return view
    }

}
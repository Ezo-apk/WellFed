package com.example.wellfed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController


class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        val loginbtn = view.findViewById<Button>(R.id.loginbtn)
        val signupbtn = view.findViewById<Button>(R.id.signupbtn)
        val forgotpass = view.findViewById<TextView>(R.id.forgotPassText)


        signupbtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_makeAccountFragment)
        }

        forgotpass.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        loginbtn.setOnClickListener {

        }

        return view
    }
}
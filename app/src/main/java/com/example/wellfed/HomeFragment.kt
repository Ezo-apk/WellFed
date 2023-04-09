package com.example.wellfed

import android.app.Activity
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wellfed.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var dataAdapter: DataAdapter
    private lateinit var recyclerView: RecyclerView
    private var postList : MutableList<DataModel> = mutableListOf()
    private var parentActivity : FragmentActivity ?= activity



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentHomeBinding.inflate(layoutInflater)
//
//        loadData()
//        recyclerView = view.findViewById(R.id.feedView)
//        recyclerView.layoutManager = LinearLayoutManager(view.context)
//        dataAdapter = DataAdapter(postList)
//        recyclerView.adapter = dataAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)


        recyclerView = binding.feedView
        recyclerView.layoutManager = LinearLayoutManager(context)
        dataAdapter = DataAdapter(postList)
        recyclerView.adapter = dataAdapter

        dataAdapter.setOnItemClickListener(object : DataAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                openWholePost()
            }

        })

        return binding.root
    }


    private fun openWholePost() {

    }
    private fun loadData() {
        postList.add(DataModel(R.drawable.cat1, "Cat", ""))
        postList.add(DataModel(R.drawable.cat2, "Cat, but high res", ""))
        postList.add(DataModel(R.drawable.salad, "Toss me", ""))
        postList.add(DataModel(R.drawable.dog, "Dog", ""))
    }

}
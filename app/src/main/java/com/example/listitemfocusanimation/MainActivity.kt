package com.example.listitemfocusanimation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listAdapter: ItemListAdapter

    val topics = listOf("Education","Finance","Government","Entertainment","Technology","Math","Biology","Physics","Chemistry","Space","Sports","Music","Animal","Countries","Weather","Politics","Traffic","Poverty","Social Media","Internet","Housing")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        listAdapter = ItemListAdapter(topics)

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = listAdapter
    }

}

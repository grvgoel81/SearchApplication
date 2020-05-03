package com.example.searchapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchapplication.R
import com.example.searchapplication.model.Data
import com.example.searchapplication.ui.adapter.DataAdapter
import com.example.searchapplication.utils.loadJSONFromAssets
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    lateinit var dataList: ArrayList<Data>
    lateinit var dataAdapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val mLayoutManager = LinearLayoutManager(this@MainActivity)

        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        recyclerView.isNestedScrollingEnabled = false

        if (savedInstanceState == null) run {
            bindDataToDataList()
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                dataAdapter!!.filter.filter(editable)
            }
        })

    }

    private fun setupRecyclerView(results: List<Data>?) {

        if (results != null) {
            dataAdapter = DataAdapter(results, this@MainActivity)
            recyclerView.adapter = dataAdapter
            dataAdapter?.notifyDataSetChanged()
        }
    }

    private fun bindDataToDataList() {
        dataList = ArrayList<Data>()
        val loadJSON = loadJSONFromAssets("search.json")
        val dataJsonArray = JSONArray(loadJSON)
        for (i in 0 until dataJsonArray.length()) {
            val dataModel = Data()
            val dataJsonObject = dataJsonArray.getJSONObject(i)
            dataModel.title = dataJsonObject.optString("title")
            dataModel.description = dataJsonObject.optString("description")
            dataModel.price = dataJsonObject.optInt("price")
            dataList.add(dataModel)
        }
        setupRecyclerView(dataList)
    }
}

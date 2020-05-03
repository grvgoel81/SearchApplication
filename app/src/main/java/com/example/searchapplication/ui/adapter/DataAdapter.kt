package com.example.searchapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.searchapplication.R
import com.example.searchapplication.model.Data

class DataAdapter(private val Data: List<Data>, private val context: Context) :
    RecyclerView.Adapter<DataViewHolder>(),
    Filterable {

    private var dataSearchList: List<Data>? = null

    init {
        this.dataSearchList = Data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return DataViewHolder(view)
    }


    override fun getItemCount(): Int = dataSearchList!!.size


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        var data = dataSearchList!![position]
        holder.updateView(data)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                dataSearchList = if (charString.isEmpty()) {
                    Data
                } else {
                    val filteredList = ArrayList<Data>()
                    for (data in Data) {
                        if (data.title!!.toUpperCase().startsWith(charString.toUpperCase())) {
                            filteredList.add(data)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = dataSearchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                dataSearchList = filterResults.values as ArrayList<Data>
                notifyDataSetChanged()
            }
        }
    }
}

class DataViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {

    private var tvTitle = itemVIew.findViewById<TextView>(R.id.tvTitle)
    private var tvDescription = itemVIew.findViewById<TextView>(R.id.tvDescription)
    private var tvPrice = itemVIew.findViewById<TextView>(R.id.tvPrice)

    fun updateView(data: Data) {
        tvTitle.text = data.title
        tvDescription.text = data.description
        tvPrice.text = "Rs " + data.price.toString()
    }

}
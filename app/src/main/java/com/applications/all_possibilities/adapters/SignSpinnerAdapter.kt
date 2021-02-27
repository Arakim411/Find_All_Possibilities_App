package com.applications.all_possibilities.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.applications.all_possibilities.R
import kotlinx.android.synthetic.main.sign_spinner_item.view.*

class SignSpinnerAdapter(private  val items: ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        // there is no need to use viewHolder for four items
        val view = LayoutInflater.from(p2?.context).inflate(R.layout.sign_spinner_item, p2,false)
        view.spinnerText.text = items[p0]

        return view
    }


}
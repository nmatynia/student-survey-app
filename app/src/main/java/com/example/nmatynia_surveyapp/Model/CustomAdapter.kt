package com.example.nmatynia_surveyapp.Model

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.nmatynia_surveyapp.R
import android.view.LayoutInflater;
import android.widget.TextView

class CustomAdapter(
    private val appContext: Context, private val surveyNameList:Array<String>,
    private val surveyStartDateList:Array<String>,
    private val surveyEndDateList: Array<String>): BaseAdapter() {

    private val inflater:LayoutInflater
        = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return surveyNameList.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        view = inflater.inflate(R.layout.published_survey_item, parent, false)

        val name = view.findViewById<TextView>(R.id.surveyName)
        val startDate = view.findViewById<TextView>(R.id.surveyStartDate)
        val endDate = view.findViewById<TextView>(R.id.surveyEndDate)

        name.text = surveyNameList[position]
        startDate.text = surveyStartDateList[position]
        endDate.text = surveyEndDateList[position]

        return view
    }


}
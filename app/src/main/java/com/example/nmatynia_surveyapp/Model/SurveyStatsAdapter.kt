package com.example.nmatynia_surveyapp.Model

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.nmatynia_surveyapp.R
import android.view.LayoutInflater;
import android.widget.TextView

class SurveyStatsAdapter(
    private val appContext: Context,
    private val questionContentList:Array<String>,
    private val resSDPrecentageList:Array<String>,
    private val resDPrecentageList:Array<String>,
    private val resNorPrecentageList:Array<String>,
    private val resAPrecentageList:Array<String>,
    private val resSAPrecentageList:Array<String>): BaseAdapter() {

    private val inflater:LayoutInflater
            = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return questionContentList.size
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

        val questionContent = view.findViewById<TextView>(R.id.questionContent)
        val stronglyAgree = view.findViewById<TextView>(R.id.resSA)
        val agree = view.findViewById<TextView>(R.id.resA)
        val nor = view.findViewById<TextView>(R.id.resNor)
        val disagree = view.findViewById<TextView>(R.id.resD)
        val stronglyDisagree = view.findViewById<TextView>(R.id.resSD)

        questionContent.text = questionContentList[position]
        stronglyAgree.text = resSAPrecentageList[position]
        agree.text = resAPrecentageList[position]
        nor.text = resNorPrecentageList[position]
        disagree.text = resDPrecentageList[position]
        stronglyDisagree.text = resSDPrecentageList[position]

        return view
    }

}
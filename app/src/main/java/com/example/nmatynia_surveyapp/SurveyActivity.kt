package com.example.nmatynia_surveyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.nmatynia_surveyapp.Model.SurveyDataBase

class SurveyActivity : AppCompatActivity() {
    var simpleList: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        val db = SurveyDataBase(this)

        var surveyList =  db.getAllSurveys()
        var surveyTitles = surveyList.map{it.Title}
        simpleList = findViewById(R.id.surveyListView)
        val arrayAdapter =
            ArrayAdapter(this,R.layout.activity_survey_list_view,R.id.surveyListItem,surveyTitles )
        if(surveyList.isEmpty()){
            findViewById<TextView>(R.id.noSurveys).isVisible = true
        }
        simpleList?.adapter = arrayAdapter
    }
}
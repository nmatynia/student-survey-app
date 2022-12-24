package com.example.nmatynia_surveyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.nmatynia_surveyapp.Model.CustomAdapter
import com.example.nmatynia_surveyapp.Model.SurveyDataBase

class AdminSurveyActivity : AppCompatActivity() {
    lateinit var publishedSurveyList: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_survey)

        publishedSurveyList = findViewById<ListView>(R.id.publishedSurveysList)

        val db = SurveyDataBase(this)

        val publishedSurveys = db.getAllPublishedSurveys()

        val surveyNames = publishedSurveys.map{db.getSurvey(it.SurveyId).Title}.toTypedArray()
        val surveyStartDates = publishedSurveys.map{it.StartDate}.toTypedArray()
        val surveyEndDates = publishedSurveys.map{it.EndDate}.toTypedArray()

        val adapter = CustomAdapter(applicationContext, surveyNames, surveyStartDates, surveyEndDates)

        publishedSurveyList!!.adapter = adapter
    }
}
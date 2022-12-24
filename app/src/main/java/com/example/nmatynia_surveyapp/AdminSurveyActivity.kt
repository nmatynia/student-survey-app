package com.example.nmatynia_surveyapp

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
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

        publishedSurveyList?.setOnItemClickListener { parent, view, position, id ->
            val publishedSurveyId = publishedSurveys[position].Id
            val surveyId = publishedSurveys[position].SurveyId
            val surveyTitle = db.getSurvey(surveyId).Title
            val surveyStartDate = publishedSurveys[position].EndDate
            val surveyEndDate = publishedSurveys[position].StartDate
            val intent = Intent(baseContext, EditSurveyActivity::class.java)

            intent.putExtra("ID", publishedSurveyId)
            intent.putExtra("SURVEY_ID", surveyId)
            intent.putExtra("TITLE", surveyTitle)
            intent.putExtra("START_DATE", surveyStartDate)
            intent.putExtra("END_DATE", surveyEndDate)
            startActivity(intent)
        }
        val surveyNames = publishedSurveys.map{db.getSurvey(it.SurveyId).Title}.toTypedArray()
        val surveyStartDates = publishedSurveys.map{it.StartDate}.toTypedArray()
        val surveyEndDates = publishedSurveys.map{it.EndDate}.toTypedArray()

        val adapter = CustomAdapter(applicationContext, surveyNames, surveyStartDates, surveyEndDates)

        publishedSurveyList!!.adapter = adapter
    }
}
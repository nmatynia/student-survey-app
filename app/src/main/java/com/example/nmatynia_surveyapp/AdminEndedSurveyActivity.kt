package com.example.nmatynia_surveyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.nmatynia_surveyapp.Model.CustomAdapter
import com.example.nmatynia_surveyapp.Model.SurveyDataBase
import java.text.SimpleDateFormat
import java.util.*


class AdminEndedSurveyActivity : AppCompatActivity() {
    lateinit var publishedSurveyList: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_ended_survey)

        publishedSurveyList = findViewById<ListView>(R.id.publishedSurveysList)

        val db = SurveyDataBase(this)
        val today = Calendar.getInstance().time

        val publishedSurveys = db.getAllPublishedSurveys()

        val filteredSurveys = publishedSurveys.filter { survey ->
            val surveyDate = SimpleDateFormat("dd/MM/yyyy").parse(survey.EndDate)
            surveyDate.before(today)
        }

        publishedSurveyList?.setOnItemClickListener { parent, view, position, id ->
            val publishedSurveyId = filteredSurveys[position].Id
            val surveyId = filteredSurveys[position].SurveyId
            val surveyTitle = db.getSurvey(surveyId).Title
            val surveyStartDate = filteredSurveys[position].StartDate
            val surveyEndDate = filteredSurveys[position].EndDate
            val intent = Intent(baseContext, EditSurveyActivity::class.java)

            intent.putExtra("ID", publishedSurveyId)
            intent.putExtra("SURVEY_ID", surveyId)
            intent.putExtra("TITLE", surveyTitle)
            intent.putExtra("START_DATE", surveyStartDate)
            intent.putExtra("END_DATE", surveyEndDate)
            startActivity(intent)
        }
        val surveyNames = filteredSurveys.map{db.getSurvey(it.SurveyId).Title}.toTypedArray()
        val surveyStartDates = filteredSurveys.map{it.StartDate}.toTypedArray()
        val surveyEndDates = filteredSurveys.map{it.EndDate}.toTypedArray()

        val adapter = CustomAdapter(applicationContext, surveyNames, surveyStartDates, surveyEndDates)

        publishedSurveyList!!.adapter = adapter
    }

    fun goBack(view: View){
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }


}
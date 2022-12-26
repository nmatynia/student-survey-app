package com.example.nmatynia_surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.nmatynia_surveyapp.Model.CustomAdapter
import com.example.nmatynia_surveyapp.Model.SurveyDataBase
import java.text.SimpleDateFormat
import java.util.*

class SurveyActivity : AppCompatActivity() {
    lateinit var publishedSurveyList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        publishedSurveyList = findViewById<ListView>(R.id.publishedSurveysList)

        val db = SurveyDataBase(this)
        val today = Calendar.getInstance().time

        val publishedSurveys = db.getAllPublishedSurveys()

        val filteredSurveys = publishedSurveys.filter { survey ->
            val surveyDate = SimpleDateFormat("dd/MM/yyyy").parse(survey.EndDate)
            Log.d("marcin",surveyDate.toString())
            surveyDate.after(today)
        }

        publishedSurveyList?.setOnItemClickListener { parent, view, position, id ->
            val publishedSurveyId = filteredSurveys[position].Id
            val surveyId = filteredSurveys[position].SurveyId
            val surveyTitle = db.getSurvey(surveyId).Title
            val surveyStartDate = filteredSurveys[position].StartDate
            val surveyEndDate = filteredSurveys[position].EndDate
            val intent = Intent(baseContext, QuestionsActivity::class.java)

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

    fun logout(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
package com.example.nmatynia_surveyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class SurveyActivity : AppCompatActivity() {
    var simpleList: ListView? = null
    var moduleList = arrayOf("Something here", "Something there", "Something there as well")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        simpleList = findViewById(R.id.surveyListView)
        val arrayAdapter =
            ArrayAdapter(this,R.layout.activity_survey_list_view,R.id.surveyListItem, moduleList)
        simpleList?.adapter = arrayAdapter
    }
}
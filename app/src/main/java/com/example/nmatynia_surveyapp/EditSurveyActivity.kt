package com.example.nmatynia_surveyapp

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditSurveyActivity : AppCompatActivity() {
    var surveyId: Int? = null;
    var publishedSurveyId: String? = null
    var surveyTitle: String? = null
    var surveyStartDate: String? = null
    var surveyEndDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_survey)
        var surveyId = intent.getStringExtra("ID")
        var publishedSurveyId = intent.getStringExtra("SURVEY_ID")
        var surveyTitle = intent.getStringExtra("TITLE")
        var surveyStartDate = intent.getStringExtra("START_DATE")
        var surveyEndDate = intent.getStringExtra("END_DATE")

        val editTextSurveyName = findViewById<EditText>(R.id.editTextSurveyName) as EditText
        editTextSurveyName.setText(surveyTitle)

    }
}
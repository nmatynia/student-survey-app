package com.example.nmatynia_surveyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.nmatynia_surveyapp.Model.PublishedSurvey
import com.example.nmatynia_surveyapp.Model.Survey
import com.example.nmatynia_surveyapp.Model.SurveyDataBase
import java.text.SimpleDateFormat
import java.util.*

class EditSurveyActivity : AppCompatActivity() {
    var surveyId: Int? = null;
    var publishedSurveyId: Int? = null
    var surveyTitle: String? = null
    var surveyStartDate: String? = null
    var surveyEndDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_survey)
        var publishedSurveyId= intent.getStringExtra("ID")
        var surveyId = intent.getStringExtra("SURVEY_ID")
        var surveyTitle = intent.getStringExtra("TITLE")
        var surveyEndDate = intent.getStringExtra("END_DATE")

        val editTextSurveyName = findViewById<EditText>(R.id.editTextSurveyName) as EditText
        editTextSurveyName.setText(surveyTitle)

        val datePickerEndDate = findViewById<DatePicker>(R.id.pickerEndDate)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.parse(surveyEndDate)

        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerEndDate.updateDate(year, month, day)
    }

    fun dateToString(datePicker: DatePicker): String{
        // Get the year, month, and day selected in the DatePicker
        val year = datePicker.year
        val month = datePicker.month
        val day = datePicker.dayOfMonth

        // Create a Calendar instance with the selected date
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        // Format the date as a string using a SimpleDateFormat
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        return dateFormat.format(calendar.time)
    }

    fun submitSurveyChanges(view: View){
        var publishedSurveyId= intent.getIntExtra("ID",-1)
        var surveyId = intent.getIntExtra("SURVEY_ID",-1)
        var surveyStartDate = intent.getStringExtra("START_DATE")
        val editTextSurveyName = findViewById<EditText>(R.id.editTextSurveyName) as EditText
        val datePickerEndDate = findViewById<DatePicker>(R.id.pickerEndDate)
        val db = SurveyDataBase(this)

        db.updateSurvey(Survey(surveyId,editTextSurveyName.text.toString()))
        db.updatePublishedSurvey(
            PublishedSurvey(publishedSurveyId,surveyId,surveyStartDate!!,dateToString(datePickerEndDate))
        )
        val intent = Intent(this, AdminSurveyActivity::class.java)
        startActivity(intent)
    }

    fun goBack(view:View){
        val intent = Intent(this, AdminSurveyActivity::class.java)
        startActivity(intent)
    }
}
package com.example.nmatynia_surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.marginBottom
import com.example.nmatynia_surveyapp.Model.PublishedSurvey
import com.example.nmatynia_surveyapp.Model.Question
import com.example.nmatynia_surveyapp.Model.Survey
import com.example.nmatynia_surveyapp.Model.SurveyDataBase
import java.text.SimpleDateFormat
import java.util.*

class NewSurveyActivity : AppCompatActivity() {

    var questionCount: Int = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_survey)
    }

    fun addNewQuestion(view: View) {
        questionCount++;
        val questionList = findViewById<LinearLayout>(R.id.questionList)
        val editText = EditText(this)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 30, 0, 0)

        editText.layoutParams = layoutParams
        editText.hint = "Question $questionCount"
        editText.setEms(10)
        editText.id = View.generateViewId()
        editText.textSize = 20f


        questionList.addView(editText)
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

    fun submitSurvey(view: View){
        val db = SurveyDataBase(this)

        // Post survey
        val surveyName = findViewById<TextView>(R.id.editTextSurveyName).text.toString()
        val survey = Survey(0,surveyName)
        val createdSurveyId = db.addSurvey(survey)
        if(createdSurveyId == -1L){
            Toast.makeText(this,"Error creating the survey", Toast.LENGTH_LONG).show()
            return
        }

        // Post published survey
        val startDate = dateToString(findViewById<DatePicker>(R.id.pickerStartDate))
        val endDate = dateToString(findViewById<DatePicker>(R.id.pickerEndDate))
        val publishedSurvey = PublishedSurvey(0,createdSurveyId.toInt(),startDate,endDate)
        val createdPublishedSurveyId = db.addPublishedSurvey(publishedSurvey)
        if(createdPublishedSurveyId == -1L){
            Toast.makeText(this,"Error creating the published survey", Toast.LENGTH_LONG).show()
            return
        }

        // Post questions
        val questionList = findViewById<LinearLayout>(R.id.questionList)
        for (i in 0 until questionList.childCount) {
            val child = questionList.getChildAt(i)
            if (child is EditText) {
                val questionText = child.text.toString()
                val question = Question(0,createdSurveyId.toInt(),questionText)
                val createdQuestionId = db.addQuestion(question)
                if(createdQuestionId == -1L){
                    Toast.makeText(this,"Error creating the question", Toast.LENGTH_LONG).show()
                    return
                }
            }
        }

        Toast.makeText(this,"Successfully created new survey", Toast.LENGTH_LONG).show()
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
    }
}
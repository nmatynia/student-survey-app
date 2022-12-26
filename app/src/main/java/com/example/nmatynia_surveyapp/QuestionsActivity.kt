package com.example.nmatynia_surveyapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.nmatynia_surveyapp.Model.Question
import com.example.nmatynia_surveyapp.Model.SurveyDataBase

class QuestionsActivity : AppCompatActivity() {

    var index = 0
    var surveyId: Int? = null;
    lateinit var questions: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        surveyId = intent.getIntExtra("SURVEY_ID", -1)
        val db = SurveyDataBase(this)
        questions = db.getAllQuestions(surveyId!!)

        findViewById<TextView>(R.id.title).setText("Question 1 of ${questions.size}")
        findViewById<TextView>(R.id.questionContent).setText(questions[index].QuestionText)
    }

    fun previous(view: View){
        if(index == 0){
            return
        }
        if(index == questions.size - 1){
            val nextButton = findViewById<Button>(R.id.buttonNext)
            nextButton.setText("Next")
            nextButton.setBackgroundColor(resources.getColor(R.color.purple_500))
        }
        index--;
        findViewById<TextView>(R.id.questionContent).setText(questions[index].QuestionText)
        findViewById<TextView>(R.id.title).setText("Question ${index + 1} of ${questions.size}")
    }

    fun next(view: View){
        if(index == questions.size - 1){
            submit()
            return
        }
        if(index == questions.size - 2){
            val nextButton = findViewById<Button>(R.id.buttonNext)
            nextButton.setText("Submit")
            nextButton.setBackgroundColor(Color.parseColor("#251351"))
        }
        index++;
        findViewById<TextView>(R.id.questionContent).setText(questions[index].QuestionText)
        findViewById<TextView>(R.id.title).setText("Question ${index + 1} of ${questions.size}")
    }

    fun submit(){

    }
}
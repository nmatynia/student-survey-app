package com.example.nmatynia_surveyapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.nmatynia_surveyapp.Model.Answer
import com.example.nmatynia_surveyapp.Model.Question
import com.example.nmatynia_surveyapp.Model.StudentSurveyRespond
import com.example.nmatynia_surveyapp.Model.SurveyDataBase

class QuestionsActivity : AppCompatActivity() {

    var index = 0
    var surveyId: Int? = null;
    var publishedSurveyId: Int? = null;
    var answer = mutableListOf<String>()
    lateinit var questions: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        surveyId = intent.getIntExtra("SURVEY_ID", -1)
        publishedSurveyId = intent.getIntExtra("ID", -1)
        val db = SurveyDataBase(this)
        questions = db.getAllQuestions(surveyId!!)
        answer.apply {
            repeat(questions.size) {
                add("")
            }
        }
        findViewById<TextView>(R.id.title).setText("Question 1 of ${questions.size}")
        findViewById<TextView>(R.id.questionContent).setText(questions[index].QuestionText)
    }

    fun previous(view: View) {
        if (index == 0) {
            return
        }
        if (index == questions.size - 1) {
            val nextButton = findViewById<Button>(R.id.buttonNext)
            nextButton.setText("Next")
            nextButton.setBackgroundColor(resources.getColor(R.color.purple_500))
        }
        val radioGroup = findViewById<RadioGroup>(R.id.answerGroup)
        val selectedId = radioGroup.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedId)
        val selectedValue = radioButton.text
        answer[index] = (selectedValue.toString())
        index--;

        if (answer[index] == "") {
            radioGroup.clearCheck()
        } else {
            for (i in 0 until radioGroup.childCount) {
                val radioButton = radioGroup.getChildAt(i) as RadioButton
                if (radioButton.text == answer[index]) {
                    radioGroup.check(radioButton.id)
                    break
                }
            }
        }

        findViewById<TextView>(R.id.questionContent).setText(questions[index].QuestionText)
        findViewById<TextView>(R.id.title).setText("Question ${index + 1} of ${questions.size}")
    }

    fun next(view: View) {
        val radioGroup = findViewById<RadioGroup>(R.id.answerGroup)
        val selectedId = radioGroup.checkedRadioButtonId


        if (selectedId == -1) {
            Toast.makeText(
                this,
                "You must select your answer before advancing",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val radioButton = findViewById<RadioButton>(selectedId)
        val selectedValue = radioButton.text

        if (index == questions.size - 1) {
            answer[index] = (selectedValue.toString())
            submit()
            return
        }
        if (index == questions.size - 2) {
            val nextButton = findViewById<Button>(R.id.buttonNext)
            nextButton.setText("Submit")
            nextButton.setBackgroundColor(Color.parseColor("#251351"))
        }


        answer[index] = (selectedValue.toString())
        Log.d("marcin", answer.toString())
        index++;

        if (answer[index] == "") {
            radioGroup.clearCheck()
        } else {
            for (i in 0 until radioGroup.childCount) {
                val radioButton = radioGroup.getChildAt(i) as RadioButton
                if (radioButton.text == answer[index]) {
                    radioGroup.check(radioButton.id)
                    break
                }
            }
        }
        findViewById<TextView>(R.id.questionContent).setText(questions[index].QuestionText)
        findViewById<TextView>(R.id.title).setText("Question ${index + 1} of ${questions.size}")

    }

    fun submit() {
        val db = SurveyDataBase(this);
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val studentId = prefs.getInt("studentId", -1)
        answer.forEachIndexed { idx, answer ->
            val questionId = questions[idx].Id
            val answerId = db.addAnswer(Answer(-1, answer)).toInt()
            val rowId = db.addStudentSurveyRes(
                StudentSurveyRespond(
                    -1,
                    studentId,
                    publishedSurveyId!!,
                    questionId,
                    answerId
                )
            )
            if (rowId == -1L) {
                Toast.makeText(
                    this,
                    "An error had occured while trying to submit the survey",
                    Toast.LENGTH_LONG
                ).show()
                return
            }
        }
        Toast.makeText(
            this,
            "Your respond has been submitted",
            Toast.LENGTH_LONG
        ).show()
        val intent = Intent(this, SurveyActivity::class.java)
        startActivity(intent)
    }
}
package com.example.nmatynia_surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import com.example.nmatynia_surveyapp.Model.*
import kotlin.math.roundToInt

class EndedSurveyStatsActivity : AppCompatActivity() {
    var publishedSurveyId: Int? = null
    lateinit var endedSurveyList: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ended_survey_stats)
        var endedSurveyList = findViewById<ListView>(R.id.questionsResList)
        var publishedSurveyId= intent.getIntExtra("ID", -1)

        val db = SurveyDataBase(this)

        val studentSurveyRespondList = db.getStudentSurveyResponds(publishedSurveyId);
        Log.d("de1",studentSurveyRespondList.toString())
        val uniqueQuestionAnswerList = ArrayList<QuestionAnswerSurveyRespond>()
        studentSurveyRespondList.forEach { respond ->
            val question = db.getQuestion(respond.QuestionId)
            if(!uniqueQuestionAnswerList.any{it.StudentId == respond.StudentId && it.Question == question.QuestionText}){
                val answer = db.getAnswer(respond.AnswerId)
                Log.d("answer",answer.toString())
                uniqueQuestionAnswerList.add(
                    QuestionAnswerSurveyRespond(respond.StudentId,
                        question.QuestionText,
                        answer.AnswerText)
                )
            }
        }
        Log.d("de2",uniqueQuestionAnswerList.toString())

        val uniqueResponds = uniqueQuestionAnswerList.size //TODO

        val surveyResponseTable = ArrayList<SurveyResponseRow>()

        uniqueQuestionAnswerList.forEach { questionAnswer ->
            val question = questionAnswer.Question
            val answer = questionAnswer.Answer

            val existingRow = surveyResponseTable.find { it.question == question }
            if (existingRow == null) {
                // create a new row with all counts set to zero
                var newRow = SurveyResponseRow(question, 0, 0, 0, 0, 0, 0)
                // update the count for the corresponding response
                when (answer) {
                    "Strongly Disagree" -> newRow.stronglyDisagree++
                    "Disagree" -> newRow.disagree++
                    "Neither Agree nor Disagree" -> newRow.agreeNorDisagree++
                    "Agree" -> newRow.agree++
                    "Strongly Agree" -> newRow.stronglyAgree++
                }
                newRow.total++
                surveyResponseTable.add(newRow)
            } else {
                // update the count for the corresponding response
                when (answer) {
                    "Strongly Disagree" -> existingRow.stronglyDisagree++
                    "Disagree" -> existingRow.disagree++
                    "Neither Agree nor Disagree" -> existingRow.agreeNorDisagree++
                    "Agree" -> existingRow.agree++
                    "Strongly Agree" -> existingRow.stronglyAgree++
                }
                existingRow.total++
            }
        }
        val questionList: ArrayList<String> = surveyResponseTable.map {it.question} as ArrayList<String>
        val stronglyDisagreeList: ArrayList<String> = surveyResponseTable.map {
            if (it.total == 0) {
                "0%"
            } else {
                "${(it.stronglyDisagree.toDouble()/it.total * 100).roundToInt()}%"
            }
        }.toCollection(ArrayList())

        val disagreeList: ArrayList<String> = surveyResponseTable.map {
            if (it.total == 0) {
                "0%"
            } else {
                "${(it.disagree.toDouble()/it.total * 100).roundToInt()}%"
            }
        }.toCollection(ArrayList())

        val norList: ArrayList<String> = surveyResponseTable.map {
            if (it.total == 0) {
                "0%"
            } else {
                "${(it.agreeNorDisagree.toDouble()/it.total * 100).roundToInt()}%"
            }
        }.toCollection(ArrayList())

        val agreeList: ArrayList<String> = surveyResponseTable.map {
            if (it.total == 0) {
                "0%"
            } else {
                "${(it.agree.toDouble()/it.total * 100).roundToInt()}%"
            }
        }.toCollection(ArrayList())

        val stronglyAgreeList: ArrayList<String> = surveyResponseTable.map {
            if (it.total == 0) {
                "0%"
            } else {
                "${(it.stronglyAgree.toDouble()/it.total * 100).roundToInt()}%"
            }
        }.toCollection(ArrayList())

        val adapter = SurveyStatsAdapter(applicationContext, questionList, stronglyDisagreeList, disagreeList, norList, agreeList, stronglyAgreeList)

        endedSurveyList!!.adapter = adapter
    }

    fun logout(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}


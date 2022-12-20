package com.example.nmatynia_surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
    }
    fun goToNewSurvey(view: View){
        val intent = Intent(this, NewSurveyActivity::class.java)
        startActivity(intent)
    }

    fun logout(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
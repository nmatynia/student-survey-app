package com.example.nmatynia_surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.nmatynia_surveyapp.Model.SurveyDataBase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loginButton(view: View) {

        val login = findViewById<EditText>(R.id.editTextUserName).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()

        if(login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please insert both Login and Password", Toast.LENGTH_LONG).show()
            return
        }

        val db = SurveyDataBase(this)
        if(db.getAdmin(login).PassWord === password){
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
            Toast.makeText(this,"You logged in successfully as an Admin. ", Toast.LENGTH_LONG).show()
        }
        else if(db.getStudent(login).PassWord === password){
            val intent = Intent(this, SurveyActivity::class.java)
            startActivity(intent)
            Toast.makeText(this,"You logged in successfully. ", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this,"Incorrect credentials", Toast.LENGTH_LONG).show()
        }
    }


    fun registerButton(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
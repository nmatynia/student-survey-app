package com.example.nmatynia_surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.nmatynia_surveyapp.Model.SurveyDataBase
import com.example.nmatynia_surveyapp.Model.Student

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun signUpButton(view: View){
        val login = findViewById<EditText>(R.id.editTextUserName).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
        val repeatedPassword =  findViewById<EditText>(R.id.editTextRepeatPassword).text.toString()

        if(login.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all inputs", Toast.LENGTH_LONG).show()
            return
        }
        val db = SurveyDataBase(this)
        if(password == repeatedPassword){
            db.addStudent(Student(0,login,password))
            val intent = Intent(this, SurveyActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Successfully singed up!", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Passwords doesn't match up", Toast.LENGTH_LONG).show()
        }
    }
}
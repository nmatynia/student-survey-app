package com.example.nmatynia_surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.nmatynia_surveyapp.Model.SurveyDataBase
import com.example.nmatynia_surveyapp.Model.Admin
import com.example.nmatynia_surveyapp.Model.Student

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loginButton(view: View) {

//        val message = findViewById<TextView>(R.id.textViewMessage)
//        val userName = findViewById<EditText>(R.id.editTextUserName).text.toString()
//        val userPassword = findViewById<EditText>(R.id.editTextPassword).text.toString()
//
//        if(userName.isEmpty() || userPassword.isEmpty())
//            Toast.makeText(this,"Please insert Username and Password",Toast.LENGTH_LONG).show()
//        else {
//            val myDataBase = DataBaseHelper(this)
//            val result = myDataBase.getUser(User(-1," ", " ",
//                0, 0, " ", userName,userPassword))
//            if( result == -1)
//                Toast.makeText(this,"User not found.", Toast.LENGTH_SHORT).show()
//            else if( result == -2)
//                Toast.makeText(this,"Database error occurred.", Toast.LENGTH_SHORT).show()
//            else {
//                val intent = Intent(this, SurveyActivity::class.java)
//                startActivity(intent)
//                Toast.makeText(this,"You logged in successfully. ", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    fun registerButton(view: View) {

//        val intent = Intent(this, MainActivityNewUser::class.java)
//        startActivity(intent)
    }
}
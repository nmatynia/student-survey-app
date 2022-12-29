package com.example.nmatynia_surveyapp.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val DataBaseName = "AppDataBase.db"
private val ver : Int = 1

class SurveyDataBase(context: Context) : SQLiteOpenHelper(context,DataBaseName,null ,ver) {

    /* Admin Table */
    public val AdminTableName = "Admin"
    public val AdminColumnId = "Id"
    public val AdminColumnLoginName = "LoginName"
    public val AdminColumnPassWord = "PassWord"

    /*************************/

    /* Answer Table */
    public val AnswerTableName = "Answer"
    public val AnswerColumnId = "Id"
    public val AnswerColumnAnswerText = "AnswerText"

    /*************************/

    /* Published Survey Table */
    public val PublishedSurveyTableName = "PublishedSurvey"
    public val PublishedSurveyColumnId = "Id"
    public val PublishedSurveyColumnSurveyId = "SurveyId"
    public val PublishedSurveyColumnStartDate = "StartDate"
    public val PublishedSurveyColumnEndDate = "EndDate"

    /*************************/

    /* Question Table */
    public val QuestionTableName = "Question"
    public val QuestionColumnId = "Id"
    public val QuestionColumnSurveyId = "SurveyId"
    public val QuestionColumnQuestionText = "QuestionText"

    /*************************/

    /* Student Table */
    public val StudentTableName = "Student"
    public val StudentColumnId = "Id"
    public val StudentColumnLoginName = "LoginName"
    public val StudentColumnPassWord = "PassWord"

    /*************************/

    /* Student Survey Respond Table */
    public val StudentSurveyResTableName = "StudentSurveyRespond"
    public val StudentSurveyResColumnId = "Id"
    public val StudentSurveyResColumnStudentId = "StudentId"
    public val StudentSurveyResColumnPublishedSurveyId = "PublishedSurveyId"
    public val StudentSurveyResColumnQuestionId = "QuestionId"
    public val StudentSurveyResColumnAnwserId = "AnwserId"

    /*************************/

    /* Survey Table */
    public val SurveyTableName = "Survey"
    public val SurveyColumnId = "Id"
    public val SurveyColumnTitle = "Title"

    /*************************/


    // This is called the first time a database is accessed
    // Create a new database
    override fun onCreate(db: SQLiteDatabase?) {

        try {
            /* Admin Table */
            var sqlCreateStatement: String = "CREATE TABLE " + AdminTableName +
                    " ( " + AdminColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AdminColumnLoginName + " TEXT NOT NULL UNIQUE, " +
                    AdminColumnPassWord + " TEXT NOT NULL )"
            db?.execSQL(sqlCreateStatement)

            /* Answer Table */
            sqlCreateStatement = "CREATE TABLE " + AnswerTableName +
                    " ( " + AnswerColumnId + " INTEGER PRIMARY KEY, " +
                    AnswerColumnAnswerText + " TEXT NOT NULL )"
            db?.execSQL(sqlCreateStatement)

            /* Published Survey Table */
            sqlCreateStatement = "CREATE TABLE " + PublishedSurveyTableName +
                    " ( " + PublishedSurveyColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PublishedSurveyColumnSurveyId + " INTEGER NOT NULL, " +
                    PublishedSurveyColumnStartDate + " TEXT NOT NULL, " +
                    PublishedSurveyColumnEndDate + " TEXT NOT NULL ) "
            db?.execSQL(sqlCreateStatement)

            /* Question Table */
            sqlCreateStatement = "CREATE TABLE " + QuestionTableName +
                    " ( " + QuestionColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QuestionColumnSurveyId + " INTEGER NOT NULL, " +
                    QuestionColumnQuestionText + " TEXT NOT NULL )"
            db?.execSQL(sqlCreateStatement)

            /* Student Table */
            sqlCreateStatement = "CREATE TABLE " + StudentTableName +
                    " ( " + StudentColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    StudentColumnLoginName + " TEXT NOT NULL UNIQUE, " +
                    StudentColumnPassWord + " TEXT NOT NULL )"
            db?.execSQL(sqlCreateStatement)

            /* Student Survey Respond Table */
            sqlCreateStatement = "CREATE TABLE " + StudentSurveyResTableName +
                    " ( " + StudentSurveyResColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    StudentSurveyResColumnStudentId + " INTEGER NOT NULL, " +
                    StudentSurveyResColumnPublishedSurveyId + " INTEGER NOT NULL, " +
                    StudentSurveyResColumnQuestionId + " INTEGER NOT NULL, " +
                    StudentSurveyResColumnAnwserId + " INTEGER NOT NULL ) "
            db?.execSQL(sqlCreateStatement)

            /* Survey Table */
            sqlCreateStatement = "CREATE TABLE " + SurveyTableName +
                    " ( " + SurveyColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SurveyColumnTitle + " TEXT NOT NULL ) "
            db?.execSQL(sqlCreateStatement)
        }
        catch(e : SQLException) {  }

    }

    // This is called if the database ver. is changed
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun addStudent(student: Student): Boolean {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(StudentColumnLoginName, student.LoginName)
        cv.put(StudentColumnPassWord, student.PassWord)

        val success = db.insert(StudentTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun getAdmin(login: String): Admin{
        var admin = Admin(-1,"","")
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $AdminTableName WHERE $AdminColumnLoginName = ?", arrayOf(login))
        if(cursor.moveToFirst()){
            val id = cursor.getInt(0)
            val login = cursor.getString(1)
            val password = cursor.getString(2)
            admin =  Admin(id,login,password)
        }
        cursor.close()
        db.close()
        return admin
    }

    fun getStudent(login: String): Student{
        var student = Student(-1,"","")
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $StudentTableName WHERE $StudentColumnLoginName = ?", arrayOf(login))
        if(cursor.moveToFirst()){
            val id = cursor.getInt(0)
            val login = cursor.getString(1)
            val password = cursor.getString(2)
            student =  Student(id,login,password)
        }
        cursor.close()
        db.close()
        return student
    }

    fun getSurvey(id: Int): Survey{
        var survey = Survey(-1, "");
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $SurveyTableName WHERE $SurveyColumnId = ?", arrayOf(id.toString()))
        if(cursor.moveToFirst()){
            val id = cursor.getInt(0)
            val title = cursor.getString(1)
            survey =  Survey(id,title)
        }
        cursor.close()
        db.close()
        return survey


    }

    fun addSurvey(survey: Survey): Long {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(SurveyColumnTitle, survey.Title)

        val rowId = db.insert(SurveyTableName, null, cv)
        db.close()
        return rowId
    }

    fun addPublishedSurvey(publishedSurvey: PublishedSurvey): Long {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(PublishedSurveyColumnSurveyId, publishedSurvey.SurveyId)
        cv.put(PublishedSurveyColumnStartDate, publishedSurvey.StartDate)
        cv.put(PublishedSurveyColumnEndDate, publishedSurvey.EndDate)

        val rowId = db.insert(PublishedSurveyTableName, null, cv)
        db.close()
        return rowId
    }

    fun addAnswer(answer: Answer): Long{
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(AnswerColumnAnswerText, answer.AnswerText)

        val rowId = db.insert(AnswerTableName, null, cv)
        db.close()
        return rowId
    }

    fun addStudentSurveyRes(studentSurveyRespond: StudentSurveyRespond): Long{
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(StudentSurveyResColumnStudentId, studentSurveyRespond.StudentId)
        cv.put(StudentSurveyResColumnQuestionId, studentSurveyRespond.QuestionId)
        cv.put(StudentSurveyResColumnPublishedSurveyId, studentSurveyRespond.PublishedSurveyId)
        cv.put(StudentSurveyResColumnAnwserId, studentSurveyRespond.AnswerId)

        val rowId = db.insert(StudentSurveyResTableName, null, cv)
        db.close()
        return rowId
    }

    fun updateSurvey(survey: Survey): Boolean {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(SurveyColumnTitle, survey.Title)

        val result = db.update(SurveyTableName, cv, "$SurveyColumnId =  ${survey.Id}", null) == 1
        db.close()
        return result
    }

    fun updatePublishedSurvey(publishedSurvey: PublishedSurvey): Boolean {

        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(PublishedSurveyColumnStartDate, publishedSurvey.StartDate)
        cv.put(PublishedSurveyColumnEndDate, publishedSurvey.EndDate)

        val result = db.update(PublishedSurveyTableName, cv, "$PublishedSurveyColumnId =  ${publishedSurvey.Id}", null) == 1
        db.close()
        return result
    }

    fun addQuestion(question: Question): Long {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(QuestionColumnSurveyId, question.SurveyId)
        cv.put(QuestionColumnQuestionText, question.QuestionText)

        val rowId = db.insert(QuestionTableName, null, cv)
        db.close()
        return rowId
    }

    fun getAllSurveys(): ArrayList<Survey> {
        val surveyList = ArrayList<Survey>()
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $SurveyTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst()) {
            do {
                val id: Int = cursor.getInt(0)
                val title: String = cursor.getString(1)
                val survey = Survey(id, title)
                surveyList.add(survey)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return surveyList
    }



    fun getAllPublishedSurveys(): ArrayList<PublishedSurvey> {
        val publishedSurveyList = ArrayList<PublishedSurvey>()
        val db: SQLiteDatabase = this.readableDatabase

        val sqlStatement = "SELECT * FROM $PublishedSurveyTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst()) {
            do {
                val id: Int = cursor.getInt(0)
                val surveyId: Int = cursor.getInt(1)
                val startDate: String = cursor.getString(2)
                val endDate: String = cursor.getString(3)
                val publishedSurvey = PublishedSurvey(id, surveyId,startDate,endDate)
                publishedSurveyList.add(publishedSurvey)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return publishedSurveyList
    }

    fun getAllQuestions(surveyId: Int): ArrayList<Question> {

        val questionList = ArrayList<Question>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $QuestionTableName WHERE $QuestionColumnSurveyId = $surveyId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val surveyId: Int = cursor.getInt(1)
                val questionText: String = cursor.getString(2)

                val question = Question(id, surveyId, questionText)
                questionList.add(question)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return questionList
    }

    fun getStudentSurveyResponds(publishedSurveyId: Int): ArrayList<StudentSurveyRespond>{
        val respondList = ArrayList<StudentSurveyRespond>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentSurveyResTableName WHERE $StudentSurveyResColumnPublishedSurveyId = $publishedSurveyId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val studentId: Int = cursor.getInt(1)
                val publishedSurveyId: Int = cursor.getInt(2)
                val questionId: Int = cursor.getInt(3)
                val answerId: Int = cursor.getInt(4)


                val respond = StudentSurveyRespond(id, studentId, publishedSurveyId, questionId, answerId)
                respondList.add(respond)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return respondList
    }

    fun getQuestion(questionId: Int): Question{
        var question = Question(-1, -1,"");
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $QuestionTableName WHERE $QuestionColumnId = ?", arrayOf(questionId.toString()))
        if(cursor.moveToFirst()){
            val id = cursor.getInt(0)
            val surveyId = cursor.getInt(1)
            val title = cursor.getString(2)
            question =  Question(id, surveyId, title)
        }
        cursor.close()
        db.close()
        return question
    }

    fun getAnswer(answerId: Int): Answer{
        var answer = Answer(-1, "");
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $AnswerTableName WHERE $AnswerColumnId = ?", arrayOf(answerId.toString()))
        if(cursor.moveToFirst()){
            val id = cursor.getInt(0)
            val answerContent = cursor.getString(1)
            answer =  Answer(id, answerContent)
        }
        cursor.close()
        db.close()
        return answer
    }
}


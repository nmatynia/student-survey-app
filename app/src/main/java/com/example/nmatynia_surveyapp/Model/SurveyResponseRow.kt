package com.example.nmatynia_surveyapp.Model

data class SurveyResponseRow(val question: String, var stronglyDisagree: Int, var disagree: Int, var agreeNorDisagree: Int, var agree: Int, var stronglyAgree: Int, var total: Int)
package com.example.quizzingapp.model

data class Question(
    val correct_answers: List<Int>,
    val lable: String,
    val options: List<Option>
)
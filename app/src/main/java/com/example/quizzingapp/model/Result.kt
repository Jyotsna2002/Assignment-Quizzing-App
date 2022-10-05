package com.example.quizzingapp.model

data class Result(
    val questions: List<Question>,
    val timeInMinutes: Int
)
package com.example.quizzingapp.network

import com.example.quizzingapp.model.model_query
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

	@GET("?quiz=true")
	fun quizzData(): Call<model_query>
}
package com.example.quizzingapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.morefit.sealedClass.Response
import com.example.quizzingapp.model.model_query
import com.example.quizzingapp.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class CreateQuizzRepository() {
	private val liveData = MutableLiveData<Response<model_query>>()

	fun QuizzData(): MutableLiveData<Response<model_query>> {
		val call = ServiceBuilder.buildService().quizzData()
		call.enqueue(object : Callback<model_query> {
			override fun onResponse(
				call: Call<model_query>,
				response: retrofit2.Response<model_query>
			) {
				when {
					response.isSuccessful ->{
						liveData.postValue(Response.Success(response.body()!!))
					}

					else -> {
						liveData.postValue(Response.Error(response.message()))
						Log.e("else", "onFailure: "+response.body() )
					}
				}
			}

			override fun onFailure(call: Call<model_query>, t: Throwable) {
				val message =t.message
				liveData.postValue(message?.let { Response.Error(it) })
				Log.e("faliue", "onFailure: "+t.message )
			}
		})

		return liveData
	}
}
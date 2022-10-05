package com.example.quizzingapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morefit.sealedClass.Response
import com.example.quizzingapp.model.model_query
import com.example.quizzingapp.repository.CreateQuizzRepository
import kotlinx.coroutines.launch

class CreateQuizzViewModel() : ViewModel() {
	private var quizzResult: MutableLiveData<Response<model_query>> = MutableLiveData()
	val _quizzResult: LiveData<Response<model_query>>
		get() = quizzResult

	fun submitResult() = viewModelScope.launch {
		quizzResult = CreateQuizzRepository().QuizzData()
	}
}

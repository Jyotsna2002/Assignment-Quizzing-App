package com.example.quizzingapp.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzingapp.R
import com.example.quizzingapp.databinding.QuizzListItemsBinding
import com.example.quizzingapp.model.model_query
import com.google.android.material.button.MaterialButton

class QuizzItemsAdapter(private val context: Context,private val result: model_query, private val listener: QuizzClickListenerInterface) :
	RecyclerView.Adapter<QuizzItemsAdapter.ViewHolder>() {

	private var value1=false
	private var value2=false
	private var value3=false
	private var value4=false



	inner class ViewHolder(val binding: QuizzListItemsBinding) :
		RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
		(QuizzListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)))

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.binding.apply {

			question.text=(position+1).toString()+". "+result.result.questions[position].lable
			option1.text=result.result.questions[position].options[0].key.toString()+". "+result.result.questions[position].options[0].lable
			option2.text=result.result.questions[position].options[1].key.toString()+". "+result.result.questions[position].options[1].lable
			option3.text=result.result.questions[position].options[2].key.toString()+". "+result.result.questions[position].options[2].lable
			option4.text=result.result.questions[position].options[3].key.toString()+". "+result.result.questions[position].options[3].lable

			option1.setOnClickListener {
				if (value1){
					highlight(option1, false)
					value1=false
				}
				else{
					listener.quizzClickListener("1")
					highlight(option1, true)
					value1=true
				}
			}
			option2.setOnClickListener {
				if (value2){
					highlight(option2, false)
					value2=false
				}
				else{
					listener.quizzClickListener("2")
					highlight(option2, true)
					value2=true
				}
			}
			option3.setOnClickListener {
				if (value3){
					highlight(option3, false)
					value3=false
				}
				else{
					listener.quizzClickListener("3")
					highlight(option3, true)
					value3=true
				}
			}
			option4.setOnClickListener {
				if (value4){
					highlight(option4, false)
					value4=false
				}
				else{
					listener.quizzClickListener("4")
					highlight(option4, true)
					value4=true
				}
			}
		}
	}

	override fun getItemCount(): Int {
		return result.result.questions.size
	}

	private fun highlight(btn: MaterialButton, bool: Boolean) {
		if (bool) {
			btn.setBackgroundColor(Color.parseColor("#00FF00"))
			btn.strokeWidth = 0
			btn.setTextColor(Color.parseColor("#FFFFFF"))
		} else {
			btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
			btn.strokeWidth = 3
			btn.strokeColor= ContextCompat.getColorStateList(context,R.color.light_grey)
			btn.setTextColor(Color.parseColor("#000000"))
		}
	}
}

interface QuizzClickListenerInterface {
	fun quizzClickListener(id:String)
}
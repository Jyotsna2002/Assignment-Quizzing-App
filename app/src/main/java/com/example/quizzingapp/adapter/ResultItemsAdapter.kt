package com.example.quizzingapp.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzingapp.R
import com.example.quizzingapp.databinding.QuizzListItemsBinding
import com.example.quizzingapp.model.model_query
import com.google.android.material.button.MaterialButton

class ResultItemsAdapter(private val context:Context, private val result: model_query,private val results: Map<Int, List<Int>>) :
	RecyclerView.Adapter<ResultItemsAdapter.ViewHolder>() {

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

			result.result.questions[position].correct_answers.forEach {
				when (it) {
					1 -> highlight(option1, true)
					2 -> highlight(option2, true)
					3 -> highlight(option3, true)
					4 -> highlight(option4, true)
				}
				results[(position + 1)]?.forEach { value ->
					if (value !in result.result.questions[position].correct_answers) {
						when (value) {
							1 -> highlight(option1, false)
							2 -> highlight(option2, false)
							3 -> highlight(option3, false)
							4 -> highlight(option4, false)
						}
					}

					when (value) {
						1 -> {
							option1.icon= AppCompatResources.getDrawable(context, R.drawable.ic_check_circle)
						}
						2 -> option2.icon= AppCompatResources.getDrawable(context, R.drawable.ic_check_circle)
						3 -> option3.icon= AppCompatResources.getDrawable(context, R.drawable.ic_check_circle)
						4 -> option4.icon= AppCompatResources.getDrawable(context, R.drawable.ic_check_circle)
					}
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
			btn.setBackgroundColor(Color.parseColor("#FF0000"))
			btn.strokeWidth = 0
			btn.setTextColor(Color.parseColor("#FFFFFF"))
		}
	}
}

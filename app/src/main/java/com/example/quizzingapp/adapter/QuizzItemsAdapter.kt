package com.example.quizzingapp.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
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
	var map=HashMap<Int,List<Int>>()
    var listop1= mutableListOf<Int>()
	var listop2= mutableListOf<Int>()
	var listop3= mutableListOf<Int>()
	var listop4= mutableListOf<Int>()


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
				val list3= mutableListOf<Int>()
				if(position in listop1){
					value1=true
				}
				if (value1){
					listop1.remove(position)
					val r=map[(position+1)]
					if (r != null) {
						list3.addAll(r)
					}
					list3.remove(1)
					map.put((position+1), list3)
					listener.quizzClickListener(map)
					highlight(option1, false)
					value1=false
				}
				else{
					listop1.add(position)
					val r=map[(position+1)]
					if (r != null) {
						list3.addAll(r)
					}
					list3.add(1)
					map.put((position+1), list3)
					listener.quizzClickListener(map)
					highlight(option1, true)
				}
			}
			option2.setOnClickListener {
				val list2= mutableListOf<Int>()
				if(position in listop2){
					value2=true
				}
				if (value2){
					listop2.remove(position)
					val r=map[(position+1)]
					if (r != null) {
						list2.addAll(r)
					}
					list2.remove(2)
					map.put((position+1), list2)
					listener.quizzClickListener(map)
					highlight(option2, false)
					value2=false
				}
				else{
					listop2.add(position)
					val r=map[(position+1)]
					if (r != null) {
						list2.addAll(r)
					}
					list2.add(2)
					map.put((position+1), list2)
					listener.quizzClickListener(map)
					highlight(option2, true)
				}
			}
			option3.setOnClickListener {
				val list2= mutableListOf<Int>()
				if(position in listop3){
					value3=true
				}
				if (value3){
					listop3.remove(position)
					val r=map[(position+1)]
					if (r != null) {
						list2.addAll(r)
					}
					list2.remove(3)
					map.put((position+1), list2)
					listener.quizzClickListener(map)
					highlight(option3, false)
					value3=false
				}
				else{
					listop3.add(position)
					val r=map[(position+1)]
					if (r != null) {
						list2.addAll(r)
					}
					list2.add(3)
					map.put((position+1), list2)
					listener.quizzClickListener(map)
					highlight(option3, true)
				}
			}
			option4.setOnClickListener {
				val list2= mutableListOf<Int>()
				if(position in listop4){
					value4=true
				}
				if (value4){
					listop4.remove(position)
					val r=map[(position+1)]
					if (r != null) {
						list2.addAll(r)
					}
					list2.remove(4)
					map.put((position+1), list2)
					listener.quizzClickListener(map)
					highlight(option4, false)
					value4=false
				}
				else{
					listop4.add(position)
					val r=map[(position+1)]
					if (r != null) {
						list2.addAll(r)
					}
					list2.add(4)
					map.put((position+1), list2)
					listener.quizzClickListener(map)
					highlight(option4, true)
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
	fun quizzClickListener(result:Map<Int,List<Int>>)
}
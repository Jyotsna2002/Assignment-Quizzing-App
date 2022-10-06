package com.example.quizzingapp.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.morefit.sealedClass.Response
import com.example.quizzingapp.R
import com.example.quizzingapp.databinding.FragmentHomeBinding
import com.example.quizzingapp.databinding.FragmentLoginBinding
import com.example.quizzingapp.databinding.FragmentQuizzBinding
import com.example.quizzingapp.model.model_query
import com.example.quizzingapp.ui.QuizzClickListenerInterface
import com.example.quizzingapp.ui.QuizzItemsAdapter
import com.example.quizzingapp.view_model.CreateQuizzViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView

class Quizz_Fragment : Fragment(), QuizzClickListenerInterface {
    private var _binding: FragmentQuizzBinding? = null
    private val binding get() = _binding!!
    private lateinit var countDownTimer:CountDownTimer
    private val createQuizzViewModel by lazy { ViewModelProvider(this)[CreateQuizzViewModel::class.java] }
    companion object{
        lateinit var data:model_query
        lateinit var results: Map<Int, List<Int>>
    }
    var value:Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizzBinding.inflate(inflater, container, false)
        val view = binding.root
        results= mapOf()
        binding.timer.visibility=View.GONE
        binding.submitBtn.visibility=View.GONE
        binding.progressBar.visibility=View.VISIBLE
        binding.submitBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, Result_Fragment())
                ?.commit()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createQuizzViewModel.submitResult()
        createQuizzViewModel._quizzResult.observe(viewLifecycleOwner){
            if (it is Response.Success) {
                binding.progressBar.visibility=View.GONE
                binding.timer.visibility=View.VISIBLE
                binding.submitBtn.visibility=View.VISIBLE
                data= it.data!!
                value=it.data.result.timeInMinutes
                printDifferenceDateForHours()
                val adapter =
                    QuizzItemsAdapter(requireContext(), it.data, this)
                val recyclerView = binding.recyclerview
                recyclerView.adapter = adapter
            }
            else it.errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()

            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
    countDownTimer.cancel()
        _binding = null
    }

    override fun quizzClickListener(result:Map<Int,List<Int>>) {
        results=result
    }
    fun printDifferenceDateForHours() {

        value =0* 60 * 1000
        countDownTimer = object : CountDownTimer(value.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60


                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                binding.time.text = String.format("%02d:%02d:%02d",elapsedHours,elapsedMinutes,elapsedSeconds)
            }

            override fun onFinish() {
                val customView = layoutInflater.inflate(R.layout.dialog, null)
                val builder = MaterialAlertDialogBuilder(requireContext()).apply {
                    setView(customView)
                    background = ColorDrawable(Color.TRANSPARENT)
                    setCancelable(false)
                }
                val dialog = builder.show()

                customView.findViewById<MaterialTextView>(R.id.dialogMessage)
                    .setText("TIME UP!!")
                val logout = customView.findViewById<MaterialButton>(R.id.positiveBtn)
                logout.setOnClickListener {
                    dialog.dismiss()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragmentContainerView, Result_Fragment())
                        ?.commit()
                }
            }
        }.start()
    }
}
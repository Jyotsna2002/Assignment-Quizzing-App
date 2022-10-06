package com.example.quizzingapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.quizzingapp.databinding.FragmentResultBinding
import com.example.quizzingapp.ui.ResultItemsAdapter
import com.example.quizzingapp.ui.fragment.Quizz_Fragment.Companion.data
import com.example.quizzingapp.ui.fragment.Quizz_Fragment.Companion.results

class Result_Fragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        val adapter =
            context?.let { ResultItemsAdapter(it,data,results) }
        val recyclerView = binding.recyclerview2
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
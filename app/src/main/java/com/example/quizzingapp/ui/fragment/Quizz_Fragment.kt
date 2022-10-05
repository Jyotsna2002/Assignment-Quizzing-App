package com.example.quizzingapp.ui.fragment

import android.os.Bundle
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
import com.example.quizzingapp.ui.QuizzClickListenerInterface
import com.example.quizzingapp.ui.QuizzItemsAdapter
import com.example.quizzingapp.view_model.CreateQuizzViewModel

class Quizz_Fragment : Fragment(), QuizzClickListenerInterface {
    private var _binding: FragmentQuizzBinding? = null
    private val binding get() = _binding!!
    private val createQuizzViewModel by lazy { ViewModelProvider(this)[CreateQuizzViewModel::class.java] }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizzBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createQuizzViewModel.submitResult()
        createQuizzViewModel._quizzResult.observe(viewLifecycleOwner){
            if (it is Response.Success) {
                val adapter =
                    QuizzItemsAdapter(requireContext(),it.data!!, this)
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
        _binding = null
    }

    override fun quizzClickListener(id: String) {
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
    }

}
package com.example.quizzingapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.quizzingapp.R
import com.example.quizzingapp.databinding.FragmentLoginBinding
import com.example.quizzingapp.ui.activity.MainActivity.Companion.email
import com.example.quizzingapp.ui.activity.MainActivity.Companion.name
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class Login_Fragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = context?.let { GoogleSignIn.getClient(it, gso!!) }

        val acct = context?.let { GoogleSignIn.getLastSignedInAccount(it) }
        if (acct != null) {
            navigateToSecondActivity()
        }


        binding.googleBtn.setOnClickListener{ signIn() }
        binding.loginBtn.setOnClickListener {
            name= binding.loginNameEdit.text.toString().trim()
            email= binding.loginEmailEdit.text.toString().trim()
            if (isValid(name,email)) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView, Home_Fragment())
                    ?.commit()
            }
        }
        return view
    }
    fun signIn() {
        val signInIntent = gsc!!.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                navigateToSecondActivity()
            } catch (e: ApiException) {
                Toast.makeText(
                    context,
                    e.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun navigateToSecondActivity() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainerView, Home_Fragment())
            ?.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun isValid(name:String,email:String):Boolean{
        return when{
            name.isBlank()->{
                binding.loginName.helperText="Enter Your Name"
                false
            }
            email.isBlank()->{
                binding.loginEmail.helperText="Enter Your Email Id"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.loginEmail.helperText = "Enter valid Email Id"
                false
            }
            else -> {
                true
            }
        }
    }
}
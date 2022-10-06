package com.example.quizzingapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import coil.load
import com.example.quizzingapp.R
import com.example.quizzingapp.databinding.FragmentHomeBinding
import com.example.quizzingapp.ui.activity.MainActivity.Companion.Image
import com.example.quizzingapp.ui.activity.MainActivity.Companion.bio
import com.example.quizzingapp.ui.activity.MainActivity.Companion.email
import com.example.quizzingapp.ui.activity.MainActivity.Companion.name
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class Home_Fragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        if (Image!= "".toUri()) {
            binding.ProfilePhoto.load(Image) {
                ImageView.ScaleType.CENTER_CROP
                crossfade(true)
                placeholder(R.drawable.place2)

            }
        }

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = context?.let { GoogleSignIn.getClient(it, gso!!) }

        val acct = context?.let { GoogleSignIn.getLastSignedInAccount(it) }
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            if (personName != null) {
                name=personName
            }
            if (personEmail != null) {
                email=personEmail
            }
        }
        binding.QuizzBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, Quizz_Fragment())
                ?.commit()
        }
        binding.changeProfile.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, Profile_Fragment())
                ?.commit()
        }
        binding.name.text="Welcome "+name+","
        binding.bio2.text=bio
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
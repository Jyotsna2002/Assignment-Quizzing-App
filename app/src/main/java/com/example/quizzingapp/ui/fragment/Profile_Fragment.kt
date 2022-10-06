package com.example.quizzingapp.ui.fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import coil.load
import com.example.quizzingapp.R
import com.example.quizzingapp.databinding.FragmentProfileBinding
import com.example.quizzingapp.ui.activity.MainActivity
import com.example.quizzingapp.ui.activity.MainActivity.Companion.Image
import com.example.quizzingapp.ui.activity.MainActivity.Companion.bio
import com.example.quizzingapp.ui.activity.MainActivity.Companion.email
import com.example.quizzingapp.ui.activity.MainActivity.Companion.name
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class Profile_Fragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var IMAGE_REQUEST_CODE = 100
    private lateinit var Imageuri: Uri
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        if (Image!= "".toUri()) {
            binding.setProfilePhoto.load(Image) {
                ImageView.ScaleType.CENTER_CROP
                crossfade(true)
                placeholder(R.drawable.place2)

            }
        }
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = context?.let { GoogleSignIn.getClient(it, gso!!) }
        binding.email.text="Your email address is: \n"+ email
        binding.email.textAlignment=View.TEXT_ALIGNMENT_CENTER
        binding.changeUsernameEditText.setText(name)
        binding.changeUsernameEditText.setHintTextColor(context?.let {
            ContextCompat.getColorStateList(
                it,R.color.black)
        })
        binding.SaveChanges.setOnClickListener {
            name=binding.changeUsernameEditText.text.toString().trim()
            bio= binding.SetYourBioEditText.text.toString().trim()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, Home_Fragment())
                ?.commit()
        }
        binding.signOut.setOnClickListener {
            signOut()
        }
        binding.setProfile.setOnClickListener {
            pickImages()
        }
        binding.back.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, Home_Fragment())
                ?.commit()
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun signOut() {
        gsc?.signOut()?.addOnCompleteListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, Login_Fragment())
                ?.commit()
        }
    }
    private fun pickImages() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Imageuri = data?.getData()!!
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Uploading File...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val randomKey = UUID.randomUUID().toString()
            val storageReference =
                FirebaseStorage.getInstance().getReference("images/" + randomKey)
            storageReference.putFile(Imageuri)
                .addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener {
                        Image=Imageuri
                        binding.setProfilePhoto.load(Imageuri) {
                            ImageView.ScaleType.CENTER_CROP
                            crossfade(true)
                            placeholder(R.drawable.place2)

                        }
                        Toast.makeText(context, "Save your Changes", Toast.LENGTH_SHORT)
                            .show()
                        if (progressDialog.isShowing)
                            progressDialog.dismiss()

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing)
                        progressDialog.dismiss()
                }

        }
    }
}
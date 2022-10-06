package com.example.quizzingapp.ui.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.example.quizzingapp.R
import com.example.quizzingapp.ui.fragment.Login_Fragment

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var name:String
        lateinit var email:String
        lateinit var bio:String
        lateinit var Image:Uri
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bio="Set your bio"
        Image="".toUri()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, Login_Fragment())
            .commit()
    }
}
package com.example.actividadev1

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.actividadev1.databinding.ActivitySetPhoneBinding

class SetPhone : AppCompatActivity() {

    private lateinit var setPhoneBinding: ActivitySetPhoneBinding

    private lateinit var sharedFile: SharedPreferences
    private lateinit var nameSharedPhone: String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setPhoneBinding = ActivitySetPhoneBinding.inflate(layoutInflater)
        setContentView(setPhoneBinding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
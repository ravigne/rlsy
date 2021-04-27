package com.mrsy.rlsy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.mrsy.help.help

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val faq = findViewById<Button>(R.id.faq)
        faq.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Faq::class.java)
            startActivity(intent)


        })
        val hlp = findViewById<Button>(R.id.help)
        hlp.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, help::class.java)
            startActivity(intent)
        })
    }
}
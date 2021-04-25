package com.mrsy.rlsy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mrsy.help.help
import com.mrsy.upload.ItemsActivity
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// TODO: 19/4/21 network

        NoInternetDialogSignal.Builder(
                this,
                lifecycle
        ).apply {
            dialogProperties.apply {
            }
        }.build()


        // TODO: 18/4/21 //full screen 
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // TODO: 18/4/21 button 
        val info = findViewById<Button>(R.id.inf)
        
        info.setOnClickListener{
            val intent = Intent(this, help::class.java)
            startActivity(intent)
        }
        val button = findViewById<Button>(R.id.noticc)

        button.setOnClickListener{
            val intent = Intent(this, Notice::class.java)
            startActivity(intent)
        }


        val button1 = findViewById<Button>(R.id.result)

        button1.setOnClickListener{
            val intent = Intent(this, Result::class.java)
            startActivity(intent)
        }

        val btnform = findViewById<Button>(R.id.form)
        btnform.setOnClickListener{
            val intent = Intent(this, Form::class.java)
            startActivity(intent)
        }

        val bthelp = findViewById<Button>(R.id.btnhelp)
        bthelp.setOnClickListener{
            val intent = Intent(this, Activity::class.java)
            startActivity(intent)
        }



        val progress = findViewById<ProgressBar>(R.id.progmain)
        supportActionBar?.hide()
        
        // TODO: 18/4/21 firebase 
        val mainslider: ImageSlider = findViewById<View>(R.id.image_slider) as ImageSlider
        val remoteimages: MutableList<SlideModel> = java.util.ArrayList()
        FirebaseDatabase.getInstance().reference.child("slider")

                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        progress.visibility = View.GONE
                        for (data in dataSnapshot.children) remoteimages.add(

                                        SlideModel (
                                        data.child("url").value.toString(),
                                data.child("title").value.toString(),
                                ScaleTypes.FIT

                        )

                        )

                        mainslider.setImageList(remoteimages, ScaleTypes.FIT)
                        mainslider.setItemClickListener(object : ItemClickListener {
                            override fun onItemSelected(position: Int) {
                                openImagesActivity()

                            }

                        })

                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })
    }

    private fun openImagesActivity() {
        val intent = Intent(this, ItemsActivity::class.java)
        startActivity(intent)    }


    // TODO: 18/4/21 //backpess
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 1000)

    }

}


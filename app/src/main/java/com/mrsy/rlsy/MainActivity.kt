package com.mrsy.rlsy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

class MainActivity : AppCompatActivity(){
    private var mInterstitialAd: InterstitialAd? = null
    private var MY_REQUEST_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        FirebaseMessaging.getInstance().subscribeToTopic("hello");
//        update
        callinupdate()

        //ADS

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-5066360578662876/2705798972", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })

        Handler().postDelayed({
            showad()
        },60000)



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
            val intent = Intent(this, Profile::class.java)
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
            val intent = Intent(this, Studentcorner::class.java)
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

    private fun callinupdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    MY_REQUEST_CODE)

            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data== null) return
        if (requestCode == MY_REQUEST_CODE) {
            Toast.makeText(this,"Updating...",Toast.LENGTH_LONG).show()
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    private fun showad() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        }

    }

    private fun openImagesActivity() {
        val intent = Intent(this, Activity::class.java)
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

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1000)

    }

}


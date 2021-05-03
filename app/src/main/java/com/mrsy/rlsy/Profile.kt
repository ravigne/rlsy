package com.mrsy.rlsy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.mrsy.help.help

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        MobileAds.initialize(this, object: OnInitializationCompleteListener {
            override fun onInitializationComplete(initializationStatus: InitializationStatus) {}
        })
        val builder = AdLoader.Builder(this, "ca-app-pub-5066360578662876/2241930397")
        builder.forUnifiedNativeAd(object: UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {
            override fun onUnifiedNativeAdLoaded(unifiedNativeAd: UnifiedNativeAd) {
                val templateView = findViewById<TemplateView>(R.id.my_template1)
                templateView.setNativeAd(unifiedNativeAd)
            }
        })
        val adLoader = builder.build()
        adLoader.loadAd(AdRequest.Builder().build())

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
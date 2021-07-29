package com.mrsy.rlsy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

class Form : AppCompatActivity() {
    private val  url = "https://brabuonline.in/"
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        MobileAds.initialize(this) {}
        val mAdView  = findViewById<AdView>(R.id.adView1)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        // TODO: 19/4/21 network 
        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {

            }
        }.build()
        val webView = findViewById<WebView>(R.id.idform)
        val progress = findViewById<ProgressBar>(R.id.progform)
        webView.settings.javaScriptEnabled=true
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view:WebView, url:String):Boolean {
                progress.visibility = View.VISIBLE
         return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                progress.visibility = View.GONE
                super.onPageFinished(view, url)

            }

        }

        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        val webView = findViewById<WebView>(R.id.idform)
        if(webView!!.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}
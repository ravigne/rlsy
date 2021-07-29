package com.mrsy.rlsy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.UnifiedNativeAd

class Studentcorner : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentcorner)
        val webView =findViewById<WebView>(R.id.studentweb)

        MobileAds.initialize(this
        ) { }
        val builder = AdLoader.Builder(this, "ca-app-pub-5066360578662876/5724974241")
        builder.forUnifiedNativeAd(object: UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {
            override fun onUnifiedNativeAdLoaded(unifiedNativeAd: UnifiedNativeAd) {
                val templateView = findViewById<TemplateView>(R.id.my_template1)
                templateView.setNativeAd(unifiedNativeAd)
            }
        })
        val adLoader = builder.build()
        adLoader.loadAd(AdRequest.Builder().build())




        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                webView.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('footer').style.display='none'; " +
                            "document.getElementById('header').style.display='none'; " +
                            " document.getElementsByClassName('wrapper row2')[0].style.display='none'; " +
                            "})()"
                )
            }
        }
        webView.loadUrl("https://www.brabu.net/student_corner.php")

    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                val webView=findViewById<WebView>(R.id.studentweb)
                if (webView.canGoBack()) {
                    webView.goBack()

                }else{finish()

                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}

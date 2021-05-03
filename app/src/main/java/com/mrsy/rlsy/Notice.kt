package com.mrsy.rlsy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

class Notice : AppCompatActivity() {
    private val URL = "https://brabu.edu.in/notice.php"
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        MobileAds.initialize(this) {}
        val mAdView  = findViewById<AdView>(R.id.adView)
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

        val webview =findViewById<WebView>(R.id.webview)
         webview.settings.javaScriptEnabled = true
        val progress = findViewById<ProgressBar>(R.id.prognotice)
        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipe.setOnRefreshListener { webview.reload() }
        webview.webViewClient = WebViewClient()
        webview.webChromeClient = WebChromeClient()

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                progress.visibility = View.VISIBLE

                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                progress.visibility = View.GONE
                swipe.isRefreshing = false
                super.onPageFinished(view, url)
                webview.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('footer').style.display='none'; "+
                            "document.getElementById('header').style.display='none'; "+
                            "})()"
                )
            }

        }
        webview.loadUrl(URL)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                val webView=findViewById<WebView>(R.id.webview)
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

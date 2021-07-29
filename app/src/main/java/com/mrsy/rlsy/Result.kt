package com.mrsy.rlsy

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.UnifiedNativeAd
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal


class Result : AppCompatActivity() {
    private val URL = "https://www.brabu.net/ug_result.php"
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        MobileAds.initialize(this
        ) { }
        val builder = AdLoader.Builder(this, "ca-app-pub-5066360578662876/6828651474")
        builder.forUnifiedNativeAd(object: UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {
            override fun onUnifiedNativeAdLoaded(unifiedNativeAd:UnifiedNativeAd) {
                val templateView = findViewById<TemplateView>(R.id.my_template)
                templateView.setNativeAd(unifiedNativeAd)
            }
        })
        val adLoader = builder.build()
        adLoader.loadAd(AdRequest.Builder().build())

        // TODO: 19/4/21 network
        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {

            }
        }.build()

        val webview =findViewById<WebView>(R.id.webview1)
        webview.settings.javaScriptEnabled = true
        webview.settings.domStorageEnabled = true

         webview.webViewClient = WebViewClient()
        webview.webChromeClient = WebChromeClient()
        val progress = findViewById<ProgressBar>(R.id.progresult)

        webview.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        webview.webChromeClient = object : WebChromeClient() {
        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            return super.onJsAlert(view, url, message, result)
        }
    }
       webview.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        })
        webview.webViewClient = object : WebViewClient() {


            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                progress.visibility = View.VISIBLE
                webview.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('footer').style.display='none'; " +
                            "document.getElementById('header').style.display='none'; " +
                            " document.getElementsByClassName('wrapper row2')[0].style.display='none'; " +
                            "})()"
                )
                return super.shouldOverrideUrlLoading(view, url)

            }
            override fun onPageFinished(view: WebView, url: String) {
                progress.visibility = View.GONE
                super.onPageFinished(view, url)
                webview.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('footer').style.display='none'; " +
                            "document.getElementById('header').style.display='none'; " +
                            " document.getElementsByClassName('wrapper row2')[0].style.display='none'; " +
                            "})()"
                )
            }

        }
        webview.loadUrl(URL)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                val webView=findViewById<WebView>(R.id.webview1)
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


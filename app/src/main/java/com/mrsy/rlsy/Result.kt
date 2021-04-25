package com.mrsy.rlsy

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal


class Result : AppCompatActivity() {
    private val URL = "https://www.brabu.net/ug_result.php"
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
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
        webview.webViewClient = WebViewClient()
        webview.webChromeClient = WebChromeClient()
        var progress = findViewById<ProgressBar>(R.id.progresult)


        webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                progress.visibility = View.VISIBLE

                return super.shouldOverrideUrlLoading(view, url)
            }
            override fun onPageFinished(view: WebView, url: String) {
                progress.visibility = View.GONE
                super.onPageFinished(view, url)
                webview.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('footer').style.display='none'; "+
                            "document.getElementById('header').style.display='none'; "+
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
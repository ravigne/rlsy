package com.mrsy.rlsy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

class Faq : AppCompatActivity() {
    lateinit var mWebview : WebView
    val ref = FirebaseDatabase.getInstance().getReference("FAQ")
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {

            }
        }.build()
        mWebview = findViewById(R.id.webfaq)
        mWebview.settings.javaScriptEnabled = true
        val progress = findViewById<ProgressBar>(R.id.progfaq)

        mWebview.webViewClient=object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView, url:String):Boolean {
                progress.visibility = View.VISIBLE

                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                progress.visibility = View.GONE
                super.onPageFinished(view, url)
                mWebview.loadUrl(
                        "javascript:(function() { " +
                                "document.getElementById('footer').style.display='none'; " +
                                "document.getElementById('header').style.display='none'; " +
                                " document.getElementsByClassName('widget PopularPosts')[0].style.display='none'; " +
                                " document.getElementsByClassName('post-title-container')[0].style.display='none'; " +
                                " document.getElementsByClassName('post-bottom')[0].style.display='none'; " +
                                " document.getElementsByClassName('post-header-container container')[0].style.display='none'; " +
                                        "})()")
            }

        }






        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val  message : Any? = snapshot.getValue(toString().javaClass)
                mWebview.loadUrl(message.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    override fun onBackPressed() {
        val webView = findViewById<WebView>(R.id.webfaq)
        if(webView!!.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

}
package com.mrsy.rlsy

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

class Activity : AppCompatActivity() {
    lateinit var mWebview : WebView
    val ref = FirebaseDatabase.getInstance().getReference("URL")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {

            }
        }.build()
        mWebview = findViewById(R.id.webhelp)
        mWebview.settings.javaScriptEnabled = true
        var progress = findViewById<ProgressBar>(R.id.proghelp)

        mWebview.webViewClient=object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view:WebView, url:String):Boolean {
                progress.visibility = View.VISIBLE

      return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                progress.visibility = View.GONE
                super.onPageFinished(view, url)
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
        val webView = findViewById<WebView>(R.id.webhelp)
        if(webView!!.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

}
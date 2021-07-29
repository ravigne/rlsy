package com.mrsy.rlsy

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.webkit.DownloadListener
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

class Activity : AppCompatActivity() {
    lateinit var mWebview : WebView
    val ref = FirebaseDatabase.getInstance().getReference("URL")
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity)
        MobileAds.initialize(this) {}
        val mAdView1  = findViewById<AdView>(R.id.adView1)
        val mAdView2  = findViewById<AdView>(R.id.adView2)

        val adRequest = AdRequest.Builder().build()
        mAdView1.loadAd(adRequest)
        mAdView2.loadAd(adRequest)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {

            }
        }.build()




        mWebview = findViewById(R.id.webhelp)
        mWebview.settings.builtInZoomControls = true
        mWebview.settings.displayZoomControls = false

        mWebview.settings.javaScriptEnabled = true
        val progress = findViewById<ProgressBar>(R.id.proghelp)

        mWebview.webViewClient=object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view:WebView, url:String):Boolean {
                progress.visibility = View.VISIBLE
                mWebview.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('footer').style.display='none'; " +
                            "document.getElementById('header').style.display='none'; " +
                            " document.getElementsByClassName('widget PopularPosts')[0].style.display='none'; " +
                            " document.getElementsByClassName('post-title-container')[0].style.display='none'; " +
                            " document.getElementsByClassName('post-bottom')[0].style.display='none'; " +
                            " document.getElementsByClassName('post-header-container container')[0].style.display='none'; " +
                            "})()")
      return super.shouldOverrideUrlLoading(view, url)
            }


            override fun onPageFinished(view: WebView, url: String) {
                progress.visibility = View.GONE
                mWebview.loadUrl(
                    "javascript:(function() { " +
                            "document.getElementById('footer').style.display='none'; " +
                            "document.getElementById('header').style.display='none'; " +
                            " document.getElementsByClassName('widget PopularPosts')[0].style.display='none'; " +
                            " document.getElementsByClassName('post-header-line-1')[0].style.display='none'; " +

                            " document.getElementsByClassName('post-title-container')[0].style.display='none'; " +
                            " document.getElementsByClassName('post-bottom')[0].style.display='none'; " +
                            " document.getElementsByClassName('post-header-container container')[0].style.display='none'; " +
                            "})()")
                super.onPageFinished(view, url)

            }

        }



        mWebview.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val request: DownloadManager.Request = DownloadManager.Request(
                Uri.parse(url)
            )
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) //Notify client once download is completed!
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "Notice "
            )
            val dm: DownloadManager =
                getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(
                applicationContext,
                "Downloading File",  //To notify the Client that the file is being downloaded
                Toast.LENGTH_LONG
            ).show()
            progress.visibility = View.GONE
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



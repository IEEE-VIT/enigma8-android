package com.ieeevit.enigma8.webactivity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma8.R

class HintwebActivity : AppCompatActivity(){
    private val webView:WebView?=null
    // web view code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hint_web)
        val webView = findViewById<WebView>(R.id.url)
        webView.webViewClient = WebViewClient()
        var extras = intent.getStringExtra("URL")
        webView.loadUrl("$extras")
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }
//    this code can be used when you want go back in your app using web view

//    override fun onBackPressed() {
//        if (webView!!.canGoBack()){ // this line is for going back
//            webView.goBack()
//        }
//        else{
//            super.onBackPressed() // if you press two times back button you will eit the app as we can see in other apps


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }




}
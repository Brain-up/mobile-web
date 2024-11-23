package com.epam.brainup.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.epam.brainup.BuildConfig

interface WebViewClientListener {
    fun showLoading()
    fun hideLoading()
    fun startActivity(intent: Intent)
}

class WebViewClient(private val listener: WebViewClientListener) : WebViewClient() {

    @SuppressLint("WebViewClientOnReceivedSslError")
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError) {
        handler.proceed()
        Log.d("ssl_error", error.toString())
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        listener.showLoading()
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        listener.hideLoading()
    }

    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError
    ) {
        Log.e(TAG, "request: $request, error: $error")
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) =
        if (request.url.toString().startsWith(BuildConfig.URL)) {
            false
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(request.url)
            listener.startActivity(intent)
            true
        }

    companion object {
        const val TAG = "WebViewClient"
    }
}
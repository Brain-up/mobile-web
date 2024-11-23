package com.epam.brainup.webview

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView

interface WebChromeClientListener {
    fun showProgress(progress: Int)
}

class WebChromeClient(
    private val listener: WebChromeClientListener
) : WebChromeClient() {
    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        listener.showProgress(newProgress)
    }

    override fun onConsoleMessage(message: ConsoleMessage): Boolean {
        Log.d(
            "WebView", "${message.message()} -- From line " +
                    "${message.lineNumber()} of ${message.sourceId()}"
        )
        return true
    }
}

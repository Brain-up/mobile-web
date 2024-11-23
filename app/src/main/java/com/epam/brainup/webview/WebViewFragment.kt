package com.epam.brainup.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.epam.brainup.BuildConfig
import com.epam.brainup.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding: FragmentWebViewBinding get() = _binding!!

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = createWebView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initUI() {
        with(binding) {
            webviewProgressBar.scaleY = 3f
            webviewProgressBar.visibility = View.INVISIBLE
            webviewContainer.addView(webView)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun createWebView() = WebView(requireContext()).apply {
        setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_BACK -> if (this@apply.canGoBack()) {
                            this@apply.goBack()
                            return true
                        }
                    }
                }
                return false
            }
        })
        settings.apply {
            domStorageEnabled = true
            javaScriptEnabled = true
            allowFileAccess = true
            setGeolocationEnabled(true)
            loadsImagesAutomatically = true
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }

        scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        isScrollbarFadingEnabled = true

        if (Build.MANUFACTURER == "HUAWEI") settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webViewClient = WebViewClient(
            object : WebViewClientListener {
                override fun showLoading() {
                    _binding?.webviewProgressBar?.visibility = View.VISIBLE
                }

                override fun hideLoading() {
                    _binding?.webviewProgressBar?.visibility = View.INVISIBLE
                }

                override fun startActivity(intent: Intent) {
                    this@WebViewFragment.startActivity(intent)
                }
            },
        )
        webChromeClient = WebChromeClient(
            object : WebChromeClientListener {
                override fun showProgress(progress: Int) {
                    _binding?.webviewProgressBar?.progress = progress
                }
            }
        )
        loadUrl(BuildConfig.URL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.webviewContainer.removeAllViews()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        with(webView) {
            webView.clearHistory()
            clearCache(true)
            loadUrl("about:blank")
            onPause()
            removeAllViews()
            pauseTimers()
            destroy()
        }
    }
}



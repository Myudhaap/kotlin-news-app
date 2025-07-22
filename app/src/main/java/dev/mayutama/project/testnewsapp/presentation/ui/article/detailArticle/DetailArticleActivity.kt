package dev.mayutama.project.testnewsapp.presentation.ui.article.detailArticle

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import dev.mayutama.project.testnewsapp.util.viewBinding
import dev.mayutama.project.testnewsapp.databinding.ActivityDetailArticleBinding
import dev.mayutama.project.testnewsapp.util.Util

class DetailArticleActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityDetailArticleBinding::inflate)
    private lateinit var targetUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        targetUrl = intent.getStringExtra(EXTRA_URL) ?: ""

        binding.wvArticle.apply {
            settings.javaScriptEnabled = true
            webViewClient = object: WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    showLoading()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    hideLoading()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    hideLoading()

                    if (request?.isForMainFrame == true) {
                        Util.showToast(this@DetailArticleActivity, "Failed to load page")
                    }
                    Log.d("DetailArticleActivity", "onReceivedError: $error")
                }
            }
            loadUrl(targetUrl)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.wvArticle.canGoBack()){
                    binding.wvArticle.goBack()
                }else{
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun showLoading(){
        binding.loadingLayout.root.visibility = View.VISIBLE
        Util.disableScreenAction(window)
    }

    private fun hideLoading(){
        binding.loadingLayout.root.visibility = View.GONE
        Util.enableScreenAction(window)
    }

    companion object {
        val EXTRA_URL = "extra_url"
    }
}
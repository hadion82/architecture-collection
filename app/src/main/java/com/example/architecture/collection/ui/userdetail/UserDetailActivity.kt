package com.example.architecture.collection.ui.userdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.example.architecture.databinding.ActivityUserDetailBinding
import com.example.data.entity.UserEntity

class UserDetailActivity: AppCompatActivity() {

    private val binding by lazy { ActivityUserDetailBinding.inflate(layoutInflater) }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val htmlUrl = intent.extras?.get(UserEntity.KEY_HTML_URL) as? String

        if(htmlUrl.isNullOrBlank()) return

        binding.webView.apply {
            settings.javaScriptEnabled = true
            isVerticalScrollBarEnabled = true
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(this, true)

            webChromeClient = WebChromeClient()

            loadUrl(htmlUrl)
        }
    }
}
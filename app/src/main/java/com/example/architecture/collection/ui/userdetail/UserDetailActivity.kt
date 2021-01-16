package com.example.architecture.collection.ui.userdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.example.architecture.R
import com.example.data.entity.UserEntity
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity: AppCompatActivity(R.layout.activity_user_detail) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val htmlUrl = intent.extras?.get(UserEntity.KEY_HTML_URL) as? String

        if(htmlUrl.isNullOrBlank()) return

        web_view.apply {
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
package com.cuongtd.hackernews.ui.screens.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.cuongtd.hackernews.viewmodel.FavoriteViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ContentCompose(
    viewModel: FavoriteViewModel,
    storyId: String,
    urlToRender: String = "",
    storyTitle: String = ""
) {
    var progress by remember {
        mutableStateOf((0.1f))
    }
    var canGoBack by remember {
        mutableStateOf(false)
    }
    var canGoForward by remember {
        mutableStateOf(false)
    }
    var webView: WebView? by remember {
        mutableStateOf(null)
    }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        if (progress != 1f) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
                backgroundColor = Color.White,
                progress = progress,
                color = Color(0xFF1da1f2)
            )
        }
        Column(modifier = Modifier.weight(1F)) {
            AndroidView(factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            progress = (newProgress / 100f)
                        }
                    }
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView,
                            url: String?
                        ): Boolean {
                            view.loadUrl(url!!)
                            return false
                        }

                        override fun doUpdateVisitedHistory(
                            view: WebView,
                            url: String?,
                            isReload: Boolean
                        ) {
                            super.doUpdateVisitedHistory(view, url, isReload)
                            canGoBack = view.canGoBack()
                            canGoForward = view.canGoForward()
                        }
                    }
                    loadUrl(urlToRender)
                    webView = this
                }
            }, update = {
                it.loadUrl(urlToRender)
            })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 5.dp)
            ) {
                WebViewNavigatorIcons(webView, canGoBack, canGoForward)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1F)
            ) {
                FavoriteIconCompose(
                    viewModel,
                    storyId,
                    urlToRender,
                    storyTitle
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .weight(1F)
                    .padding(end = 10.dp)
            ) {
                CopyCompose(webView)
                Box(modifier = Modifier.width(5.dp))
                ShareCompose(urlToRender, storyTitle)
            }
        }
    }
}




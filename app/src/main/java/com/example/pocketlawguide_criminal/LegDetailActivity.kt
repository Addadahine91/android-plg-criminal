package com.example.pocketlawguide_criminal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.navigation.NavigationView
import com.yuyakaido.android.cardstackview.*
import java.util.*
import android.text.Spanned
import android.text.SpannableString
import android.text.style.TypefaceSpan
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.webkit.WebView

class LegDetailActivity : AppCompatActivity() {

        private lateinit var webView: WebView

        companion object {
            const val EXTRA_TITLE = "name"
            const val EXTRA_URL = "url"

            fun newIntent(context: Context, recipe: Leg): Intent {
                val detailIntent = Intent(context, LegDetailActivity::class.java)

                detailIntent.putExtra(EXTRA_TITLE, recipe.name)
                detailIntent.putExtra(EXTRA_URL, recipe.url)

                return detailIntent
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_leg_detail)

            val title = intent.extras?.getString(EXTRA_TITLE)
            val url = intent.extras?.getString(EXTRA_URL)

            val pdfUrl = "http://docs.google.com/gview?embedded=true&url=$url"

            setTitle(title)

            webView = findViewById(R.id.detail_web_view)
            webView.isVerticalScrollBarEnabled = true
            webView.loadUrl(pdfUrl)

        }
    }
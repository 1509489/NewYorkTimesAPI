package com.pixelart.newyorktimesapi.ui.detailscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.pixelart.newyorktimesapi.R
import kotlinx.android.synthetic.main.fragment_article_detail.view.*

/**
 * A fragment representing a single Article detail screen.
 * This fragment is either contained in a [ArticleListActivity]
 * in two-pane mode (on tablets) or a [ArticleDetailActivity]
 * on handsets.
 */
class ArticleDetailFragment : androidx.fragment.app.Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_WEB_URL)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                url = it.getString(ARG_WEB_URL)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_article_detail, container, false)

        val u = url
        rootView.article_detail.apply {
            webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    rootView.progressBar.visibility = View.GONE
                }
            }
            settings.javaScriptEnabled = true
            loadUrl(u)
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the url ID that this fragment
         * represents.
         */
        const val ARG_WEB_URL = "web_url"
    }
}

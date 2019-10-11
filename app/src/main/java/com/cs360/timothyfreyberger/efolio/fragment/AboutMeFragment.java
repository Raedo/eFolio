package com.cs360.timothyfreyberger.efolio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cs360.timothyfreyberger.efolio.R;

import androidx.fragment.app.Fragment;

public class AboutMeFragment extends Fragment {

    public AboutMeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_me, container, false);
        Context thisContext = container.getContext();

        WebView mWebView = (WebView) root.findViewById(R.id.webView);
        mWebView.loadUrl("https://www.linkedin.com/in/timothy-freyberger/");

        //enable JavaScript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Force links and redirects to open in the webView
        mWebView.setWebViewClient(new WebViewClient());

        return root;
    }


}

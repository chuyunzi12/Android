package com.grt.testdrivendevelopment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link BookListFragment} subclass.
 * Use the {@link BrowserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowserFragment extends Fragment {

    public BrowserFragment() {
        // Required empty public constructor
    }

    public static BrowserFragment newInstance() {
        BrowserFragment fragment = new BrowserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_browser, container, false);
        WebView webView=rootView.findViewById(R.id.Browser);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view,url);
            }
        });
        WebSettings webSettings=webView.getSettings();
        webView.loadUrl("https://news.sina.com.cn");
        webSettings.setJavaScriptEnabled(true);
        return rootView;
    }
}
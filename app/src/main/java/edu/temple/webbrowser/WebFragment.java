package edu.temple.webbrowser;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebFragment extends Fragment {
    private final String httpString = "https://";

    String newURL = httpString;
    WebFragmentInterface wfInterfaceListener;

    WebView webview;
    Button forwardButton;
    Button backButton;

    public WebFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        wfInterfaceListener = (WebFragmentInterface) activity;
    }

    /*inflate layout view and set listeners for forward/back buttons*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web, container, false);

        forwardButton = v.findViewById(R.id.for_b);
        backButton = v.findViewById(R.id.back_b);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoBack()) {
                    webview.goBack();
                }
            }
        });
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoForward()) {
                    webview.goForward();
                }
            }
        });
        return v;
    }

    /*called when a user wants to load a URL into the fragment's WebView*/
    public void loadSite(String userURL) {
        webview = getView().findViewById(R.id.fragment_webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                    wfInterfaceListener.updateURL(url);
                    newURL = url;
            }
        });
        /*include a check to avoid malformed URL*/
        newURL = userURL;
        if (!(userURL.substring(0,8).equals(httpString))) {
            newURL = httpString + userURL;
        }
        webview.loadUrl(newURL);
    }

    public interface WebFragmentInterface {
        void updateURL(String url);
    }
}

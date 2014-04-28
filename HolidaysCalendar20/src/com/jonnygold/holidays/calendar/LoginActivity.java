package com.jonnygold.holidays.calendar;

import com.perm.kate.api.Auth;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends ActionBarActivity {
    private static final String TAG = "Kate.LoginActivity";
    private static final String APP_ID = "4319903";

    private WebView webview;
    
    private View progressView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        progressView = (View) findViewById(R.id.view_progress_bar);
        
        webview = (WebView) findViewById(R.id.vkontakteview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearCache(true);
        
        //Чтобы получать уведомления об окончании загрузки страницы
        webview.setWebViewClient(new VkontakteWebViewClient());
                
        //otherwise CookieManager will fall with java.lang.IllegalStateException: CookieSyncManager::createInstance() needs to be called before CookieSyncManager::getInstance()
        CookieSyncManager.createInstance(this);
        
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        
        String url=Auth.getUrl(APP_ID, Auth.getSettings());
        webview.loadUrl(url);
    }
    
    class VkontakteWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);      
            progressView.setVisibility(View.VISIBLE);
        	webview.setVisibility(View.GONE);
            parseUrl(url);
        }
        
        @Override
        public void onPageFinished(WebView view, String url) {
        	super.onPageFinished(view, url);
        	Log.w("URL", url);
        	progressView.setVisibility(View.GONE);
        	webview.setVisibility(View.VISIBLE);
        }
        
    }
    
    private void parseUrl(String url) {
        try {
            if(url==null)
                return;
            Log.i(TAG, "url="+url);
            if(url.startsWith(Auth.redirect_url))
            {
                if(!url.contains("error=")){
                    String[] auth=Auth.parseRedirectUrl(url);
                    Intent intent=new Intent();
                    intent.putExtra("token", auth[0]);
                    intent.putExtra("user_id", Long.parseLong(auth[1]));
                    intent.putExtra("holiday", getIntent().getParcelableExtra("holiday"));
                    setResult(Activity.RESULT_OK, intent);
                }
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
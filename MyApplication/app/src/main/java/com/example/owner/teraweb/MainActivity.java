package com.example.owner.teraweb;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public EditText urlEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        urlEntry = (EditText)findViewById(R.id.urlBar);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://www.egallagher.com");
        myWebView.setWebViewClient(new WebViewClient(){

           @Override
           public void onPageStarted(WebView view, String url, Bitmap favicon) {
               urlEntry.setText(url);
               //fixes the issue where on goBack() & goForward() the getUrl()
               //function may not return the correct url because
               //the redirect has not happened yet
           }
        });
        urlEntry.setText(myWebView.getUrl().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //when clicked, if there is user webpage history, will go to last visited webpaged prior
    public void goToURL(View view){
        WebView myWebView = (WebView) findViewById(R.id.webview);
        String url = urlEntry.getText().toString();
        if(!url.startsWith("www.")&& !url.startsWith("http://")){
            url = "www."+url;
        }
        if(!url.startsWith("http://")){
            url = "http://"+url;
        }
        myWebView.loadUrl(url);

    }

    //when clicked, if there is user webpage history, will go to previously viewed webpage
    public void historyBack(View view){
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.goBack();

    }

    //when clicked, if there is user webpage history, will go to last visited webpaged prior
    public void historyForward(View view){
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.goForward();

    }
}

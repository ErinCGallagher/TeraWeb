package com.example.owner.teraweb;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public EditText urlEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        urlEntry = (EditText)findViewById(R.id.urlBar);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://www.egallagher.com");

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                urlEntry.setText(url);
                //fixes the issue where on goBack() & goForward() the getUrl()
                //function may not return the correct url because
                //the redirect has not happened yet
            }
        });
        urlEntry.setText(myWebView.getUrl().toString());

        urlEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            //go to the URL when the send button is pressed
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    WebView myWebView = (WebView) findViewById(R.id.webview);
                    String url = urlEntry.getText().toString();
                    if(!url.startsWith("www.")&& !url.startsWith("http://")){
                        url = "www."+url;
                    }
                    if(!url.startsWith("http://")){
                        url = "http://"+url;
                    }
                    myWebView.loadUrl(url);

                    try  {
                        //close the soft keyboard
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }

                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        WebView myWebView = (WebView) findViewById(R.id.webview);
        int id = item.getItemId();

        if (id == R.id.google) {
            myWebView.loadUrl("http://google.com");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

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

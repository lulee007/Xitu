package com.lulee007.xitu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.lulee007.xitu.base.XTBaseActivity;
import com.mikepenz.materialize.MaterializeBuilder;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class EntryWebPageActivity extends XTBaseActivity {

    public final static String BUNDLE_KEY_ENTRY_URL = "entry_url";
    public final static String BUNDLE_KEY_ENTRY_AUTHOR_NAME = "entry_author_name";
    public final static String BUNDLE_KEY_ENTRY_AUTHOR_ICON = "entry_author_icon";
    private static final String BUNDLE_KEY_ENTRY_AUTHOR_ID = "author_id";
    private static final String BUNDLE_KEY_ENTRY_COMMENT_COUNT = "comment_count";
    private static final String BUNDLE_KEY_ENTRY_COLLECTION_COUNT = "collection_count";

    private WebView webView;
    private View loadingDataView;
    private WebSettings webSettings;
    private ActionBar actionBar;
    private String authorIcon;
    private String authorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_page);
        new MaterializeBuilder().withActivity(this).build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        webView = (WebView) findViewById(R.id.web_view_page);
        loadingDataView = findViewById(R.id.loading_data);

        //check params
        Intent intent = getIntent();
        String url = intent.getStringExtra(BUNDLE_KEY_ENTRY_URL);
        String authorName = intent.getStringExtra(BUNDLE_KEY_ENTRY_AUTHOR_NAME);
        authorIcon = intent.getStringExtra(BUNDLE_KEY_ENTRY_AUTHOR_ICON);
        authorId = intent.getStringExtra(BUNDLE_KEY_ENTRY_AUTHOR_ID);
        int commentCount = intent.getIntExtra(BUNDLE_KEY_ENTRY_COMMENT_COUNT, 0);
        int collectionCount = intent.getIntExtra(BUNDLE_KEY_ENTRY_COLLECTION_COUNT, 0);
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException(BUNDLE_KEY_ENTRY_URL + " cannot be null or empty");
        }
        if (TextUtils.isEmpty(authorName)) {
            throw new IllegalArgumentException(BUNDLE_KEY_ENTRY_AUTHOR_NAME + " cannot be null or empty");
        }
        if (TextUtils.isEmpty(authorIcon)) {
            throw new IllegalArgumentException(BUNDLE_KEY_ENTRY_AUTHOR_ICON + " cannot be null or empty");
        }
        if (TextUtils.isEmpty(authorId)) {
            throw new IllegalArgumentException(BUNDLE_KEY_ENTRY_AUTHOR_ID + " cannot be null or empty");
        }

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(R.layout.entry_web_view_toolbar);
            RxView.clicks(actionBar.getCustomView().findViewById(R.id.author_view))
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            Intent intent = AuthorHomeActivity.buildIntent(EntryWebPageActivity.this, authorIcon, authorId);
                            startActivity(intent);
                        }
                    });
            actionBar.setDisplayShowCustomEnabled(true);
        }
        webSettings = webView.getSettings();
        webView.setWebViewClient(new XTWebClient());
        webView.setWebChromeClient(new XTWebChromeClient());
        webSettings.setJavaScriptEnabled(true);

        ((TextView) findViewById(R.id.comment_count)).setText(String.valueOf(commentCount));
        ((TextView) findViewById(R.id.author_name)).setText(authorName);
        ((TextView) findViewById(R.id.entry_collection_count)).setText(String.valueOf(collectionCount));
        Glide.with(this).load(authorIcon).into((ImageView) findViewById(R.id.author_icon));
        webView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Logger.d("action_share");
                return true;
            case R.id.action_view_in_browser:
                Logger.d("action_view_in_browser");
                return true;
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected class XTWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingDataView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingDataView.setVisibility(View.GONE);
        }


    }

    protected class XTWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            if (actionBar != null) {
//                actionBar.setTitle(title);
//            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    public static Intent buildEntryWebPageParams(Context context, String url, String authorName, String icon, String authorId,int commentCount,int collectionCount) {
        Intent intent = new Intent(context, EntryWebPageActivity.class);
        intent.putExtra(EntryWebPageActivity.BUNDLE_KEY_ENTRY_URL, url);
        intent.putExtra(EntryWebPageActivity.BUNDLE_KEY_ENTRY_AUTHOR_NAME, authorName);
        intent.putExtra(EntryWebPageActivity.BUNDLE_KEY_ENTRY_AUTHOR_ICON, icon);
        intent.putExtra(EntryWebPageActivity.BUNDLE_KEY_ENTRY_AUTHOR_ID, authorId);
        intent.putExtra(EntryWebPageActivity.BUNDLE_KEY_ENTRY_COMMENT_COUNT, commentCount);
        intent.putExtra(EntryWebPageActivity.BUNDLE_KEY_ENTRY_COLLECTION_COUNT, collectionCount);

        return intent;

    }
}

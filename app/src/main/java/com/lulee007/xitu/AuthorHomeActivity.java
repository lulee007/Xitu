package com.lulee007.xitu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.lulee007.xitu.adapter.CommonFragmentPagerAdapter;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.models.Author;
import com.lulee007.xitu.presenter.AuthorHomePresenter;
import com.lulee007.xitu.presenter.CollectionPresenter;
import com.lulee007.xitu.presenter.ListEntriesFragmentPresenter;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.util.GlideCircleTransform;
import com.lulee007.xitu.view.IAuthorHomeView;
import com.lulee007.xitu.view.fragment.ListEntriesFragment;
import com.lulee007.xitu.view.fragment.SubscribedTagsFragment;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.ArrayList;
import java.util.List;

public class AuthorHomeActivity extends XTBaseActivity implements AppBarLayout.OnOffsetChangedListener, IAuthorHomeView {

    private ImageView authorIcon;
    private float oldAlpha;
    private static final int minIconHeight = 32;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private AuthorHomePresenter authorHomePresenter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String userId;
    private boolean isCurrentUser;
    private ImageView authorBlurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_home);
        new MaterializeBuilder().withActivity(this).build();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        authorIcon = (ImageView) findViewById(R.id.author_icon);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_author_home);
        authorBlurView = (ImageView) findViewById(R.id.author_icon_blur);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appBarLayout.addOnOffsetChangedListener(this);
        setAuthorIconSize(240);
        collapsingToolbar.setTitleEnabled(false);

        String url = getIntent().getStringExtra("url");
        userId = getIntent().getStringExtra("author_id");
        isCurrentUser =AuthUserHelper.getInstance().isCurrentUser(userId);

        authorHomePresenter = new AuthorHomePresenter(this);
        authorHomePresenter.getAuthorInfo(userId);

        if(url!=null) {
            authorHomePresenter.loadUserBlurBackground(url);
            Glide.with(this)
                    .load(url)
                    .transform(new GlideCircleTransform(this))
                    .into(authorIcon);
        }

    }

    public static Intent buildIntent(Context context, String authorIconUrl, String authorId) {
        Intent intent = new Intent(context, AuthorHomeActivity.class);
        intent.putExtra("url", authorIconUrl);
        intent.putExtra("author_id", authorId);
        return intent;
    }

    private void setActionBarAlpha(float alpha) {
//        collapsingToolbar.setAlpha(alpha);
//        appBarLayout.setAlpha(alpha);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            collapsingToolbar.setContentScrim(getDrawable(R.mipmap.profile_bg));
//        }else{
//            collapsingToolbar.setContentScrimResource(R.mipmap.profile_bg);
//        }
//        int color= 0;
//
//            color=getResources().getColor(R.color.juejin_blue);
//
//        collapsingToolbar.setContentScrimColor(Color.argb((int) (alpha * 255)
//                , Color.red(color)
//                , Color.green(color)
//                , Color.blue(color)
//        ));
    }

    private void setAuthorIconSize(int newSize) {
        authorIcon.setVisibility(newSize == minIconHeight ? View.GONE : View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) authorIcon.getLayoutParams();
        layoutParams.width = layoutParams.height = newSize;
        authorIcon.setLayoutParams(layoutParams);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int max = appBarLayout.getTotalScrollRange();
        float alpha = Math.abs(verticalOffset * 1.0f / max);
        alpha = 1 - alpha;
        setActionBarAlpha(alpha);
        if (alpha == 0.0 || oldAlpha == alpha)
            return;
        oldAlpha = alpha;
        int newHeight = ((int) (alpha * 240 > minIconHeight ? alpha * 240 : minIconHeight));

        setAuthorIconSize(newHeight);

    }

    @Override
    public void onGetAuthorInfoDone(Author author) {
        getSupportActionBar().setTitle(author.getUsername());

        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = new ArrayList<>();
        if (!isCurrentUser)
            fragments.add(ListEntriesFragment.newInstanceForAuthor(ListEntriesFragmentPresenter.BY_USER, userId));

        fragments.add(ListEntriesFragment.newInstanceForAuthor(CollectionPresenter.BY_COLLECTION, userId));
        fragments.add(SubscribedTagsFragment.newInstanceForAuthor(userId));

        List<String> titles = new ArrayList<>();

        if (!isCurrentUser)
            titles.add(String.format("%d \r\n分享", author.getPostedEntriesCount()));

        titles.add(String.format("%d \r\n收藏", author.getCollectedEntriesCount()));
        titles.add(String.format("%d \r\n标签", author.getSubscribedTagsCount()));
        CommonFragmentPagerAdapter fragmentPagerAdapter = new CommonFragmentPagerAdapter(fragmentManager, fragments, titles);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onGetAuthorInfoError() {

    }

    @Override
    public void onUserBlurBgDownloaded(Bitmap bitmap) {
        authorBlurView.setImageBitmap(bitmap);
    }

    @Override
    public void onUserBlurBgDownloadError() {

    }
}

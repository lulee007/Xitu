package com.lulee007.xitu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.lulee007.xitu.util.BlurTransformation;
import com.lulee007.xitu.util.GlideCircleTransform;
import com.orhanobut.logger.Logger;

public class AuthorHomeActivity extends XTBaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private ImageView authorIcon;
    private float oldAlpha;
    private static final int minIconHeight = 32;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        authorIcon = (ImageView) findViewById(R.id.author_icon);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_author_home);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appBarLayout.addOnOffsetChangedListener(this);
        setAuthorIconSize(240);

        collapsingToolbar.setTitle("掘金者");
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        int color = getResources().getColor(R.color.juejin_blue);
        collapsingToolbar.setContentScrimColor(Color.argb((int) (0.4 * 255),
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        ));
        final String url = getIntent().getStringExtra("url");
        Glide.with(AuthorHomeActivity.this)
                .load(url)
                .bitmapTransform(new BlurTransformation(AuthorHomeActivity.this))
                .into((ImageView) findViewById(R.id.author_icon_blur));
        Glide.with(this)
                .load(url)
                .transform(new GlideCircleTransform(this))
                .into(authorIcon);

        FragmentManager fragmentManager=getSupportFragmentManager();
        CommonFragmentPagerAdapter fragmentPagerAdapter=new CommonFragmentPagerAdapter(fragmentManager,null,null);


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
        Logger.d("height:" + authorIcon.getLayoutParams().height);
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
}

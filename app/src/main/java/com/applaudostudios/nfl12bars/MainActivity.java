package com.applaudostudios.nfl12bars;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.applaudostudios.nfl12bars.models.BarVenue;


public class MainActivity extends ActionBarActivity implements
        BarsFragment.OnListItemSelectedListener {
    View mCustomView;
    ActionBar mActionBar;
    ImageView mImgShare;
    BarVenue bar;
    private boolean has2Panes = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActionBar = getSupportActionBar();
        determinePaneLayout();
        showCustomActionBar();

        if (has2Panes==true)
        {
            mImgShare = (ImageView)mCustomView.findViewById(R.id.imgShare);
            mImgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String share = bar.getName()+"\n"+bar.getAddress();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "NFL Bar Venues");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, share);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
        }

    }

    private void determinePaneLayout() {
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
        if (fragmentItemDetail != null) {
            has2Panes = true;
        }
    }



    @Override
    public void onItemSelected(BarVenue item) {

        if (has2Panes) {
            DetailFragment fragmentItem = DetailFragment.newInstance(item);
            bar = item;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentItem);
            ft.commit();
        }else {
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("item", item);
            startActivity(i);
        }
    }


    public void showCustomActionBar()
    {

        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        if (has2Panes==false) {
            mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        }else{
            mCustomView = mInflater.inflate(R.layout.custom_actionbar_large, null);
        }
            mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }
}
package com.applaudostudios.nfl12bars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.applaudostudios.nfl12bars.models.BarVenue;

/**
 * Created by RafaelCastro on 28/5/15.
 */
public class DetailActivity extends ActionBarActivity {
    DetailFragment mDetailFragment;

    View mCustomView;
    ActionBar mActionBar;
    ImageView mImgShare,mImgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mActionBar = getSupportActionBar();
        showCustomActionBar();
        final BarVenue barVenue = (BarVenue)getIntent().getSerializableExtra("item");
        mImgShare = (ImageView)mCustomView.findViewById(R.id.imgShare);
        mImgHome = (ImageView)mCustomView.findViewById(R.id.imgHome);

        mImgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String share = barVenue.getName()+"\n"+barVenue.getAddress();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "NFL Bar Venues");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, share);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        if (savedInstanceState==null)
        {
            mDetailFragment = DetailFragment.newInstance(barVenue);
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainer,mDetailFragment);
            fragmentTransaction.commit();
        }
    }


    public void showCustomActionBar()
    {

        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.custom_actionbar_details, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }
}
